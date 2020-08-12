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

		<c:if test="${enableSubmit}">
			<form class="form-horizontal" method="post">
				<div class="form-group">
					<label class="col-sm-2 col-form-label">Date start:</label>
					<div class="col-sm-10">
					    <input type="date" class="form-control" data-date-format="YYYY-MM-DD" id="dateStart" name="dateStart">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-form-label">Date end:</label>
					<div class="col-sm-10">
					    <input type="date" class="form-control" data-date-format="YYYY-MM-DD" id="dateEnd" name="dateEnd">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-form-label">Comment:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="comment" name="comment" />
					</div>
				</div>
				<input type="hidden" id="action" name="action" value="add" />

				<div>
					<button type="submit" class="btn" style="width:100%;">Submit</button>
				</div>
			</form>
		</c:if>



		<br>
		<input class="form-control" type="text" id="listRequestsInput" onkeyup="tableSorter('listRequestsInput', 'listRequestsTable', 3)" placeholder="Filtreaza ..">
		<br>
		<table class="sortable" style="width:100%"  id="listRequestsTable">
		  <tr>
		    <th>User</th><th>Start Date</th><th>End Date</th><th>Status</th>
		  </tr>
				<c:forEach var="request" items="${requests.leaveRequests}">
					<tr>
				   		<td>${requests.users[request.value.userId].firstName} ${requests.users[request.value.userId].lastName}</td>
				   		<td>${request.value.dateStart}</td>
				   		<td>${request.value.dateEnd}</td>
				   		<td>
							<c:choose>
								<c:when test="${request.value.status == 2}">
									<strong style="color:green;"><c:out default="None" value="APROVED"/></strong>
								</c:when>
								<c:when test="${request.value.status == 1}">
									<strong style="color:red;"><c:out default="None" value="REJECTED"/></strong>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${canApprove}">
											<form style="display: inline-block;" action="<c:url value="/requests/approve/" />${request.value.requestId}" method="post">
												<button type="submit" class="btn">APROVE</button>
											</form>
											<form style="display: inline-block;" action="<c:url value="/requests/reject/" />${request.value.requestId}" method="post">
												<button type="submit" class="btn">REJECT</button>
											</form>
										</c:when>
										<c:otherwise>
											<strong style="color:orange;"><c:out default="None" value="PENDING"/></strong>
										</c:otherwise>
									</c:choose>
									<c:if test="${request.value.userId == credentials.crededentials.userId}">
										<form style="display: inline-block;" action="<c:url value="/requests/cancel/" />${request.value.requestId}" method="post">
											<button type="submit" class="btn">CANCEL</button>
										</form>
									</c:if>
								</c:otherwise>
							</c:choose>
				   		</td>
				   	</tr>
				</c:forEach>
		</table>
     <%@ include file="/common/footer.jsp" %>
  </body>
</html>