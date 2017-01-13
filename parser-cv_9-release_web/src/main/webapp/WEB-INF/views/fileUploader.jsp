<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport"
	content="width=device-width, initial-scale=1">
<title>CV ANALYZER</title>

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

			<div class="jpg-dz" <%--style="text-align: center"--%> >

				How it works?
				<hr>
				<img src="/web-resources/pictures/parser flow.jpg" style="width: 1120px">
			</div>

			<div class="panel-body">
				<div>
					<form id="dropzone-form" class="dropzone"
						enctype="multipart/form-data">

						<div
							class="dz-default dz-message file-dropzone text-center well col-sm-12 jpg-dz">
							At first UPLOAD CV (drag & drop zone)
							<span><center><img class="media-object" src="/web-resources/pictures/upload-files.png" alt="Generic placeholder image" style="width:140px"></center></span>
						</div>
						<div class="dropzone-previews">
						</div>
					</form>
					<hr>

					<hr>
					<div class="row">
						<div class="col-xs-2">
							<button id="upload-button123" class="btn btn-primary btn-lg">
								<h4 align="middle">SECOND: uploading files to cloud</h4>
								<span class="glyphicon glyphicon-cloud-upload"></span> Upload
							</button>
						</div>
						<div class="col-xs-10">
							<button id="parse-button" class="btn btn-primary btn-lg pull-right">
								<h4 align="middle">THIRD: parsing & analyzing files</h4>
								<span class="glyphicon glyphicon-play"></span> Parse
							</button>
						</div>
					</div>

				<hr>
				<div>
					<a class="btn btn-success pull-left btn-lg" href="analyze">
						<span class="glyphicon glyphicon-picture"></span> View Analyzed
					</a>
				</div>
				<div>
					<a class="btn btn-success pull-right btn-lg" href="list">
						<span class="glyphicon glyphicon-eye-open"></span> View All Uploads
					</a>
				</div>
				<hr>
				<a class="btn btn-block btn-lg" href="admin">
					<span class="glyphicon glyphicon-bookmark"></span> ADMIN page
				</a>

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</div>
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
		src='<c:url value="/web-resources/libs/dropzone.js"/>'></script>
	<script type="text/javascript"
		src='<c:url value="/web-resources/js/app.js"/>'></script>
	<script type="text/javascript"
		src='<c:url value="/web-resources/js/parse.js"/>'></script>
</body>
</html>