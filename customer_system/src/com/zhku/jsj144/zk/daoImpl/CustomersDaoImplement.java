package com.zhku.jsj144.zk.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.zhku.jsj144.zk.dao.CustomersDao;
import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.utils.JdbcUtils;
import com.zhku.jsj144.zk.utils.TransactionUtils;

public class CustomersDaoImplement implements CustomersDao {

	//插入用户信息
	/* (non-Javadoc)
	 * @see com.zhku.jsj144.zk.dao.CustomersDao#insert(com.zhku.jsj144.zk.domain.Customers)
	 */
	@Override
	public int insert(Customers customers){
		//调用工具类进行插入操作
//		QueryRunner qr=new QueryRunner();//没有获取连接
		try{
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		
		//id，用UUID工具进行生成
		String id=getId();
		
		//参数数组
		Object[] params=new Object[]{id,customers.getName(),customers.getGender(),
				customers.getBirthday(),customers.getEmail(),customers.getCellphone(),
				customers.getPreference(),customers.getType(),customers.getDescription()};
		
		int count = qr.update("insert into customers values(?,?,?,?,?,?,?,?,?)", params);
		System.out.println("count:"+count);
		return count;//返回插入结果
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

	//查询用户信息
	/* (non-Javadoc)
	 * @see com.zhku.jsj144.zk.dao.CustomersDao#selectAll()
	 */
	@Override
	public List selectAll() {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			//返回查询结果集
			List resultList = qr.query("select * from customers", new BeanListHandler(Customers.class));
			return  resultList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//删除一条记录
	/* (non-Javadoc)
	 * @see com.zhku.jsj144.zk.dao.CustomersDao#deleteOne(java.lang.String)
	 */
	@Override
	public void deleteOne(String id) {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			qr.update("delete from customers where id=?", id);
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
		
	}

	//删除一条记录，并手动管理事务
	@Override
	public void deleteOneTransaction(Connection con,String id) {
		QueryRunner qr=new QueryRunner();
		try {
			qr.update(con,"delete from customers where id=?", id);
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//删除一条记录，并手动管理事务[写法2]
	@Override
	public void deleteOneTransaction2(String id) {
		QueryRunner qr=new QueryRunner();
		try {
			//ThreadLocal类【线程本地类】的使用：确保同一个线程使用的始终是同一个Connection对象
			qr.update(TransactionUtils.getConnection(),"delete from customers where id=?", id);
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//查询一条用户记录信息，用于修改使用
	@Override
	public Customers getUpdateInfo(String id) {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		Customers customers=null;
		try {
			customers = qr.query("select * from customers where id=?", new BeanHandler(Customers.class),id);
			return customers;
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//修改一条用户记录信息
	@Override
	public void update(Customers customers) {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		Object[] params={customers.getName(),customers.getGender(),customers.getBirthday(),
				customers.getCellphone(),customers.getEmail(),customers.getPreference(),
				customers.getType(),customers.getDescription(),customers.getId()};
		try {
			qr.update("update customers set name=?,gender=?,birthday=?,cellphone=?,email=?,"
					+ "preference=?,type=?,description=? where id=?", params);
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//条件查询
	@Override
	public List selectCondition(String conditionName, String keyWords) {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			//select * from customers where name like '% 张 %'
			List customersList=qr.query("select * from customers where "+conditionName+" like ?", new BeanListHandler(Customers.class),"%"+keyWords+"%");
			return  customersList;
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//查找总记录个数
	@Override
	public int selectAllCount() {
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
//			//异常：java.lang.ClassCastException: java.lang.Long cannot be cast to java.lang.Integer
//			int count = (Integer) qr.query("select count(*) from customers", new ScalarHandler());
			
			long count = (Long) qr.query("select count(*) from customers", new ScalarHandler());
			return (int)count;//此处再强制转换
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

	//分页查询
	//	物理分页
	//	在sql查询时，从数据库只检索分页需要的数据
	//	mysql物理分页，采用limit关键字
	//	例如：检索11-20条 select * from user limit 10,10 ;
	@Override
	public List<Customers> selectPageList(int start, int pageRecord) {
		//参数：初始索引，每页的记录数
		QueryRunner qr=new QueryRunner(JdbcUtils.getDatasourse());
		try {
			//eg: select * from user limit 10,10 ;
			List<Customers> pageList = qr.query("select * from customers limit ?,?", new BeanListHandler(Customers.class),start,pageRecord);
			return pageList;
		} catch (SQLException e) {
			throw new RuntimeException(e);//抛出运行时异常
		}
	}

}
