package com.weibojson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertDB {
	
	//public static final String DBDRIVER="com.mysql.jdbc.Driver";  
	public static final String DBDRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//public static final String DBURL="jdbc:sqlserver://CSJ-PC/Weibodb"; 
	public static final String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=Weibodb";
    public static final String DBUSER="sa";  
    public static final String DBPASSWORD="xiaomonv12";  
    
    public void Insert2DB( String[] temp) throws Exception{
    	temp = symbolProcess(temp);
    	 Connection conn = null ;  
         Class.forName(DBDRIVER);  
         conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);  
         Statement stmt = conn.createStatement();  
         String v= "VALUES(";
         for (int i = 0; i < temp.length-1; i++) {
			v+="'"+temp[i]+"',";
		}
         v+="'"+temp[temp.length-1]+"');";

       System.out.println(v);
         String sql = "INSERT INTO weibo3(userId,position,text,lat,lng)"+v;  
//         System.out.println(sql);
         stmt.executeUpdate(sql);  
 
         stmt.close();  
         conn.close();
    }

	/**ÌØÊâ·ûºÅ´¦Àí
	 * @param input
	 */
	private String[] symbolProcess(String[] input) {
		String[] result = new String[input.length];
		for(int i = 0;i<input.length;i++){
			result[i] = input[i].replace("'", "''");
		}
		if(result[2].contains("http"))
			result[2] = result[2].split("http")[0];
		return result;
	}

}
