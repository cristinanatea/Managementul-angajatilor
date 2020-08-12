<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Projects</title>
	<%@ include file="/common/includes.jsp" %>
</head>
  <body>
	<%@ include file="/common/header.jsp" %>
		<input class="form-control" type="text" id="listProjectsInput" onkeyup="tableSorter('listProjectsInput', 'listProjectsTable', 2)" placeholder="Filtreaza ..">
		<br>
		<table class="sortable" style="width:100%"  id="listProjectsTable">
			<tr>
				<th>Denumire</th><th>Descriere</th><th>Utilizatori</th>
			</tr>

<c:forEach var="type" items="${projectsList.projects}">
			<tr>
		   		<td><a href="<c:url value="/projects/" />${type.key}">${type.value.projectName}</a></td>
		   		<td>${type.value.description}</td>
		   		<td>
		   			<c:forEach var="userId" items="${projectsList.usersAssignedToProject[type.key]}">
			   			<a href="<c:url value="/users/" />${userId}">${projectsList.users[userId].firstName} ${projectsList.users[userId].lastName}</a><br>
		   			</c:forEach>
		   		</td>
		   	</tr>
</c:forEach>

		</table>
	 <%@ include file="/common/footer.jsp" %>
  </body>
</html>