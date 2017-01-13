<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DBA page</title>
	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/libs/bootstrap-3.1.1/css/bootstrap.min.css"/>'>
	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/libs/bootstrap-dialog/css/bootstrap-dialog.min.css"/>'>
	<link rel="stylesheet" type="text/css"
		  href='<c:url value="/web-resources/css/style.css"/>'>
</head>
<body>
	<div class="container">
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
				<h3>Spring MVC + Dropzone.js Example</h3>
			</div>
			<div class="panel-body">
				<a class="btn btn-primary" href="/admin">
					<span class="glyphicon glyphicon-chevron-left"></span>Back to ADMIN</a> <br>
			</div>

			<c:url value="/logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					   value="${_csrf.token}" />
			</form>

			<div class="container">
				<div class="panel panel-default">
					<!-- Default panel contents -->

					<div class="panel-heading">History visits for  <strong>${userName} </strong></div>
					<div class="panel-body">
						<div class="row">
						<div class="col-md-6">This information contains History Visits for only one user!</div>
							<div class="col-md-offset-10">
							<div class="dropdown">
								<button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="true">
									Select User
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<c:forEach items="${userListAuth}" var="dataUserAuth" varStatus="loopCounter">
										<li><a href="/db/${dataUserAuth.username}"> ${dataUserAuth.username}</a></li>
									</c:forEach>
									<li role="separator" class="divider"></li>
									<li><a href="/db/anonymousUser">Anonymous</a></li>
								</ul>
							</div>
							</div>
						</div>

					</div>

					<!-- Table -->
					<table class="table">
						<thead>
						<tr>
							<th width="5%">###</th>
							<th width="7%">User ID</th>
							<th width="30%">DATE visitors</th>
							<th width="30%">TIME visitors</th>
							<th width="30%">check</th>
						</tr>
						</thead>
						<tbody>
						<c:set var="day0" value="2000-12-14"/>
						<c:set var="colorCH" value="0"/>


						<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
							<fmt:formatDate var="day1" pattern="yyyy-MM-dd" value="${dataUser.date}"/>

							<c:set var="colorTR" value="info"/>
							<c:if test="${day0 lt day1}">
								<c:set var="colorTR" value="warning"/>
								<c:set var="colorCH" value="1"/>
							</c:if>
							<%--<c:if test="${day0 eq day1}">
								<c:if test="${colorTR eq 'info'}">
									<c:set var="colorTR" value="warning"/>
									<c:set var="colorCH" value="1"/>
								</c:if>
							</c:if>--%>



							<c:set var="day0" value="${day1}"/>

						<tr class="${colorTR}">
							<td><c:out value="${loopCounter.count}" /></td>
							<td><c:out value="${dataUser.id}" /></td>
							<%--<td><c:out value="${dataUser.date}" /></td>--%>
							<td>
							<fmt:formatDate pattern="yyyy-MM-dd" value="${dataUser.date}"/>
							</td>
							<td>
							<fmt:formatDate pattern="HH:mm:ss" value="${dataUser.date}"/>
							</td>
							<td>
							<fmt:formatDate var="day1" pattern="yyyy-MM-dd" value="${dataUser.date}"/>


								<c:if test="${day0 lt day1}">
									<c:out value="TRUE"/>
									<c:set var="day0" value="${day1}"/>
									<%--<fmt:formatDate var="day0" pattern="yyyy-MM-dd" value="${day0}"/>--%>
								</c:if>

							</td>
						</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>


			<hr>

			<div class="panel panel-default page-information">
				<div class="panel-heading text-center">
					<h4>List of CV for ONLY one user (${userName})</h4>
				</div>
				<div class="panel-body">
					<table class="table table-hover table-condensed">
						<thead>
						<tr>
							<th width="3%">###</th>
							<th width="3%">id_cont</th>
							<th width="15%">Full NAME</th>
							<th width="20%">Education</th>
							<th width="20%">Training</th>
							<th width="20%">Experience</th>
							<th width="20%">Skills</th>
							<th width="20%">Language</th>
							<th width="20%">user_id</th>
						</tr>
						</thead>
						<tbody>
						<c:set var="countRow" value="1" scope="page" />
						<c:forEach items="${userList}" var="dataUser" varStatus="loopCounter">
							<c:forEach items="${cvList}" var="dataFile" varStatus="loopCounter">
								<%--<c:forEach items="${contList}" var="dataFileCont" varStatus="loopCounter">--%>
								<%--<c:set target="${contList}" var="contListT"></c:set>--%>
								<c:if test="${dataFile.usersByFK.id == dataUser.id}">

									<tr>
										<td><c:out value="${loopCounter.count}" /></td>
										<td><c:out value="${dataFile.contact.id}" /></td>
										<td><c:out value="${dataFile.contact.fullName}" /></td>
										<td><c:out value="${dataFile.edu}" /></td>
										<td><c:out value="${dataFile.trainings}" /></td>
										<td><c:out value="${dataFile.exp}" /></td>
										<td><c:out value="${dataFile.skills}" /></td>
										<td><c:out value="${dataFile.lang}" /></td>
										<td><c:out value="${dataFile.usersByFK.id}" /></td>
										<c:set var="countRow" value="${countRow + 1}" scope="page" />

										<td>
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
			src='<c:url value="/web-resources/libs/bootstrap-dialog/js/bootstrap-dialog.min.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/js/modal.js"/>'></script>
	<script type="text/javascript"
			src='<c:url value="/web-resources/js/loginForm.js"/>'></script>
</body>
</html>