package com.jz.sm.framework.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBUtil {
	private Connection con;
	private Statement sta;
	private ResultSet rs;
	
	private Connection getConnection() {
		Connection myCon = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=librarymis_new";
			myCon = DriverManager.getConnection(url,"sa","sa");
			System.out.println("获得一次连接！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myCon;
	}
	
	public DBUtil() {
		List<Connection> list = new ArrayList<Connection>();
		for (int i = 0; i < 5; i++) {
			list.add(this.getConnection());
		}
		this.con = list.get(0);
	}
		
	public int executeUpdate(String sql) {
		int n = -1;
		try {
			this.sta = this.con.createStatement();
			n = this.sta.executeUpdate(sql);
			if (n != -1) {
				System.out.println("数据库更新操作成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	public ResultSet excuteQuary(String sql) {
		try {
			this.sta = this.con.createStatement();
			this.rs = this.sta.executeQuery(sql);
			System.out.println("数据库查询操作成功！");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.rs;
	}
	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (sta != null) {
				sta.close();
				sta = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
			System.out.println("数据库关闭成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
