package com.zhku.jsj144.zk.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.service.CustomersService;

public class DeleteAllServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//删除多条记录【批量删除】--考虑事务处理机制
		//获取批量删除的id号
		String choose[]=request.getParameterValues("choose");//选择的choose数量
		
		//进行批量删除
		CustomersService customersService=new CustomersService();
		customersService.deleteAll(choose);//批量删除
		
		//重新查询记录
		request.getRequestDispatcher("/FindAllServlet").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
