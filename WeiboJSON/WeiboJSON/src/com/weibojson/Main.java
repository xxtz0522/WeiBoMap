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

		// ��ʾ��ǰʱ�䣬ֻ��Ϊ�˱Ƚϲ�ͬJSON�������Ľ����ٶȣ�û��ʵ������
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // ��ȡ��ǰʱ��
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = s.format(c.getTime()); // ��ʽ����ǰʱ��
		System.out.println("startTime: " + startDate);

		String dateString = "";
		String FileName = "";

		String uid;
		String latString = "";
		String lngString = "";
		String titleString = "";

		String latlngString = "";

		// ѭ������������json�ļ�
		for (int t = 1; t <= 30; t++) {

			if (t < 10) {
				dateString = "0" + t;
				
			}else {
				dateString = t+"";
			}
			FileName = "F:\\WB\\11" + dateString + "�人��.json";

			// String FileName = "D:\\1102�人��.json";
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

				// �����û�ID��ͬһ�û�ID��ͬ
//				String idString = jsonObject.getString("idstr");
				uid = jsonObject.getJSONObject("user").getString("id");

				// �����ֻ��ͺ�
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
					phone = "����Ϣ";
				}*/
				
				//�����ı�
				
//				String textString=jsonObject.getJSONObject("geo").getString("text");
				String textString=jsonObject.getString("text").toString();
				//String textString=jsonObject.getString("text");
				
				// ������γ��
				latlngString = jsonObject.getJSONObject("geo").getString(
						"coordinates");
				int index = latlngString.indexOf(",");
				latString = latlngString.substring(1, index);
				lngString = latlngString.substring(index + 1,
						latlngString.length() - 1);

				// ����΢�����ݾ�γ�ȸ����ĵ���λ�����ƣ��硰�人�С�
				// �����ļ���JSONObject��һ����Ҫ���ж�
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
						titleString = "�޵���";
					}
				} else {
					titleString = "�޵���";
				}

				
				
				
				// �������������ݴ������ݿ�
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
			// �õ�ʱ�䣬����ͬ��
			Calendar c2 = Calendar.getInstance(TimeZone
					.getTimeZone("GMT+08:00")); // ��ȡ������ʱ��
			String endDate = s.format(c2.getTime()); // ��ǰ����
			System.out.println("_end_Time: " + endDate);

		}
	}

}
