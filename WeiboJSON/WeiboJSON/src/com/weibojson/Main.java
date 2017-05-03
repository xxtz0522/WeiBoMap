package com.weibojson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// 显示当前时间，只是为了比较不同JSON解析包的解析速度，没有实际意义
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // 获取当前时间
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = s.format(c.getTime()); // 格式化当前时间
		System.out.println("startTime: " + startDate);

		String dateString = "";
		String FileName = "";

		String uid;
		String latString = "";
		String lngString = "";
		String titleString = "";

		String latlngString = "";

		// 循环解析电脑中json文件
		for (int t = 1; t <= 30; t++) {

			if (t < 10) {
				dateString = "0" + t;
				
			}else {
				dateString = t+"";
			}
			FileName = "F:\\WB\\11" + dateString + "武汉市.json";

			// String FileName = "D:\\1102武汉市.json";
			// String FileName = "D:\\test.json";

			int i = 1;
			// File file = new File ( FileName ) ;
			// BufferedReader reader = new BufferedReader ( new FileReader (
			// file ) ) ;
			String tempString = null;

			FileInputStream fileInputStream = new FileInputStream(FileName);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			while ((tempString = bufferedReader.readLine()) != null) {
				// JSONObject jsonObject = new JSONObject.parse(tempString);
				JSONObject jsonObject = JSON.parseObject(tempString);

				// String JsonContext = new ReadJSONFiles().ReadFile(FileName);
				// JSONArray jsonArray = JSONArray.parseArray(JsonContext);
				// int size = jsonArray.size();
				// System.out.println("Size: " + size);
				//
				// for (int i = 0; i < size; i++) {
				// JSONObject jsonObject = jsonArray.getJSONObject(i);

				// 解析用户ID，同一用户ID相同
//				String idString = jsonObject.getString("idstr");
				uid = jsonObject.getJSONObject("user").getString("id");

				// 解析手机型号
				/*String source = jsonObject.getString("source");
				String phone = "";
				if (source.contains("<")) {
					int index1 = source.indexOf(">");
					int index21 = source.indexOf("<");
					int index22 = source.indexOf("<", index21 + 1);
					phone = (String) source.substring(index1 + 1, index22);

					if (phone.contains("'")) {

						String regexp = "\'";
						phone = phone.replaceAll(regexp, "");
					}
				} else {
					phone = "无信息";
				}*/
				
				//解析文本
				
//				String textString=jsonObject.getJSONObject("geo").getString("text");
				String textString=jsonObject.getString("text").toString();
				//String textString=jsonObject.getString("text");
				
				// 解析经纬度
				latlngString = jsonObject.getJSONObject("geo").getString(
						"coordinates");
				int index = latlngString.indexOf(",");
				latString = latlngString.substring(1, index);
				lngString = latlngString.substring(index + 1,
						latlngString.length() - 1);

				// 解析微博根据经纬度给出的地理位置名称，如“武汉市”
				// 由于文件中JSONObject不一，需要做判断
				if (jsonObject.containsKey("annotations")) {
					JSONObject geoPointArray = jsonObject.getJSONArray("annotations").getJSONObject(0);
					if (geoPointArray.containsKey("place")) {
						if (geoPointArray.getJSONObject("place").containsKey("title")) {
							titleString = geoPointArray.getJSONObject("place").getString("title");
							if (titleString.contains("'")) {
								String regexp = "\'";
								titleString = titleString.replaceAll(regexp, "");
							}
						}
					} else {
						titleString = "无地区";
					}
				} else {
					titleString = "无地区";
				}

				
				
				
				// 将解析出的数据存入数据库
				String[] data = { uid, titleString, textString, latString,
						lngString };
				InsertDB insertDB = new InsertDB();
				insertDB.Insert2DB(data);

//				System.out.println("[" + FileName + "-" + i + "] idstr="
//						+ uid);
				// System.out.println("[" + i + "] lat=" + latString);
				// System.out.println("[" + i + "] lng=" + lngString);
				// System.out.println("[" + i + "] title=" + titleString);
				// System.out.println("[" + i + "] phone=" + phone);
				++i;
			}
			bufferedReader.close();
			// 得到时间，作用同上
			Calendar c2 = Calendar.getInstance(TimeZone
					.getTimeZone("GMT+08:00")); // 获取东八区时间
			String endDate = s.format(c2.getTime()); // 当前日期
			System.out.println("_end_Time: " + endDate);

		}
	}

}
