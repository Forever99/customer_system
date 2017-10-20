package com.zhku.jsj144.zk.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.zhku.jsj144.zk.dao.CustomersDao;
import com.zhku.jsj144.zk.daoImpl.CustomersDaoImplement;
import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.domain.PageBean;
import com.zhku.jsj144.zk.utils.DaoFactory;
import com.zhku.jsj144.zk.utils.JdbcUtils;
import com.zhku.jsj144.zk.utils.TransactionUtils;

public class CustomersService {

	//解耦合技术    [反射技术+配置文件+接口]
	//对比分析：
	//以前代码耦合的时候：写代码是直接new对象，那么需要以来具体的类，如果没有这些具体的类，这
	//么这个业务层默认的程序员就没有办法继续做开发
	//现在，解耦合：引入了接口，通过接口去引用了返回的实现类，将来实现类有了之后，只需要将实现类集成进来，
	//然后配置文件修改以下即可，不用再去修改源代码了。
	
	private CustomersDao CustomersDao=DaoFactory.getInstance().createDao(CustomersDao.class);
//	private CustomersDao CustomersDao=new CustomersDao();
	public int insert(Customers customers){
//		CustomersDao CustomersDao=new CustomersDao();
		
//		CustomersDao CustomersDao;
//		try {
//			CustomersDao = (CustomersDao) DaoFactory.getDao();//解耦合技术获得
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
		int count = CustomersDao.insert(customers);
		return count;
	}

	//查询所有用户信息
	public List selectAll() {
		List resultList=CustomersDao.selectAll();//查询结果集
		return resultList;
	}

	//删除一条记录，一个客户的信息
	public void deleteOne(String id) {
		CustomersDao.deleteOne(id);
	}

//	//批量删除
//	public void deleteAll(String[] choose) {
//		//开启事务处理，循环进行删除，每次删除一条记录，如果删除失败，则进行回滚事务，否则，最后进行提交事务，并关闭资源
//		
//		//问题思考：Connection对象应该出现待在dao层，而不应该出现在service层，这样导致service层和到层耦合了
//		//思考：如何进行解耦呢？
//		//这样Service层就不会使用dao层才会使用的Connection对象了。
//		//方法：ThreadLocal类[线程本地类]的使用，可以解决这个问题
//		
//		
//		//开启事务，需要获得Connection对象
//		Connection con=null;
//		try {
//			con=JdbcUtils.getDatasourse().getConnection();//获得连接对象
//			con.setAutoCommit(false);//开启事务
//			
//			for(int i=0;i<choose.length;i++){
//				//手动管理事务，要自己传递Connection对象过去，确保Connection是唯一的
//				CustomersDao.deleteOneTransaction(con,choose[i]);//每次删除一条记录
//			}
//			con.commit();//提交事务
//		} catch (SQLException e) {
//			try {
//				con.rollback();//回滚事务
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			throw new RuntimeException("批量删除失败");
//		}
//	}
	
	//完善后的deleteAll(String[] choose)方法
	//批量删除
	public void deleteAll(String[] choose) {
		//开启事务处理，循环进行删除，每次删除一条记录，如果删除失败，则进行回滚事务，否则，最后进行提交事务，并关闭资源

		//确保同一个线程使用的始终是同一个Connection对象
		try{
			//开启事务
			TransactionUtils.startTransaction();
			//批量删除操作
			for(int i=0;i<choose.length;i++){
				//手动管理事务，要自己传递Connection对象过去，确保Connection是唯一的
				CustomersDao.deleteOneTransaction2(choose[i]);//每次删除一条记录
			}
			//提交事务
			TransactionUtils.commit();
		}catch(Exception e){
			TransactionUtils.rollback();//回滚操作
			throw new RuntimeException("批量删除失败...");
		}finally{
			//释放资源
			TransactionUtils.relase();
		}
	}

	public Customers getUpdateInfo(String id) {
		Customers customers=CustomersDao.getUpdateInfo(id);
		return customers;
	}

	public void update(Customers customers) {
		CustomersDao.update(customers);
	}

	public List selectCondition(String conditionName, String keyWords) {
		List customersList=CustomersDao.selectCondition(conditionName,keyWords);
		return customersList;
	}

	//分页查询业务逻辑，目的获取分页pageBean的5个信息
	//分页推导公式：
	//1.  总页数=（总记录数+每页记录数-1）/每页记录数
	//2.  初始索引=(当前页数-1)*每页记录数
	public PageBean pageQuery(String num) {
		
		int totalRecord=CustomersDao.selectAllCount();//总记录数
		int pageRecord=10;//每页记录数【默认是每页10条记录】
		int totalPage=(totalRecord+pageRecord-1)/pageRecord;//总页数
		int currentPage=Integer.parseInt(num);//当前页数
		
		//主要是初始索引参数，以及每页的记录数作为参数
		int start=(currentPage-1)*pageRecord;//开始索引
		List<Customers> dataList=CustomersDao.selectPageList(start,pageRecord);//当前页记录数
		
		//将pageBean的信息封装起来
		PageBean pageBean=new PageBean();
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setPageRecord(pageRecord);
		pageBean.setCurrentPage(currentPage);
		pageBean.setDataList(dataList);
		
		return pageBean;
	}
}
