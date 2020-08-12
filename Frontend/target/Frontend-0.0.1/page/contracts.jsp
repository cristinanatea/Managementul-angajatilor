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
				    <input type="text" class="form-control" id="contractName" name="contractName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Daily norm(hours):</label>
				<div class="col-sm-10">
				    <input type="number" class="form-control" id="dailyNorm" name="dailyNorm">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Available paid leaves(days):</label>
				<div class="col-sm-10">
				    <input type="number" class="form-control" id="yearlySickDays" name="yearlySickDays">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-form-label">Available medical leaves(days):</label>
				<div class="col-sm-10">
				    <input type="number" class="form-control" id="yearlyPaidDays" name="yearlyPaidDays">
				</div>
			</div>
			<div>
				<button type="submit" class="btn" style="width:100%;">Add</button>
			</div>
		</form>

		<br>
		<input class="form-control" type="text" id="listContractsInput" onkeyup="tableSorter('listContractsInput', 'listContractsTable', 2)" placeholder="Filtreaza ..">
		<br>

		<table class="sortable" style="width:100%"  id="listContractsTable">
		  <tr>
		    <th>Id</th>
		    <th>Name</th>
		    <th>Daily norm(hours)</th>
		    <th>Available paid leaves(days)</th>
		    <th>Available medical leaves(days)</th>
			<c:if test="${canRemove}">
		   		<th>Action</th>
			</c:if>
		  </tr>
			<c:forEach items="${contractsList}" var="contract">
				<tr>
			   		<td>${contract.contractId}</td>
			   		<td>${contract.contractName}</td>
			   		<td>${contract.dailyNorm}</td>
			   		<td>${contract.yearlySickDays}</td>
			   		<td>${contract.yearlyPaidDays}</td>
					<c:if test="${canRemove}">
			   			<td>
							<form style="display: inline-block;" action="<c:url value="/contracts/remove/" />${contract.contractId}" method="post">
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