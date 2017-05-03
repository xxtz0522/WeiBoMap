package com.trans.file;


public class Trans {

	/**
	 * @param args
	 */

	public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	/**
	 * @param gg_lat
	 * @param gg_lon
	 * @return
	 */
	public static double[] bd_encrypt(double gg_lat, double gg_lon) {
		double[] result = new double[2];
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		result[0] = z * Math.cos(theta) + 0.0065;
		result[1] = z * Math.sin(theta) + 0.006;
		return result;
	}

	void bd_decrypt(double bd_lat, double bd_lon, double gg_lat, double gg_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		gg_lon = z * Math.cos(theta);
		gg_lat = z * Math.sin(theta);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
