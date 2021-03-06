<%@page import="com.chengsj.data.DataManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	DataManager dataManager = new DataManager();
	List<double[]>datas = dataManager.getData();
	int count = 20;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=DTh6xZQu6PDuDLnFK7pfv03E"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<title>热门微博热力图</title>
<style type="text/css">
ul,li {
	list-style: none;
	margin: 0;
	padding: 0;
	float: left;
}

html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px;
	font-family: "微软雅黑";
}

#container {
	height: 100%;
	width: 100%;
}

#r-result {
	width: 100%;
}
</style>
</head>
<body>
	<div id="container"></div>
	<div id="r-result">
		<input type="button"  onclick="openHeatmap();" value="显示热力图" style="position: fixed;top:0px;left:0px; height: 50px"/>
		<input type="button"  onclick="closeHeatmap();" value="关闭热力图" style="position: fixed;top:0px;left:120px; height: 50px" /> 

	</div>
</body>
</html>
<script type="text/javascript">
	var points =[
	<%for(double[] temp :datas){%>
		{"lng" :<%=temp[1]%>,
		"lat" :<%=temp[0]%>,
		"count" :<%=count%>},
	<%}%>
	
	]
	
</script>
<script type="text/javascript">
	var map = new BMap.Map("container"); // 创建地图实例

	var point = new BMap.Point(114.3162, 30.581084);
	map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别
	map.enableScrollWheelZoom(); // 允许滚轮缩放
/*
	var points = [ {
		"lng" : 114.3212,
		"lat" : 30.577084,
		"count" : 10
	}, {
		"lng" : 114.3192,
		"lat" : 30.551084,
		"count" : 10
	}, {
		"lng" : 114.3172,
		"lat" : 30.582284,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.585584,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.587084,
		"count" : 10
	}, {
		"lng" : 114.3152,
		"lat" : 30.579084,
		"count" : 10
	}, {
		"lng" : 114.3132,
		"lat" : 30.576084,
		"count" : 10
	}, {
		"lng" : 114.3172,
		"lat" : 30.581994,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.582054,
		"count" : 10
	}, {
		"lng" : 114.3112,
		"lat" : 30.590084,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.581084,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.581004,
		"count" : 10
	}, {
		"lng" : 114.3160,
		"lat" : 30.581584,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.579984,
		"count" : 10
	}, {
		"lng" : 114.3169,
		"lat" : 30.581084,
		"count" : 10
	}, {
		"lng" : 114.3162,
		"lat" : 30.581084,
		"count" : 10
	}, {
		"lng" : 114.3092,
		"lat" : 30.581184,
		"count" : 10
	}, {
		"lng" : 114.3099,
		"lat" : 30.581324,
		"count" : 10
	}, {
		"lng" : 114.3168,
		"lat" : 30.581064,
		"count" : 10
	}, {
		"lng" : 114.3165,
		"lat" : 30.579004,
		"count" : 10
	}, ];
*/
	/*points
	var points = new Array();
	if (window.DOMParser)
	{ // Firefox, Chrome, Opera, etc.
	    parser=new DOMParser();
	    fso=parser.parseFromString("text/xml");
	}
	else // Internet Explorer
	{
	    fso = new ActiveXObject("Scripting.FileSystemObject");
	    fso.async = false;
	    fso.loadXML(xml);
	}

	    //var fso=new ActiveXObject(Scripting.FileSystemObject);
	var f = fso.OpenTextFile("F:\\WBtxt\\out_geohot1.txt", ForReading);

	for(var i=0;i<1821;i++)
	{
	    var str = f.Readline();
	    temp=str.spilt(",");
	    points[i].lat=temp[0];
	    points[i].lng=temp[1];
	    points[i].count=50;
	}*/

	//document.getElementById("testtt").innerHTML = "HHHHHH";
	//document.getElementById("testtt").innerHTML=points[1].lat;

	if (!isSupportCanvas()) {
		alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
	}
	//详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
	//参数说明如下:
	/* visible 热力图是否显示,默认为true
	 * opacity 热力的透明度,1-100
	 * radius 势力图的每个点的半径大小   
	 * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
	 *	{
	 .2:'rgb(0, 255, 255)',
	 .5:'rgb(0, 110, 255)',
	 .8:'rgb(100, 0, 255)'
	 }
	 其中 key 表示插值的位置, 0~1.
	 value 为颜色值.
	 */
	heatmapOverlay = new BMapLib.HeatmapOverlay({
		"radius" : 20
	});
	map.addOverlay(heatmapOverlay);
	heatmapOverlay.setDataSet({
		data : points,
		max : 100
	});
	//是否显示热力图
	function openHeatmap() {
		heatmapOverlay.show();
	}
	function closeHeatmap() {
		heatmapOverlay.hide();
	}
	closeHeatmap();
	function setGradient() {
		/*格式如下所示:
		 {
		 0:'rgb(102, 255, 0)',
		 .5:'rgb(255, 170, 0)',
		 1:'rgb(255, 0, 0)'
		 }*/
		var gradient = {};
		var colors = document.querySelectorAll("input[type='color']");
		colors = [].slice.call(colors, 0);
		colors.forEach(function(ele) {
			gradient[ele.getAttribute("data-key")] = ele.value;
		});
		heatmapOverlay.setOptions({
			"gradient" : gradient
		});
	}
	//判断浏览区是否支持canvas
	function isSupportCanvas() {
		var elem = document.createElement('canvas');
		return !!(elem.getContext && elem.getContext('2d'));
	}
	//    var  mapStyle ={
	//        features: ["road", "building","water","land"],//隐藏地图上的poi
	//        style : "midnight"  //设置地图风格为高端黑
	//    }
	//    map.setMapStyle(mapStyle);
</script>