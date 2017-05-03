package com.trans.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TransFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputFileName = "F:\\WBtxt\\geohot5.txt";
		String outputFileName = "F:\\WBtxt\\out_geohot5.txt";
		File inputFile = new File(inputFileName);
		File outputFile = new File(outputFileName);
		if (outputFile.exists())
			outputFile.delete();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream input = new FileInputStream(inputFile);
			br = new BufferedReader(new InputStreamReader(input));

			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outputFileName, true)));
			String data = null;
			while ((data = br.readLine()) != null) {
				String[] locs = data.split(",");
				double lon = Double.parseDouble(locs[0]);
				double lat = Double.parseDouble(locs[1]);
				double[] result = Trans.bd_encrypt(lat, lon);
				bw.append(result[0] + "," + result[1] + "\n");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

	}

}
