package com.chengsj.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

	public DataManager() {
	}

	public int getA() {
		return 0;
	}

	public List<int[]> getArray() {
		List<int[]> result = new ArrayList<int[]>();
		result.add(new int[] { 0, 1, 2, 3 });
		List<HashMap<String, Double>> temp;
		return result;
	}

	public List<double[]> getData() {
		List<double[]> result = new ArrayList<double[]>();
		String inputFileName = "F:\\WBtxt\\out_geohot5.txt";
		File inputFile = new File(inputFileName);
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream input = new FileInputStream(inputFile);
			br = new BufferedReader(new InputStreamReader(input));
			String data = null;
			while ((data = br.readLine()) != null) {
				String[] locs = data.split(",");
				double lon = Double.parseDouble(locs[0]);
				double lat = Double.parseDouble(locs[1]);
				result.add(new double[]{lon,lat});
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return result;

	}
}
