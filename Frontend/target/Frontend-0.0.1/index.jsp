<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Home</title>
    <%@ include file="/common/includes.jsp" %>
</head>
<body>
    <%@ include file="/common/header.jsp" %>

	<div class="container-fluid text-center">
		<h3 class="margin">Hello!</h3>
		<img src="<c:url value="/images/cristina.jpg" />" class="img-responsive img-circle margin" style="display:inline"  width="350" height="350">
		<h3>I am the software that simplifies your bussiness</h3>
	</div>

	<div class="container-fluid text-center">
		<h3 class="margin">Discover more!</h3>
		<p>This scheduling software makes it faster and easier than ever to build schedules and share them with employees and keep your workforce in the know. </p>
	</div>

    <%@ include file="/common/footer.jsp" %>
</body>
</html>