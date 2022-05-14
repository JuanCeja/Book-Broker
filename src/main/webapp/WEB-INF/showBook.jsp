<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1> <c:out value="${book.title }"></c:out> </h1>
<h4> <c:out value="${book.user.userName }"/> read <c:out value="${book.title }"></c:out> by <c:out value="${book.author }"></c:out>.</h4>
<p>Here are <c:out value="${book.user.userName }"/>'s thoughts:</p>
<hr>
<div>
	<c:out value="${book.description }"></c:out>
</div>
<a href="/home">Home</a>
</body>
</html>