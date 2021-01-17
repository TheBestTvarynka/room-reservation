<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Not found</title>
    <link href="<c:url value="/css/404.css" />" rel="stylesheet">
    <link rel="icon" type="image/ico" href="<c:url value="/img/main_icon.ico" />"/>
</head>
<body>
    <div class="container">
        <div class="window">
            <span class="t404">404</span>
            <span class="description">oops, the page you are looking for can't be found</span>
            <a href="${pageContext.request.contextPath}/">Back to homepage</a>
        </div>
    </div>
</body>
</html>
