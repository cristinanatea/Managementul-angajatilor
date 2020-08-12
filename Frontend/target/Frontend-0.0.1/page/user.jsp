<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
    <%@ include file="/common/includes.jsp" %>
</head>
  <body>
<c:choose>
	<c:when test="${editMode}">
		<c:set var="editableFieldsState" value=""/>
		<c:set var="editableFieldsState2" value="disabled='disabled"/>
	</c:when>
	<c:when test="${addMode}">
		<c:set var="editableFieldsState" value=""/>
		<c:set var="editableFieldsState2" value=""/>
	</c:when>
	<c:otherwise>
		<c:set var="editableFieldsState" value="disabled='disabled'"/>
		<c:set var="editableFieldsState2" value="disabled='disabled'"/>
	</c:otherwise>
</c:choose>
    <%@ include file="/common/header.jsp" %>
<c:set var="userId" value="${userInfo.userId}"/>
<c:set var="firstName" value="${userInfo.firstName}"/>
<c:set var="lastName" value="${userInfo.lastName}"/>
<c:set var="email" value="${userInfo.email}"/>
<c:set var="availablePaidDays" value="${userInfo.availablePaidDays}"/>
<c:set var="availableSickDays" value="${userInfo.availableSickDays}"/>
<c:if test="${credentials.rights.fullAdministration}">
				<form class="form-horizontal" action="<c:url value="/users/reset/password/" />${userId}" method="post">
					<button type="submit" class="btn">Reset password</button>
			    </form>
</c:if>
				<form class="form-horizontal" method="post">
					<input type="hidden" class="form-control" id="userId" name="userId"  value="${userId}">
					<div class="form-group">
						<label class="col-sm-2 col-form-label">First name:</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" id="firstname" name="firstname"  <c:out value = "${editableFieldsState}"/> value="${firstName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 col-form-label">Last name:</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" id="lastname" name="lastname"  <c:out value = "${editableFieldsState}"/> value="${lastName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 col-form-label">Email:</label>
						<div class="col-sm-10">
						    <input type="email" class="form-control" id="email" name="email"  <c:out value = "${editableFieldsState2}"/> value="${email}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 col-form-label">Available paid days:</label>
						<div class="col-sm-10">
						    <input type="number" class="form-control" id="availablePaidDays" name="availablePaidDays" <c:out value = "${editableFieldsState}"/> value="${availablePaidDays}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 col-form-label">Available Sick days:</label>
						<div class="col-sm-10">
						    <input type="number" class="form-control" id="availableSickDays" name="availableSickDays"  <c:out value = "${editableFieldsState}"/> value="${availableSickDays}">
						</div>
					</div>

	<c:if test="${editMode || addMode}">
			<div class="form-group" class="col-sm-2">
				<label class="col-sm-2 col-form-label">Role:</label>
				<div class="col-sm-10">
				<select name="roleId" id="roleId" <c:out value = "${editableFieldsState}"/> class="form-control">
					<c:forEach items="${rolesList}" var="role">
						<option value="${role.roleId}">${role.roleName}</option>
					</c:forEach>
				</select>
				</div>
			</div>
			<div class="form-group" class="col-sm-2">
				<label class="col-sm-2 col-form-label">Contract:</label>
				<div class="col-sm-10">
				<select name="contractId" id="contractId" <c:out value = "${editableFieldsState}"/> class="form-control">
					<c:forEach items="${contractsList}" var="contract">
						<option value="${contract.contractId}">${contract.contractName}</option>
					</c:forEach>
				</select>
				</div>
			</div>
	</c:if>
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
						    <strong><a href="<c:url value="/timesheet/user/" />${userId}">Timesheet</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;

<c:if test="${userId == credentials.crededentials.userId}">
						    <strong><a href="<c:url value="/users/update/password" />">Update password</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;
</c:if>
<c:if test="${credentials.rights.manageProject || credentials.rights.fullAdministration}">
						    <strong><a href="<c:url value="/manage/user/" />${userId}">Edit managed projects</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;
</c:if>
<c:if test="${credentials.rights.fullAdministration}">
						    <strong><a href="<c:url value="/users/edit/" />${userId}">Edit user</a></strong>
</c:if>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<label class="col-sm-2 col-form-label">Projects:</label>
						<div class="col-sm-10">
							<ul style="list-style-type: none;">
								<c:forEach var="project" items="${projectsList}">
									<li><strong><a href="<c:url value="/projects/" />${project.projectId}">${project.projectName}</a></strong></li>
								</c:forEach>
							</ul>
						</div>
					</div>
	</c:otherwise>
</c:choose>
				</form>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>