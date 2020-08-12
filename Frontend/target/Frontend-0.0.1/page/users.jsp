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
		<input class="form-control" type="text" id="listUsersInput" onkeyup="tableSorter('listUsersInput', 'listUsersTable', 4)" placeholder="Filtreaza ..">
		<br>
		<table class="sortable" style="width:100%"  id="listUsersTable">
		  <tr>
		    <th>Nume</th><th>Prenume</th><th>Email</th><th>Working on</th>
		  </tr>
				<c:forEach var="type" items="${usersList.users}">
					<tr>
				   		<td>${type.value.firstName}</td>
				   		<td>${type.value.lastName}</td>
				   		<td><a href="<c:url value="/users/" />${type.key}">${type.value.email}</a></td>
				   		<td><c:forEach var="projectId" items="${usersList.projectsAssignedToUser[type.key]}">
				       		<a href="<c:url value="/projects/" />${projectId}">${usersList.projects[projectId].projectName}</a><br>
				   			</c:forEach></td>
				   	</tr>
				</c:forEach>
		</table>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>