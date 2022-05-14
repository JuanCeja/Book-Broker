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
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Welcome, ${thisUser.userName }</h1>
	<h4>Books from everyone's shelves:</h4>
	<div>
		<a href="/logout">Logout</a> <a href="/books/new">Create Book</a>
	</div>
	<table class="table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>Title</td>
				<td>Author Name</td>
				<td>Posted By</td>
				<td>Actions</td>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books }" var="book">
				<tr>
					<td>${book.id }</td>
					<td><a href="/books/${book.id }">${book.title }</a></td>
					<td>${book.author }</td>
					<td>${book.user.userName }</td>
					<td><c:choose>
							<c:when test="${book.user.id == user_id }">
								<a href="/books/edit/${book.id}">Edit</a>
								<a href="/books/delete/${book.id}">Delete</a>
							</c:when>
							<c:otherwise>
								<a href="/borrow/${book.id }">Borrow</a>
							</c:otherwise>

						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<hr />
	<h3>Books I'm Borrowing...</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>Title</td>
				<td>Author Name</td>
				<td>Owner</td>
				<td>Actions</td>
			</tr>
		</thead>
		<tbody>

			<c:if test="${user.id == book.borrower.id }">
				<tr>
					<td> <c:out value="${book.id }"/> </td>
					<td> <c:out value="${book.title }"/> </td>
					<td> <c:out value="${book.author }"/> </td>
					<td> <c:out value="${book.user.userName }"/> </td>
					<td> <a href="/return/book">return</a> </td>
				</tr>
			</c:if>
		</tbody>
	</table>

</body>
</html>