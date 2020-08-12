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
				    <input type="text" class="form-control" id="roleName" name="roleName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">View all projects:</label>
				<div class="col-sm-10">
				    <input type="checkbox" class="form-control" id="viewAllProjects" name="viewAllProjects" value="yes">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Manage projects:</label>
				<div class="col-sm-10">
				    <input type="checkbox" class="form-control" id="manageProject" name="manageProject" value="yes">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Manage Users:</label>
				<div class="col-sm-10">
				    <input type="checkbox" class="form-control" id="manageUsers" name="manageUsers" value="yes">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Manage holidays:</label>
				<div class="col-sm-10">
				    <input type="checkbox" class="form-control" id="manageHolidays" name="manageHolidays" value="yes">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Manage leave requests:</label>
				<div class="col-sm-10">
				    <input type="checkbox" class="form-control" id="manageLeaveRequests" name="manageLeaveRequests" value="yes">
				</div>
			</div>

			<div>
				<button type="submit" class="btn" style="width:100%;">Add</button>
			</div>
		</form>




		<br>
		<input class="form-control" type="text" id="listRolesInput" onkeyup="tableSorter('listRolesInput', 'listRolesTable', 2)" placeholder="Filtreaza ..">
		<br>

		<table class="sortable" style="width:100%"  id="listRolesTable">
		  <tr>
		    <th>Name</th>
		    <th>View all projects</th>
		    <th>Manage project</th>
		    <th>Manage users</th>
		    <th>Manage holidays</th>
		    <th>Manage leave requests</th>
		    <th>Admin</th>
			<c:if test="${canRemove}">
		   		<th>Action</th>
			</c:if>

		  </tr>
		  <!--  ${type.key} -->
				<c:forEach items="${rolesList}" var="role">
					<tr>
				   		<td>${role.roleName}</td>
				   		<td>
							<c:choose>
								<c:when test="${role.viewAllProjects}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
				   		<td>
							<c:choose>
								<c:when test="${role.manageProject}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
				   		<td>
							<c:choose>
								<c:when test="${role.manageUsers}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
				   		<td>
							<c:choose>
								<c:when test="${role.manageHolidays}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
				   		<td>
							<c:choose>
								<c:when test="${role.manageLeaveRequests}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
				   		<td>
							<c:choose>
								<c:when test="${role.fullAdministration}">
									<c:out default="None" value="YES"/>
								</c:when>
								<c:otherwise>
									<c:out default="None" value="NO"/>
								</c:otherwise>
							</c:choose>
				   		</td>
						<c:if test="${canRemove}">
					   		<td>
								<form style="display: inline-block;" action="<c:url value="/roles/remove/" />${role.roleId}" method="post">
									<button type="submit" class="btn">REMOVE</button>
								</form>
							</td>
						</c:if>
				   	</tr>
				</c:forEach>
		</table>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>