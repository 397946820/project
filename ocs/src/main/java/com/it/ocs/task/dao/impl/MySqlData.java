

package com.it.ocs.task.dao.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class MySqlData {
	
	private Connection conn;
	private Statement stmt;
	
	public MySqlData(){
		
	}
	/**
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * 描述：初始化连接mysql数据库
	 */
	public MySqlData(String url, String user, String password){
		
		
		try {	
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			this.conn = (Connection) DriverManager.getConnection(url,user,password);
			
			this.stmt = (Statement) conn.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 * 描述：执行sql，获取数据
	 */
	public ResultSet getMySqlDate(String sql){
		
		ResultSet resultSet = null;
		
		try {
			
			resultSet = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public ResultSet selectMySqlDate(String url,String user,String password,String sql){
		Connection connection;
		Statement statement;
		ResultSet result = null;
		try {	
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
				
			connection = (Connection) DriverManager.getConnection(url,user,password);
				
			statement = (Statement) connection.createStatement();
				
			connection.setAutoCommit(false);
			statement.executeQuery("set time_zone='+1:00'");
			result = statement.executeQuery(sql);
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
}