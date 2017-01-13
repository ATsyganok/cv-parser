<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<title>Access is denied</title>

<link href="${contextPath}/web-resources/libs/bootstrap-3.1.1/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/web-resources/css/common.css" rel="stylesheet">
<body>
	<h1>HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username : <strong> ${username} <br/>You do not have permission to access this page!</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>