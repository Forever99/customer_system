package com.zhku.jsj144.zk.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.service.CustomersService;

public class GetUpdateInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//得到修改的相关信息
		String id = request.getParameter("id");
		
		CustomersService customersService=new CustomersService();
		Customers customers=customersService.getUpdateInfo(id);
		
		//保存查询结果，并将查询结果显示到修改信息页面进行显示
		request.setAttribute("customers", customers);
		request.getRequestDispatcher("/updateInfo.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
