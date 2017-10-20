<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'addCustomer.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- 	路径问题：此种是错误的 -->
<!-- 	<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script> -->

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
<link href="css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
	$(function(){
		$("#birthday").datepicker({
			minDate: new Date(1980, 0, 01),
			maxDate: new Date(2020, 11, 31),
			yearRange :'-40:+30',
			changeMonth: true,
			changeYear: true,
			dateFormat: 'yy-mm-dd'
		});
	});
</script>

</head>

<h3>客户信息添加的页面</h3>

<body>
	<!-- <font color="red">${msg }</font> -->
	<%
		//生成一个唯一的串
		String token = UUID.randomUUID().toString();
		session.setAttribute("token_session", token);//设置session
		
	%>
	<form action="${pageContext.request.contextPath }/CustomersServlet"
		method="post">
		<!-- 	<form action="/customer_system/CustomersServlet" method="post"> -->
		<table>
			<tr>
				<td>名字</td>
				<td><input type="text" name="name">
				
<!-- 				设置token  的隐藏数值 -->

				<input type="hidden" name="token" value="${token_session }">
				</td>
			</tr>
			<tr>
				<td>性别</td>
				<td><input type="radio" name="gender" value="男">男 <input
					type="radio" name="gender" value="女">女</td>
			</tr>

			<tr>
				<td>生日</td>
				<td><input type="text" name="birthday" id="birthday"></td>
			</tr>

			<tr>
				<td>手机</td>
				<td><input type="text" name="cellphone"></td>
			</tr>

			<tr>
				<td>电子邮件</td>
				<td><input type="text" name="email"></td>
			</tr>

			<tr>
				<td>客户爱好</td>
				<td><input type="checkbox" name="preference" value="上班">上班
					<input type="checkbox" name="preference" value="工作">工作 <input
					type="checkbox" name="preference" value="学习">学习 <input
					type="checkbox" name="preference" value="睡觉">睡觉</td>
			</tr>

			<tr>
				<td>客户类型</td>
				<td><select name="type">
						<option value="vip">vip</option>
						<option value="黄金vip">黄金vip</option>
						<option value="普通用户">普通用户</option>
						<option value="超级vip">超级vip</option>
				</select></td>
			</tr>

			<tr>
				<td>备注</td>
				<td><textarea rows="5" cols="30" name="description"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交">
					&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"></td>
			</tr>


		</table>
	</form>
</body>
</html>
