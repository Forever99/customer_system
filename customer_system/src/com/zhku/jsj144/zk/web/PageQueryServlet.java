package com.zhku.jsj144.zk.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.zk.domain.PageBean;
import com.zhku.jsj144.zk.service.CustomersService;

public class PageQueryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("text/html;charset=utf-8");

		//分页查询客户信息
		String currentPage=request.getParameter("currentPage");//当前页数
		//获取分页信息
		CustomersService customersService=new CustomersService();
		//思考：如何只是单纯的把查询的信息带过去，就不能实现分页查询
		//原因：每次查询都是一个CustomersList。是固定的，下一次则无法使用
		//我们无法知道，每页记录数，总页数，总记录数，当前页数据，只是知道当前页数，所以无法实现分页查询
		//结论：我们要一个PageBean，去封装我们要的数据：每页记录数，总页数，总记录数，当前页数，当前页数据。
		//用于我们下一次查询时使用
		PageBean pageBean=customersService.pageQuery(currentPage);
		
		//保存分页查询信息，显示到分页查询信息页面
		request.setAttribute("pageBean", pageBean);//保存分页查询信息
		request.getRequestDispatcher("/pageQueryInfo.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
