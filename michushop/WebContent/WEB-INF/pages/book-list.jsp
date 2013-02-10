<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE HTML>

<html>
<head>
<title>Book listing</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/print">

<a href="${pageContext.request.contextPath}/book">Add Book</a>
<input type="submit" value="Print" />

<table>
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>Price</th>
		<th>Publication Date</th>
	</tr>
	
	<c:forEach var="book" items="${books}">  
		<tr>
			<td>
				<input type="checkbox" name="wybor" value="${book.id}"/>
				<a href="${pageContext.request.contextPath}/book?id=${book.id}">${book.title}</a>
			</td>
			<td>${book.description}</td>
			<td>${book.price}</td>
			<td>${book.pubDate}</td>
		</tr>
	</c:forEach>
</table>
</form>
</body>
</html>