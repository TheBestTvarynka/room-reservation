<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/forms.css" />" rel="stylesheet">
</head>
<style>
    body {
        width: calc(100% - 2em);
        height: calc(100vh - 2em);
        display: inline-flex;
        justify-content: center;
        align-items: center;
        background: #f6f6f6;
    }
</style>
<body>
    <form method="post" action="${pageContext.request.contextPath}/login" class="login_form">
        <span class="error_message">${requestScope.get('error')}</span>
        <span class="info_message">${requestScope.get('message')}</span>
        <span class="login_form_title">Login</span>
        <label>Username</label>
        <input type="text" name="username" id="username" placeholder="Enter username" class="input" required>
        <label>Password</label>
        <input type="password" name="password" id="password" placeholder="Enter password" class="input" required>
        <button type="submit" class="button">Next</button>
        <a href="${pageContext.request.contextPath}/register" class="redirect">Register</a>
    </form>
</body>
</html>
