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
					<span class="glyphicon glyphicon-chevron-left"></span> Go Back</a>
				<br>
			</div>

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
			<h3 align="center">List of CV to YOU (${nameSession})</h3>
			<hr>

			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th width="3%">###</th>
						<%--<th width="3%">id_cont</th>--%>
						<th width="15%">Full NAME</th>
						<th width="15%">Objective</th>
						<th width="20%">Education</th>
						<th width="20%">Training</th>
						<th width="20%">Experience</th>
						<th width="20%">Skills</th>
						<th width="20%">Language</th>
						<%--<th width="20%">user_id</th>--%>
					</tr>
				</thead>
				<tbody>
				<%--<% int countRow=1; %>--%>
			<%--	<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
					<c:out value="${dataUser.id}"/>
					<br>
				</c:forEach>--%>
				<c:set var="countRow" value="1" scope="page" />
				<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
					<c:forEach items="${cvList}" var="dataFile" varStatus="loopCounter">
					<%--<c:forEach items="${contList}" var="dataFileCont" varStatus="loopCounter">--%>
					<%--<c:set target="${contList}" var="contListT"></c:set>--%>
						<c:if test="${dataFile.usersByFK.id == dataUser.id}">

						<tr>
							<td><c:out value="${countRow}" /></td>
							<%--<td><c:out value="${dataFile.contact.id}" /></td>--%>
							<td><c:out value="${dataFile.contact.fullName}" /></td>
							<td><c:out value="${dataFile.objective}" /></td>
							<td><c:out value="${dataFile.edu}" /></td>
							<td><c:out value="${dataFile.trainings}" /></td>
							<td><c:out value="${dataFile.exp}" /></td>
							<td><c:out value="${dataFile.skills}" /></td>
							<td><c:out value="${dataFile.lang}" /></td>
							<%--<td><c:out value="${dataFile.usersByFK.id}" /></td>--%>
						<c:set var="countRow" value="${countRow + 1}" scope="page" />

							<%--<td>
								<button id="contact-button" class="btn btn-danger" value="${dataFile.id}" name="coName"> &lt;%&ndash;onclick="getCont('123')"&ndash;%&gt;
									<span class="glyphicon glyphicon-asterisk"></span> INFO
							</button></td>--%>
						<td>

							<%--<button id="contact-button" class="btn btn-danger" value="${loopCounter.count}" name="coName"> &lt;%&ndash;onclick="getCont('123')"&ndash;%&gt;
									<span class="glyphicon glyphicon-asterisk"></span> INFO
							</button></td>--%>
							<%--<button onclick="openAddMenu('${dataFile.id}')" value="${dataFile.id}"> addINFO </button>--%>
							<button type="button" class="btn btn-success " data-toggle="modal"
									data-target="#myModal" data-id ="${dataFile.contact.id}">
								<span class="glyphicon glyphicon-info-sign"></span>Contact info</button> <%--data-id ="555"--%>

							<div id="myModal" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">${times}</button>
											<h3 class="modal-title">Contact does not recognized!</h3>
											</div>
										<form>
										<div>
											<h4 class="modal-body-region"></h4>
										</div>
										<div>
											<h4 class="modal-body-phone"></h4>
										</div>
											<div>
											<h4 class="modal-body-email"></h4>
										</div>
										</form>

										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
												</div>
										</div>


									</div>
								</div>


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
			<a class="btn btn-primary pull-right" href="fileUploader">
				<span class="glyphicon glyphicon-chevron-left"></span> Back to MAIN page</a> <br>
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
			src='<c:url value="/web-resources/libs/bootstrap-dialog/js/bootstrap-dialog.min.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/js/modal.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/js/loginForm.js"/>'></script>
</body>

</html>