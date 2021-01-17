<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link href="<c:url value="/css/forms.css" />" rel="stylesheet">
    <link rel="icon" type="image/ico" href="<c:url value="/img/main_icon.ico" />"/>
</head>
<style>
    a {
        text-decoration: none;
        color: #1b191d;
    }
    body {
        width: 100%;
        margin: 0;
        display: flex;
        flex-direction: column;
        background: #f6f6f6;
    }
    .page {
        width: 100%;
        display: inline-flex;
        justify-content: center;
        align-content: center;
    }
    form {
        background: white;
        display: flex;
        font-size: 20px;
        width: 45%;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        padding: 1em;
        box-shadow: 0 10px 12px -4px grey;
        border-radius: 1em;
        margin-top: 3em;
    }
    form label {
        color: #36333a;
        font-size: 15px;
        margin-top: 0.5em;
    }
    .title {
        width: 100%;
        text-align: center;
        font-size: 20px;
        color: #1b191d;
    }
</style>
<body>
    <c:import url="header.jsp" />
    <div class="page">
        <form method="post" action="${pageContext.request.contextPath}/order" name="order">
            <span class="error_message">${requestScope.get('error')}</span>
            <span class="info_message">${requestScope.get('message')}</span>
            <span class="title">Create new order</span>

            <label>Phone</label>
            <input type="text" name="phone" id="phone" placeholder="e. g. 0987654321" class="input" oninput="checkAll(order)" required>

            <label>Enter room number</label>
            <input type="text" name="roomNumber" id="roomNumber" placeholder="e. g. 1-01" class="input" required value="${param.room}">

            <label>Enter from date</label>
            <input type="date" name="dateFrom" id="dateFrom" class="input" required>

            <label>Enter to date</label>
            <input type="date" name="dateTo" id="dateTo" class="input" required>

            <button type="submit" id="submit" class="button">Submit</button>
        </form>
    </div>
    <script>
        const checkPhone = form => {
            const re_phone = /^\d{10}$/;
            const input = document.getElementById('phone');
            if (re_phone.test(form.phone.value)) {
                input.setAttribute('class', 'input');
                return true;
            } else {
                input.setAttribute('class', 'input_error');
                return false;
            }
        };
        const checkAll = form => {
            const submitButton = document.getElementById('submit');
            if(checkPhone(form)) {
                console.log('true');
                submitButton.removeAttribute('disabled');
            } else {
                console.log('false');
                submitButton.setAttribute('disabled', 'true');
            }
        };
    </script>
</body>
</html>
