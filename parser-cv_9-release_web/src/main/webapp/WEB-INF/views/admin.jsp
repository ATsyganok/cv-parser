<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport"
		  content="width=device-width, initial-scale=1">
	<title>ADMIN PAGE</title>

	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/libs/bootstrap-3.1.1/css/bootstrap.min.css"/>'>
	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/libs/bootstrap-dialog/css/bootstrap-dialog.min.css"/>'>
	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/css/style.css"/>'>

</head>
<body>
	<div class="container" >
		<h1>Title : ${title}</h1>
		<h1>Message : ${message}</h1>

		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome :  <strong>${pageContext.request.userPrincipal.name}</strong> |
				<button id="login-form" class="btn btn-link btn-lg"><span class="glyphicon glyphicon-log-out"></span>Logout</button>
			</h2>
		</c:if>
	</div>

	<div class="information">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h3>View and Analyze CV in Web Browser</h3>
				<h4>Based on: Spring MVC + JPA/Hibernate + Spring Security(Log&Reg) + Dropzone.js</h4>
			</div>
			<div class="panel-body">
				<a class="btn btn-primary" href="fileUploader">
					<span class="glyphicon glyphicon-chevron-left"></span> Go Back</a> <br>
				<br>
			</div>



			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span2 col-lg-offset-6">
					</div>
				</div>
			</div>

			<div class="list-group progress-striped container">
				<a href="#" class="list-group-item active">
					SELECT user for more details!
				</a>
				<c:forEach items="${userListAuth}" var="dataUserAuth" varStatus="loopCounter">
					<a href="/db/${dataUserAuth.username}" class="list-group-item"> ${dataUserAuth.username}</a>
				</c:forEach>
				<a href="/db/anonymousUser" class="list-group-item">Anonymous</a>
			</div>
			<hr>

				<c:url value="/logout" var="logoutUrl" />
				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"
						   value="${_csrf.token}" />
				</form>

				<div class="panel panel-default container">
					<div class="panel-heading text-center">
					<h4>Count visited users:</h4>
					</div>
						<div class="panel-body">
							<c:set var="userName" value="name" scope="session"/>
							<c:set var="countAnonymous" value="0" scope="session"/>
							<c:set var="count" value="0" scope="session"/>
							<c:forEach items="${userListAuth}" var="dataUserAuth" varStatus="loopCounter">
								<c:set var="userName" value="${dataUserAuth.username}" scope="session"/>
								<c:set var="countAnonymous" value="0" scope="session"/>
								<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
									<c:if test="${dataUser.nameAuth == userName}">
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:if>
									<c:set var="countAnonymous" value="${countAnonymous + 1}" scope="session"/>
								</c:forEach>
								<ul class="list-group">
									<li class="list-group-item"> ${userName} <span class="badge">${count}</span></li>
								</ul>
							</c:forEach>
							<ul class="list-group">
								<li class="list-group-item"> Anonymous <span class="badge">${countAnonymous}</span></li>
							</ul>
						</div>
				</div>


			<div class="panel panel-default container">
				<div class="panel-heading text-center">
					<h4>File types (uploaded):</h4>
				</div>
				<div class="panel-body">
					<c:set var="pdfCount" value="0" scope="session"/>
					<c:set var="docCount" value="0" scope="session"/>
					<c:forEach items="${fileList}" var="dataFile" varStatus="loopCounter">
						<c:if test="${dataFile.type == 'application/pdf'}">
							<c:set var="pdfCount" value="${pdfCount + 1}" scope="page"/>
						</c:if>
						<c:if test="${dataFile.type == 'application/msword'}">
							<c:set var="docCount" value="${docCount + 1}" scope="page"/>
						</c:if>
					</c:forEach>
					<ul class="list-group">
						<li class="list-group-item"> PDF files <span class="badge">${pdfCount}</span></li>
						<li class="list-group-item"> DOC files <span class="badge">${docCount}</span></li>
					</ul>
				</div>
			</div>
			<br>

			<div class="panel panel-default page-information">
				<div class="panel-heading text-center">
					<h4>List of files from ALL USERS (sorted by date)</h4>
				</div>
				<div class="panel-body">
					<table class="table table-hover table-condensed">
						<thead>
						<tr>
							<th width="3%">S.N.</th>
							<th width="20%">File Name</th>
							<th width="15%">File Type</th>
							<th width="20%">Upload Date</th>
							<th width="15%">Session ID</th> <%--admin user HARDCORE--%>
							<th width="15%">User</th> <%--admin user HARDCORE--%>
							<th width="15%">File Size</th>
							<th width="10%">Actions</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${fileList}" var="dataFile" varStatus="loopCounter">
									<tr>
										<td><c:out value="${loopCounter.count}" /></td>
										<td><c:out value="${dataFile.name}" /></td>
										<td><c:out value="${dataFile.type}" /></td>
										<td><c:out value="${dataFile.date}" /></td>
										<td><c:out value="${dataFile.sessionID}" /></td>
										<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
											<c:if test="${dataFile.sessionID == dataUser.sessionID}">
											<td><c:out value="${dataUser.nameAuth}" /></td>
											</c:if>
										</c:forEach>
										<td>
											<c:choose>
												<c:when test="${(dataFile.size < 1024)}">
													${dataFile.size/(1024 * 1.0)} Bytes
												</c:when>
												<c:when
														test="${(dataFile.size >= 1024) && (dataFile.size < 1024*1024)}">
													<fmt:formatNumber value="${dataFile.size/(1024 * 1.0)}"
																	  maxFractionDigits="2" /> KB
												</c:when>
												<c:when test="${(dataFile.size >= 1024*1024)}">
													<fmt:formatNumber value="${dataFile.size/(1024 * 1024 * 1.0)}"
																	  maxFractionDigits="2" /> MB
												</c:when>
											</c:choose>
										</td>

										<td>
											<a class="btn btn-primary" href="${pageContext.request.contextPath}/get/${dataFile.id}">
												<span class="glyphicon glyphicon-cloud-download"></span> Download
											</a></td>
									</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</div>
			<a class="btn btn-primary pull-right" href="#">
				<span class="glyphicon glyphicon-chevron-up"></span>UP</a> <br>
		</div>
	</div>
	<div class="powered">
		&copy; powered by V.Mankivsky & A.Tsyganok
	</div>

	<script type="text/javascript"
			src='<c:url value="/web-resources/libs/jquery/jquery-2.1.1.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/libs/bootstrap-3.1.1/js/bootstrap.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/js/loginForm.js"/>'></script>

</body>
</html>