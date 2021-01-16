<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--</body>--%>
<%--</html>--%>
<link href="<c:url value="/css/header.css" />" rel="stylesheet">
<div class="header">
    <div>
        <a href="${pageContext.request.contextPath}/browse" class="header_button">Browse</a>
        <a href="${pageContext.request.contextPath}/request" class="header_button">Make request</a>
        <a href="${pageContext.request.contextPath}/order" class="header_button">Make order</a>
    </div>
    <div class="block">
        <span>${sessionScope.get('username')}</span>
        <a href="${pageContext.request.contextPath}/logout">
            <img src="https://img.icons8.com/android/24/ffffff/logout-rounded.png" alt="LogOut"/>
        </a>
    </div>
</div>
