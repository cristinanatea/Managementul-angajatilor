<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Project details</title>
    <%@ include file="/common/includes.jsp" %>
</head>
  <body>
	<c:choose>
		<c:when test="${editMode || addMode}">
			<c:set var="editableFieldsState" value=""/>
		</c:when>
		<c:otherwise>
			<c:set var="editableFieldsState" value="disabled='disabled'"/>
		</c:otherwise>
	</c:choose>
    <%@ include file="/common/header.jsp" %>
		<c:forEach var="type" items="${projectsList.projects}">
			<form class="form-horizontal" method="post">
				<input type="hidden" class="form-control" id="projectId" name="projectId" value="${type.key}">
				<div class="form-group">
					<label class="col-sm-2 col-form-label">Project name:</label>
					<div class="col-sm-10">
					    <input type="text" class="form-control" id="name" name="name" <c:out value = "${editableFieldsState}"/> value="${type.value.projectName}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-form-label">Description:</label>
					<div class="col-sm-10">
					    <input type="text" class="form-control" id="description" name="description" <c:out value = "${editableFieldsState}"/> value="${type.value.description}">
					</div>
				</div>
				<c:choose>
					<c:when test="${editMode}">
						<input type="submit" class="form-control" value="Update">
					</c:when>
					<c:when test="${addMode}">
						<input type="submit" class="form-control" value="Add">
					</c:when>
					<c:otherwise>
						<br><br>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 col-form-label">Quick links:</label>
								<div class="col-sm-10">
								    <strong><a href="<c:url value="/timesheet/project/" />${type.key}">Timesheet</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${credentials.rights.manageProject || credentials.rights.fullAdministration}">
								    <strong><a href="<c:url value="/manage/project/" />${type.key}">Edit assigned users</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;
								    <strong><a href="<c:url value="/projects/edit/" />${type.key}">Edit project</a></strong>
</c:if>
								</div>
							</div>
							<hr>
						<div class="form-group">
							<label class="col-sm-2 col-form-label">Users:</label>
							<div class="col-sm-10">
								<ul style="list-style-type: none;">
									<c:forEach var="userId" items="${projectsList.usersAssignedToProject[type.key]}">
										<li><strong><a href="<c:url value="/users/" />${userId}">${projectsList.users[userId].firstName} ${projectsList.users[userId].lastName} [${projectsList.users[userId].email}]</a></strong></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</form>
		</c:forEach>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>