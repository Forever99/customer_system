package com.zhku.jsj144.zk.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.service.CustomersService;

public class DeleteOneServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String id=req.getParameter("id");//要删除记录的id
		
		//删除一条记录【单个客户信息】
		CustomersService customerService=new CustomersService();//业务层对象
		customerService.deleteOne(id);
		//重新查询记录
		req.getRequestDispatcher("/FindAllServlet").forward(req, resp);//返回删除一条记录后的查询结果情况
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
