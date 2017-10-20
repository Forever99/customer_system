package com.zhku.jsj144.zk.dao;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import com.zhku.jsj144.zk.domain.Customers;
//将Dao层抽象成一个接口，然后再写它的实现类
//目的：为了Service层与Dao层的解耦合。
//原因：每次Service调用一个方法，都要new一个Dao层对象，这样，就增加了Service层和Dao层的耦合性
//因此，通过书写接口，然后书写配置文件，利用反射技术，得到Dao层的对象，而不用每次new对象
//好处：提高了扩展性。

//应用场景：解耦合【接口+配置文件+反射技术】
//	某个模块不会写，自己可以定义一个接口，然后外包给别人去实现，利用解耦合技术，然后把别人实现好的方法，即：接口的实现类
//继承进来，在进行书写相应的配置文件，即可达成目的

public interface CustomersDao {

	//插入用户信息
	public abstract int insert(Customers customers);

	//查询用户信息
	public abstract List selectAll();

	//删除一条记录
	public abstract void deleteOne(String id);

	//删除一条记录，并手动管理事务
	public abstract void deleteOneTransaction(Connection con,String id);

	//删除一条记录，并手动管理事务[写法2]
	public abstract void deleteOneTransaction2(String id);

	//查询一条用户记录信息，用于修改使用
	public abstract Customers getUpdateInfo(String id);

	//修改一条用户记录信息
	public abstract void update(Customers customers);

	//条件查询
	public abstract List selectCondition(String conditionName, String keyWords);

	//查找总记录个数
	public abstract int selectAllCount();

	//分页查询
	public abstract List<Customers> selectPageList(int start, int pageRecord);

}