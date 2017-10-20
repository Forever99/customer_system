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
<script>
	//删除确认对话框
	function confirm_deleteOne(id) {
		// 	alert(id);
		var message = confirm("你确定要删除这条记录吗？");
		if (message == true) {
			window.location.href = "/customer_system/DeleteOneServlet?id=" + id;//正确
		}
	}

	//全选或者全不选
	function checkAll() {
		//id="chooseFirst"  如果选中，下面的checkbox全部被选中
		var chooseAll = document.getElementsByName("choose");//全部choose
		var choose = document.getElementById("chooseFirst");//第一个choose
		//选中状态：checked可以为true或者false
		if (choose.checked) {
			for (var i = 0; i < chooseAll.length; i++) {
				chooseAll[i].checked = true;
			}

		}
		//id="chooseFirst"  如果没有选中，下面的checkbox全部不选中
		else {
			for (var i = 0; i < chooseAll.length; i++) {
				chooseAll[i].checked = false;
			}
		}
	}
</script>
</head>

<body>
	<h1 align="center">当前是第${pageBean.currentPage}页--客户信息查询列表</h1>
	<table border="1" align="center">
		<tr>
			<!--     <td>编号</td> -->
			<td>选中状态<input type="checkbox" name="choose" id="chooseFirst"
				onclick="checkAll()"></td>
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


		<c:forEach var="customer" items="${pageBean.dataList }">
			<tr>
				<!--     <td>${customer.id }</td> -->
				<td><input type="checkbox" name="choose"
					value="${customer.id }"></td>
				<td>${customer.name }</td>
				<td>${customer.gender }</td>
				<td>${customer.birthday }</td>
				<td>${customer.cellphone }</td>
				<td>${customer.email }</td>
				<td>${customer.preference }</td>
				<td>${customer.type }</td>
				<td>${customer.description }</td>
				<!--     <td><a href="/customer_system/DeleteOneServlet?id=${customer.id }">删除</a></td> -->
				<td><a href="javascript:void(0)"
					onclick="confirm_deleteOne('${customer.id}')">删除</a> <a
					href="/customer_system/GetUpdateInfoServlet?id=${customer.id }">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<div align="center">
		<!-- 	说明：是要不是第一页，就显示首页，上一页；否则不显示 -->
		<c:if test="${pageBean.currentPage!=1 }">
			<a href="/customer_system/PageQueryServlet?currentPage=1">首页</a>
			<a
				href="/customer_system/PageQueryServlet?currentPage=${pageBean.currentPage!=1?pageBean.currentPage-1:1 }">上一页</a>
		</c:if>
		<!-- 	参考百度的分页：左5 右4 -->
		
<!-- 		情况一：总共页数小于或者等于10页 -->

<!-- 		情况二：总共页数大于10页  -->
<!-- 			此情况，考虑三种可能：（1）左边：[端点 ]begin=1,end=10（2）右边 :[端点]begin=${pageBean.totalPage-9} end=${pageBean.totalPage} 
							       （3）中间 [普通情况]begin=${pageBean.currentPage-5>0?pageBean.currentPage-5:1 }
											end=${pageBean.currentPage+4<pageBean.totalPage?pageBean.currentPage+4:pageBean.totalPage}
 -->

		<%-- <c:forEach var="i" begin="${pageBean.currentPage-5>1?pageBean.currentPage-5:1 }" end="${pageBean.currentPage+4<pageBean.totalPage?pageBean.currentPage+4: pageBean.totalPage}"> --%>

		<c:choose>
			
			<c:when test="${pageBean.totalPage<=10 }">
				<c:forEach var="i" begin="1" end="${pageBean.totalPage}">
					<c:if test="${pageBean.currentPage==i }">
						<font color="red">${i }</font>
					</c:if>
					<c:if test="${pageBean.currentPage!=i }">
						<a href="/customer_system/PageQueryServlet?currentPage=${i }">${i}</a>
					</c:if>
				</c:forEach>
			</c:when>
			
			<c:otherwise>
				<c:choose>
					
					<c:when test="${pageBean.currentPage<=10 }">
						<c:forEach var="i"
							begin="1"
							end="10">
							<c:if test="${pageBean.currentPage==i }">
								<font color="red">${i }</font>
							</c:if>
							<c:if test="${pageBean.currentPage!=i }">
								<a href="/customer_system/PageQueryServlet?currentPage=${i }">${i}</a>
							</c:if>
						</c:forEach>
					</c:when>
					
					<c:when test="${pageBean.currentPage+9>=pageBean.totalPage }">
						<c:forEach var="i"
							begin="${pageBean.totalPage-9}"
							end="${pageBean.totalPage}">
							<c:if test="${pageBean.currentPage==i }">
								<font color="red">${i }</font>
							</c:if>
							<c:if test="${pageBean.currentPage!=i }">
								<a href="/customer_system/PageQueryServlet?currentPage=${i }">${i}</a>
							</c:if>
						</c:forEach>
					</c:when>
				
					<c:otherwise>
						<c:forEach var="i"
							begin="${pageBean.currentPage-5>0?pageBean.currentPage-5:1 }"
							end="${pageBean.currentPage+4<pageBean.totalPage?pageBean.currentPage+4:pageBean.totalPage}">
							<c:if test="${pageBean.currentPage==i }">
								<font color="red">${i }</font>
							</c:if>
							<c:if test="${pageBean.currentPage!=i }">
								<a href="/customer_system/PageQueryServlet?currentPage=${i }">${i}</a>
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				
			</c:otherwise>

		</c:choose>



		<!-- 	说明：是要不是最后一页，就显示尾页，下一页；否则不显示 -->
		<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
			<a
				href="/customer_system/PageQueryServlet?currentPage=${pageBean.currentPage!=pageBean.totalPage?pageBean.currentPage+1:pageBean.totalPage }">下一页</a>
			<a
				href="/customer_system/PageQueryServlet?currentPage=${pageBean.totalPage }">尾页</a>
		</c:if>

	</div>
</body>
</html>
