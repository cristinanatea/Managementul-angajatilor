		<!-- Navbar -->
		<nav class="navbar navbar-default">
			<div class="container">
				<div class="logo">
					<a class="navbar-brand" href="#"><img src="<c:url value="/images/butterfly.jpg" />" width="60" height="60" style="display: inline"></a>
				</div>
				<div class="menu collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav navbar-right">
		
					<c:if test="${not empty credentials}">						
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn"><a href="<c:url value="/logout" />">Logout</a></button>
						</div>

<c:choose>
	<c:when test="${credentials.rights.viewAllProjects || credentials.rights.fullAdministration}">
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn">Projects</button>
						  <div class="dropdown-content">
						    <a href="<c:url value="/projects/mine" />">My projects</a>
						    <a href="<c:url value="/projects" />">All projects</a>
						  </div>
						</div>
	</c:when>
	<c:otherwise>
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn"><a href="<c:url value="/projects/mine" />">My projects</a></button>
						</div>
	</c:otherwise>
</c:choose>
						
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn">Users</button>
						  <div class="dropdown-content">
						    <a href="<c:url value="/users/me" />">My account</a>
						    <a href="<c:url value="/users" />">All users</a>
						  </div>
						</div>
						
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn">Timesheet</button>
						  <div class="dropdown-content">
						    <a href="<c:url value="/timesheet" />">My timesheet</a>
						    <a href="<c:url value="/holidays" />">Holidays</a>
						    <a href="<c:url value="/requests" />">Leave requests</a>
						  </div>
						</div>
						
<c:if test="${credentials.rights.manageProject || credentials.rights.manageUsers || credentials.rights.manageHolidays || credentials.rights.manageLeaveRequests || credentials.rights.fullAdministration}">
						<div class="dropdown" style="float:right;">
						  <button class="dropbtn">Management</button>
						  <div class="dropdown-content">
<c:if test="${credentials.rights.manageProject || credentials.rights.fullAdministration}">
						    <a href="<c:url value="/projects/add" />">Add project</a>
</c:if>
<c:if test="${credentials.rights.manageUsers || credentials.rights.fullAdministration}">
						    <a href="<c:url value="/users/add" />">Add user</a>
</c:if>
<c:if test="${credentials.rights.manageHolidays || credentials.rights.fullAdministration}">
						    <a href="<c:url value="/holidays" />">Manage Holidays</a>
</c:if>
<c:if test="${credentials.rights.manageLeaveRequests || credentials.rights.fullAdministration}">
						    <a href="<c:url value="/requests/all" />">Manage requests</a>
</c:if>
<c:if test="${credentials.rights.fullAdministration}">
						    <a href="<c:url value="/roles" />">Manage roles</a>
						    <a href="<c:url value="/contracts" />">Manage contracts</a>
</c:if>
						  </div>
						</div>
</c:if>

					</c:if>
					<c:if test="${empty credentials}">
						<li><a href="<c:url value="/" />">Home</a></li>
						<li><a href="<c:url value="/login" />">Login</a></li>
					</c:if>
					</ul>
				</div>
			</div>
		</nav>

		
	<div class="bg-1 text-center" style="min-height: 100vh;text-align:center; width:100%">
		<br>
		<div style="width:90%; display:block; margin: 0 auto;">