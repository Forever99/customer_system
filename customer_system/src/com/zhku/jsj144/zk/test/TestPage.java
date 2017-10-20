package com.zhku.jsj144.zk.test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.utils.JdbcUtils;

//测试逻辑分页和物理分页
public class TestPage {

	//逻辑分页
//	在sql查询时，先从数据库检索出所有数据的结果集。在程序内，通过逻辑语句获得分页需要的的数据
//	例如: 检索11-20条 userList.subList(10,20);

	@Test
	public void logicalPage(){
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			List<Customers> customersList = qr.query("select * from customers", new BeanListHandler(Customers.class));
		
			//检索第11-20条记录 【10-19】（因为是从0开始计算）
			customersList=customersList.subList(10, 20);
			for (int i = 0; i < customersList.size(); i++) {
				System.out.println(customersList.get(i).toString());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//物理分页
//	在sql查询时，从数据库只检索分页需要的数据
//	通常不同的数据库有着不同的物理分页语句。mysql物理分页，采用limit关键字
//	例如：检索11-20条 select * from user limit 10,10 ;
//	其中第1个10：表示开始索引位置
//	其中第2个10：表示记录数目
	@Test
	public void physicalPage(){
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			//检索第11-20条记录 【10-19】（因为是从0开始计算）
			List<Customers> customersList = qr.query("select * from customers limit ?,?", new BeanListHandler(Customers.class),10,10);
			for (int i = 0; i < customersList.size(); i++) {
				System.out.println(customersList.get(i).toString());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//插入批量数据
	@Test
	public void testInsert(){
		//调用工具类进行插入操作
		try{
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		
		for(int i=0;i<80;i++){
			//id，用UUID工具进行生成
			String id=getId();
			//参数数组
			Object[] params=new Object[]{id,"张三"+i+i,"男",
					"1990-0-09","asdf@com","12344455",
					"学习，睡觉","vip","12344"};
			qr.update("insert into customers values(?,?,?,?,?,?,?,?,?)", params);//插入操作
		}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	

	//生成并获取id
	private String getId(){
		String id=UUID.randomUUID().toString();
		id=id.replace("-", "");
		return id;
	}
}
