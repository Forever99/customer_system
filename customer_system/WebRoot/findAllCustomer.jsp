<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>My JSP 'findAllCustomer.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script>
	//删除确认对话框
	function confirm_deleteOne(id) {
// 	alert(id);
    var message=confirm("你确定要删除这条记录吗？");
    if(message==true){
    	
//     	window.location("/customer_system/DeleteOneServlet?id="+id);//错误【使用错误，是等号=，不是括号（）】
// 		window.location="/customer_system/DeleteOneServlet?id="+id;//正确

//     	document.location.herf="/customer_system/DeleteOneServlet?id="+id;//错误[拼写错误]
		window.location.href="/customer_system/DeleteOneServlet?id="+id;//正确
// 		document.location.href="/customer_system/DeleteOneServlet?id="+id;//正确
//     	document.location="/customer_system/DeleteOneServlet?id="+id;//正确
    }
	}
	
	//全选或者全不选
	function checkAll(){
		//id="chooseFirst"  如果选中，下面的checkbox全部被选中
		var chooseAll=document.getElementsByName("choose");//全部choose
		var choose=document.getElementById("chooseFirst");//第一个choose
		//选中状态：checked可以为true或者false
		if(choose.checked){
			for(var i=0;i<chooseAll.length;i++){
			 	chooseAll[i].checked=true;
			}
		
		}		
		//id="chooseFirst"  如果没有选中，下面的checkbox全部不选中
		else{
			for(var i=0;i<chooseAll.length;i++){
				 	chooseAll[i].checked=false;
				}
		}
	}
</script>
</head>

<body>
	<h1 align="center">客户信息查询列表</h1>
	<form action="/customer_system/SelectConditionServlet" method="post"> 
	<table align="center">
	<tr>
	<td>
	<select name="conditionName">
	<option value="name"
	<c:if test="${param.conditionName=='name' }">
	selected="selected"
	</c:if>
	>按姓名查询</option>
	<option value="cellphone"
	<c:if test="${param.conditionName=='cellphone' }">
	selected="selected"
	</c:if>
	>按手机号码查询</option>
	<option value="description"
	<c:if test="${param.conditionName=='description' }">
	selected="selected"
	</c:if>
	>按描述查询</option>
	</select>
	</td>
	<td><input type="text" name="keyWords" value="${param.keyWords }"></td>
	<td><input type="submit" value="查询"></td>
	</tr>
	</table>
	</form>

	<form action="/customer_system/DeleteAllServlet" method="post"> 
	
	<table border="1" align="center">
		<tr>
			<!--     <td>编号</td> -->
			<td>选中状态<input type="checkbox" name="choose" id="chooseFirst" onclick="checkAll()"></td>
			<td>客户姓名</td>
			<td>性别</td>
			<td>生日</td>
			<td>手机</td>
			<td>电子邮件</td>
			<td>客户爱好</td>
			<td>客户类型</td>
			<td>备注</td>
			<td>状态</td>
		</tr>


		<c:forEach var="customer" items="${CustomersList }">
			<tr>
				<!--     <td>${customer.id }</td> -->
				<td><input type="checkbox" name="choose" value="${customer.id }"></td>
				<td>${customer.name }</td>
				<td>${customer.gender }</td>
				<td>${customer.birthday }</td>
				<td>${customer.cellphone }</td>
				<td>${customer.email }</td>
				<td>${customer.preference }</td>
				<td>${customer.type }</td>
				<td>${customer.description }</td>
				<!--     <td><a href="/customer_system/DeleteOneServlet?id=${customer.id }">删除</a></td> -->
				<td><a href="javascript:void(0)" onclick="confirm_deleteOne('${customer.id}')">删除</a>
					<a href="/customer_system/GetUpdateInfoServlet?id=${customer.id }">修改</a>
					</td>
			</tr>
		</c:forEach>


	</table>
	<div align="center"><input type="submit" value="多选删除提交按钮" ></div>
	</form> 
</body>
</html>
