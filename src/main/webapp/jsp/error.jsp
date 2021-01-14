<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link href="<c:url value="/css/error.css" />" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="error_text_container">
            <div class="error_text">
                <p class="error_title">Error occurred</p>
                <p class="error_description">${requestScope.get('error')}</p>
            </div>
        </div>
        <div class="photo_container">
            <img src="<c:url value="/img/error.jpg" />" alt="">
        </div>
    </div>
</body>
</html>
