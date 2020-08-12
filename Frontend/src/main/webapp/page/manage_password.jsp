<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Update password</title>
    <%@ include file="/common/includes.jsp" %>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
		<form class="form-horizontal" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">Old password</label>
				<div class="col-sm-10">
					<input class="form-control" id="oldPassword" type="password" name="oldPassword"
						placeholder="Old password..." value="">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">New password</label>
				<div class="col-sm-10">
					<input class="form-control" id="newPassword" type="password" name="newPassword"
						placeholder="New password..." value="">
				</div>
			</div>

			<div>
				<button type="submit" class="btn">Update</button>
			</div>
		</form>
    <%@ include file="/common/footer.jsp" %>
</body>
</html>