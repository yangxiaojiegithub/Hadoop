<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品信息</title>
</head>
<body> 
<!-- 显示错误信息 -->
<c:if test="${errMessages != null}">
	<c:forEach items="${errMessages }" var="error">
		${error }
	</c:forEach>
</c:if>
<form id="itemForm" action="${pageContext.request.contextPath }/items/editItemsSubmit.action" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="${itemsCustomer.id }"/>
修改商品信息：
<table width="100%" border=1>
<tr>
	<td>商品名称</td>
	<td><input type="text" name="name" value="${itemsCustomer.name }"/></td>
</tr>
<tr>
	<td>商品价格</td>
	<td><input type="text" name="price" value="${itemsCustomer.price }"/></td>
</tr>
<tr>
	<td>商品生产日期</td>
	<td><input type="text" name="createtime" value="<fmt:formatDate value="${itemsCustomer.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
</tr>
<tr>
	<td>商品图片</td>
	<td>
		<c:if test="${itemsCustomer.pic !=null}">
			<img src="/picture/${itemsCustomer.pic}" width=400 height=400/>
			<br/>
		</c:if>
		<input type="hidden" name="pic" value="${itemsCustomer.pic }">
		<input type="file"  name="itemsPic"/> 
	</td>
</tr>
<tr>
	<td>商品简介</td>
	<td>
	<textarea rows="3" cols="30" name="detail">${itemsCustomer.detail }</textarea>
	</td>
</tr>
<tr>
	<td>商品类型</td>
	<td>商品类型: 
		<select name="itemsType">
			<c:forEach items="${itemsType }" var="itemsType">
				<option value="${itemsType.key}">${itemsType.value}</option>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" value="提交"/>
</td>
</tr>
</table>

</form>
</body>

</html>