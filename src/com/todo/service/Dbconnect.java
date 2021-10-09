package com.todo.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Dbconnect {
	private static Connection conn = null;
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				
				String dbFile = "todolist.db";
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				System.out.println("\n*** 데이터 조회 ***");
				Statement stat1 = conn.createStatement();
				String sql1 = "select * from list";
				ResultSet rsl = stat1.executeQuery(sql1);
				while(rsl.next()) {
					int id = rsl.getInt("id");
					String title = rsl.getString("title");
					String desc = rsl.getString("memo");
					String category = rsl.getString("category");
					String current_date = rsl.getString("current_date");
					String due_date = rsl.getString("due_date");
					System.out.println(id + " [" + category + "] " + title + " " + desc + " " + due_date + " " + current_date);
				}
				stat1.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(conn != null) {
					try {
						conn.close();
					} catch(Exception e) {}
				}
			}
		}
		return conn;
	}
}
