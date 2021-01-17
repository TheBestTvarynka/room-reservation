<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/css/header.css" />" rel="stylesheet">
<div class="header">
    <div class="block">
        <img src="<c:url value="/img/main_icon.png"/>" alt="" class="main_icon">
        <a href="${pageContext.request.contextPath}/home" class="header_button">Home</a>
        <a href="${pageContext.request.contextPath}/browse" class="header_button">Browse</a>
        <a href="${pageContext.request.contextPath}/request" class="header_button">Make request</a>
        <a href="${pageContext.request.contextPath}/order" class="header_button">Make order</a>
    </div>
    <div class="block">
        <span>${sessionScope.get('username')}</span>
        <a href="${pageContext.request.contextPath}/logout" class="log_out">
            <img src="https://img.icons8.com/android/24/ffffff/logout-rounded.png" alt="LogOut" class="log_out_img"/>
        </a>
    </div>
</div>
