package com.zhku.jsj144.zk.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

//事务管理工具类
//确保事务管理过程中使用的是同一个connection

public class TransactionUtils {

	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();//线程本地类
	private static DataSource ds=JdbcUtils.getDatasourse();
	public static DataSource getDataSource(){//数据源
		return ds;
	}
	
	//获得connecion对象
	public static Connection getConnection(){
		
//		说明：先判断tl.get()是否是null，
//		如果是null，则说明当前线程还没有对应的Connection对象，这时创建一个Connection对象并添加到本地线程变量中；
//		如果不为null，则说明当前的线程已经拥有了Connection对象，直接使用就可以了。
//		这样，就保证了不同的线程使用线程相关的Connection，而不会使用其它线程的Connection
		
		Connection conn=tl.get();
		if(conn==null){
			try {
				conn=ds.getConnection();//从连接池中取一个连接出来
				//将取出来的连接放到tl中
				tl.set(conn);//设置本地线程类
				return conn;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
//	//开启事务
//	public void startTransaction(){
//		if(con==null){
//			con=getConnection();//获得连接
//		}
//		con= (Connection) tl.get();
//		try {
//			con.setAutoCommit(false);//开启事务
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	// 开启 事务  
	// 结果 就是 返回了 一个 connection对象, 并且 将 返回的 connection放到了 threadlocal中 , 
	
	public static void startTransaction(){
		try {
			Connection conn = tl.get();//确保连接来自threaLocal（本地线程类）
			if(conn==null){
				conn = getConnection();
//			tl.set(conn);
			}
			conn.setAutoCommit(false);//开启 事务  
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//回滚事务
	public static void rollback(){
		try {
			Connection conn = tl.get();//确保连接来自threaLocal（本地线程类）
			if(conn==null){
				conn = getConnection();
//			tl.set(conn);
			}
			conn.rollback();//回滚事务
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//提交事务
	public static void commit(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//			tl.set(conn);
			}
			conn.commit();//提交事务
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//释放资源
	public static void relase(){
		try {
			Connection conn = tl.get();
			if(conn!=null){
				conn.close();//释放资源
				tl.remove();//移除当前connection
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
