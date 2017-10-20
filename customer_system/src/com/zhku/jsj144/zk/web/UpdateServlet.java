package com.zhku.jsj144.zk.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.service.CustomersService;

public class UpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("text/html;charset=utf-8");
				
		//更新要修改的表单信息
		CustomersService customersService=new CustomersService();
		
		//封装表单信息
		try {
			Customers customers=new Customers();
			BeanUtils.populate(customers, request.getParameterMap());
			//额外处理
			
			//对于爱好preference是多个值，那么使用beanutils封装时，会丢失数据，需要手动处理
			String[] values=request.getParameterValues("preference");
			if(values!=null){
				String preference=Arrays.toString(values);//将数组变为字符串[上班,工作]
				//进一步处理：将 [上班,工作]  ---> 上班,工作
				preference=preference.substring(1, preference.length()-1);
				customers.setPreference(preference);//设置爱好
			}
			
			//更改信息
			customersService.update(customers);
			
			//重新显示查询信息
			request.getRequestDispatcher("/FindAllServlet").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
