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
		<form class="form-horizontal" method="post">
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Name:</label>
				<div class="col-sm-10">
				    <input type="text" class="form-control" id="name" name="name" disabled="disabled" value="${title}">
				</div>
			</div>
			<input type="hidden" class="form-control" id="id" name="id" value="${id}">
			
			<div>
				<button type="submit" class="btn" style="width:100%;">Update</button>
			</div>
			<br>
			<hr>
			<br>
			
			<c:forEach var="type" items="${checkedEntries}">
				<div class="form-group">
					<label class="col-sm-6 col-form-label">${type.value}</label>
					<div class="col-sm-2">
				    	<input type="checkbox" name="checkedEntries" value="${type.key}" checked>
					</div>
				</div>
			</c:forEach>  
			<c:forEach var="type" items="${uncheckedEntries}">
				<div class="form-group">
					<label class="col-sm-6 col-form-label">${type.value}</label>
					<div class="col-sm-2">
				    	<input type="checkbox" name="checkedEntries" value="${type.key}">
					</div>
				</div>
			</c:forEach>  
		</form>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>