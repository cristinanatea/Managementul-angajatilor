<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
    <%@ include file="/common/includes.jsp" %>
</head>
  <body>
    <%@ include file="/common/header.jsp" %>

		<br><br>
		Salut ${credentials.user.firstName} ${credentials.user.lastName}
		<br><br>
		Email ${credentials.user.email}

     <%@ include file="/common/footer.jsp" %>
  </body>
</html>