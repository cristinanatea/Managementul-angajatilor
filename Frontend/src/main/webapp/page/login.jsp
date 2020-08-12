<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
    <%@ include file="/common/includes.jsp" %>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
		<form class="form-horizontal" action="<c:url value="/login" />" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input class="form-control" id="email" type="email" name="email"
						placeholder="Email..." value="bart@olo.meu">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input class="form-control" id="password" type="password" name="password"
						placeholder="Password..." value="pass">
				</div>
			</div>

			<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			<c:if test="${!empty message}">
				<div class="text-center">
					<c:out value="Error: ${message}" />
				</div>
			</c:if>

			<div>
				<button type="submit" class="btn">Login</button>
			</div>

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
    <%@ include file="/common/footer.jsp" %>
</body>
</html>