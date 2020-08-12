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
<c:if test="${credentials.rights.manageHolidays || credentials.rights.fullAdministration}">
		<form class="form-horizontal" method="post">
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Date:</label>
				<div class="col-sm-10">
				    <input type="date" class="form-control" data-date-format="YYYY-MM-DD" id="date" name="date">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Name:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" />
				</div>
			</div>
					<input type="hidden" id="action" name="action" value="add" />
			<div>
				<button type="submit" class="btn" style="width:100%;">Add</button>
			</div>
		</form>
</c:if>
		<br>
		<input class="form-control" type="text" id="listHolidaysInput" onkeyup="tableSorter('listHolidaysInput', 'listHolidaysTable', 2)" placeholder="Filtreaza ..">
		<br>
		<table class="sortable" style="width:100%"  id="listHolidaysTable">
		  <tr>
		    <th>Date</th>
		    <th>Name</th>
<c:if test="${credentials.rights.manageHolidays || credentials.rights.fullAdministration}">
			<th>Action</th>
</c:if>>
		  </tr>
				<c:forEach var="type" items="${holidaysList.holidays}">
					<tr>
				   		<td>${type.date}</td>
				   		<td>${type.name}</td>
<c:if test="${credentials.rights.manageHolidays || credentials.rights.fullAdministration}">
				   		<td>
							<form class="form-horizontal" method="post">
								<input type="hidden" name="date" value="${type.date}">
								<input type="hidden" name="name" value="${type.name}" />
								<input type="hidden" name="action" value="remove" />
								<button type="submit" class="btn">Remove</button>
							</form>
				   		</td>
</c:if>
				   	</tr>
				</c:forEach>
		</table>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>