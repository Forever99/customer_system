package com.zhku.jsj144.zk.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.service.CustomersService;

public class SelectConditionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("text/html;charset=utf-8");
		
		//条件查询
		//条件：conditionName  关键字：keyWords
		String conditionName=request.getParameter("conditionName");//查询条件
		String keyWords=request.getParameter("keyWords");//查询关键字

		CustomersService customersService=new CustomersService();
		List customersList=customersService.selectCondition(conditionName,keyWords);
		
		//保存查询结果，并显示在查询结果页面上
		request.setAttribute("CustomersList", customersList);
		request.getRequestDispatcher("/findAllCustomer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
