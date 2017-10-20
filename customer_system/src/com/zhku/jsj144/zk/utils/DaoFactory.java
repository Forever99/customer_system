package com.zhku.jsj144.zk.utils;

import java.util.ResourceBundle;
//用于创建到的工厂类
//工厂类很多时候会被弄成单例模式

public class DaoFactory {

	private DaoFactory(){};
	//一个工厂类实例
	private static DaoFactory instance=new DaoFactory();
	//获取工厂类实例
	public static DaoFactory getInstance(){
		return instance;
	}
	
	//用于返回传进来的一个类的实例对象出去
	public <T> T createDao(Class<T> t){
		//CustomerDao.class --> CustomerDao
		String simpleName=t.getSimpleName();//获取自己吗对象的名字
		//读取一个配置文件
		//CustomersDao=com.zhku.jsj144.zk.daoImpl.CustomersDaoImplement
		String clazzName=ResourceBundle.getBundle("dao").getString(simpleName);
		
		try {
			return (T)Class.forName(clazzName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * 自己写的方法
	 * 
	public static Object getDao(){
		
		ResourceBundle bundle=ResourceBundle.getBundle("dao");//读取dao.properties配置文件
		//配置文件内容：CustomersDao=com.zhku.jsj144.zk.daoImpl.CustomersDaoImplement
		//左边：接口  右边：接口的实现类
		String implClass=bundle.getString("CustomersDao");
		
		//通过类的字符串名，利用反射，创建实例对象
		Class<?> clazz;
		try {
			clazz = Class.forName(implClass);
			Object instance = clazz.newInstance();//实例对象
			return instance;//返回创建的实例对象：CustomersDaoImplement实例对象
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
}
	*/
}
