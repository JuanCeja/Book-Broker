<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Add a Book to Your Shelf!</h1>
	<form:form action="/create/book" modelAttribute="newBook">
		<p>
			<form:label path="title">Title</form:label>
			<form:errors path="title" />
			<form:input path="title" />
		</p>
		<p>
			<form:label path="author">Author</form:label>
			<form:errors path="author" />
			<form:input path="author" />
		</p>
		<p>
			<form:label path="description">My thoughts</form:label>
			<form:errors path="description" />
			<form:input path="description" />
		</p>
		<input type="submit" value="submit" />
	</form:form>
</body>
</html>