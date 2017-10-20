package com.zhku.jsj144.zk.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.service.CustomersService;

public class FindAllServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//查询所有用户信息
		req.setCharacterEncoding("utf-8");//设置编码，防止中文乱码
		
		CustomersService customerService=new CustomersService();//业务层对象
		List resultList=customerService.selectAll();//查询结果集
		///保存查询结果，转发到客户信息查询显示页面
		req.setAttribute("CustomersList", resultList);
		req.getRequestDispatcher("/findAllCustomer.jsp").forward(req, resp);//转发到查询客户信息页面
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
