<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport"
	content="width=device-width, initial-scale=1">
<title>Spring MVC + Dropzone.js Example</title>

<link rel="stylesheet" type="text/css"
	href='<c:url value="/web-resources/libs/bootstrap-3.1.1/css/bootstrap.min.css"/>'>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/web-resources/libs/bootstrap-dialog/css/bootstrap-dialog.min.css"/>'>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/web-resources/css/style.css"/>'>

</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h3>View and Analyze CV in Web Browser</h3>
				<h4>Based on: Spring MVC + JPA/Hibernate + Spring Security(Log&Reg) + Dropzone.js</h4>
			</div>
			<div class="panel-body">
				<a class="btn btn-primary" href="fileUploader">
					<span class="glyphicon glyphicon-chevron-left"></span> Go Back</a> <br>
				<br>
				<%--<a class="btn btn-primary" href="${pageContext.request.contextPath}">
					<span class="glyphicon glyphicon-chevron-left"></span> Go Back</a> <br>
				<br>--%>
				<div>
				<h3 align="right">
					<c:set var="nameSession" value="${pageContext.request.userPrincipal.name}"/>
					<c:set var="action" value="Logout"/>
					<c:set var="glyph" value="glyphicon glyphicon-log-out"/>
					<c:if test="${nameSession == null}">
						<c:set var="nameSession" value="Anonymous"/>
						<c:set var="action" value="LOGIN"/>
						<c:set var="glyph" value="glyphicon glyphicon-log-in"/>
					</c:if>
					You are login as :  <strong> ${nameSession}</strong> |
					<button id="login-form" class="btn btn-link btn-lg">
						<span class="${glyph}"></span>  <c:out value="${action}"/>
					</button>
				</h3>
				</div>

				<c:url value="/logout" var="logoutUrl" />

				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"
						   value="${_csrf.token}" />
				</form>
				<h3 align="center">List of files to YOU (${nameSession}) </h3>
			</div>

			<%--<div>
				<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
					<c:out value="${dataUser.sessionID}"/>
				</c:forEach>
			</div>--%>

			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th width="5%">###</th>
						<th width="40%">File Name</th>
						<th width="20%">File Type</th>
						<th width="20%">Upload Date</th>
						<%--<c:if test="${nameSession == 'test1'}">	<th width="10%">Session ID</th> </c:if>--%>
						<th width="15%">File Size</th>
						<th width="10%">Actions</th>
					</tr>
				</thead>
				<tbody>

				<%--<c:set target="${userList}" var="dataUser"/>
				<c:out value="${dataUser.nameAuth}"/>--%>
				<c:set var="countRow" value="1" scope="page" />
				<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
					<c:forEach items="${fileList}" var="dataFile" varStatus="loopCounter">
						<%--<c:set value="${userList}" var="dataUser"/>--%>
						<c:if test="${dataFile.sessionID == dataUser.sessionID}">

						<tr>
							<td><c:out value="${countRow}" /></td>
							<td><c:out value="${dataFile.name}" /></td>
							<td><c:out value="${dataFile.type}" /></td>
							<td><c:out value="${dataFile.date}" /></td>
							<c:set var="countRow" value="${countRow + 1}" scope="page" />
						<%--<c:if test="${nameSession == 'test1'}">	<td><c:out value="${dataFile.sessionID}" /></td> </c:if>--%>
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
						</c:if>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
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