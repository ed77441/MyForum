<%@ tag language="java" description="te" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="contentDependencies" fragment="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>
		<jsp:invoke fragment="title" />
	</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="shortcut icon" href="/pics/favicon.ico"/>
	
	<link rel="stylesheet" href="/css/default.css" />
	<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" />
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="/css/footer.css" />
	
	<link rel="stylesheet" href="/css/disable-valid-style.css">
	<script src="/js/validation.js"></script>
	
	<jsp:invoke fragment="contentDependencies"/>
	
	
	<c:if test="${not empty sessionScope.user}">
		<script src="/js/notification.js"></script>
	</c:if>
</head>
<body>
	<mylib:header user="${sessionScope.user}" />
	<jsp:doBody />
	<mylib:footer />
</body>
</html>