package com.zhku.jsj144.zk.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/*
 * 工具类
 * 1、获得数据源【c3p0】
 * 2、获得c3p0连接池中的一个连接
 */
public class JdbcUtils {

	private static DataSource ds=new ComboPooledDataSource();//数据库连接池的数据源
	
	public static DataSource getDatasourse(){
		return ds;//返回数据库连接池的数据源
	}
	public Connection getConnection() throws SQLException{
		return ds.getConnection();//从数据库连接池中获取一个连接
	}
}
