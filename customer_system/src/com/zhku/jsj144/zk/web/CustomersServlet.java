package com.zhku.jsj144.zk.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.zhku.jsj144.zk.domain.Customers;
import com.zhku.jsj144.zk.service.CustomersService;
//添加客户信息
public class CustomersServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//解决乱码问题
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("text/html;charset=utf-8");
		
		//防表单刷新的判断代码
		//关键：带过来的参数值不会改变，但是带过来的session值被移除后就改变了，
		//因此，通过对比参数值和session值，就可以知道是不是重复提交表单了
		//注意：虽然一开始参数值token和session值session_token来源相同，
		//但是，参数值一旦被保存，则不会改变，不论来源的值有什么变化，都不会改变。
		//而session值则不同，一旦被移除后，则变为null，也因此可以通过对比参数和session值，从而知道表单是否刷新
		
		//获得 带过来的隐藏域 中的值 
		String token = req.getParameter("token");
		//获得之前存在 session 域 中的token 值 
		String token_session = (String) req.getSession().getAttribute("token_session");
		req.getSession().removeAttribute("token_session");//移除session
		//这样，第二次在访问同一个表单的时候，token_session的值就会为空
		
		if(token!=null)
			System.out.println("token:"+token);
		if(token_session!=null)
			System.out.println("token_session:"+token_session);
		if(token==null||!token.equals(token_session)){
			//就认为 是 重复刷新 提交 表单数据
			req.setAttribute("msg", " 你在重复提交表单");
			req.getRequestDispatcher("/failure.jsp").forward(req, resp);
			return ;
		}
		
		Customers customers=new Customers();//对象
		CustomersService customersService=new CustomersService();
		
		//beanutils可以将属性类型自动转换成javaBean里的属性类型（只限于8种基本数据类型）
		//想要将复杂类型的属性转换成其他型则需要注册一个转换器来实现
		//转换器
//		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		 
//		String date=req.getParameter("birthday");

		//1.获得传递过来的参数，进行封装【利用工具封装】
		try {
			BeanUtils.populate(customers, req.getParameterMap());//把获取到的参数封装到customers中
			System.out.println(customers);
			
			//对于爱好preference是多个值，那么使用beanutils封装时，会丢失数据，需要手动处理
			String[] values=req.getParameterValues("preference");
			if(values!=null){
				String preference=Arrays.toString(values);//将数组变为字符串[上班,工作]
				//进一步处理：将 [上班,工作]  ---> 上班,工作
				preference=preference.substring(1, preference.length()-1);
				customers.setPreference(preference);//设置爱好
			}
			
//			System.out.println(customers.getName()+":"+customers.getGender());
			
			//2.调用CustomersServervice的inser方法
			int count = customersService.insert(customers);
			
			//3.根据插入的结果情况，从而跳转不同的页面
			if(count==1){
				req.setAttribute("msg", " 恭喜你，注册成功");
				req.getRequestDispatcher("/success.jsp").forward(req, resp);
			}
			else{
//				req.getSession().setAttribute("msg", "注册失败，请重新注册");
//				resp.sendRedirect("/customer_system/addCustomer.jsp");
				req.setAttribute("msg", " 注册失败，请重新注册");
				req.getRequestDispatcher("/failure.jsp").forward(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
