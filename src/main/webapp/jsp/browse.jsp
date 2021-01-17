<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Browse</title>
    <link href="<c:url value="/css/forms.css" />" rel="stylesheet">
    <link href="<c:url value="/css/pagination.css" />" rel="stylesheet">
    <link href="<c:url value="/css/browse_page_styles.css" />" rel="stylesheet">
    <link rel="icon" type="image/ico" href="<c:url value="/img/main_icon.ico" />"/>
</head>
<style>
    a {
        text-decoration: none;
        color: #1b191d;
    }
    body {
        color: #1b191d;
        width: 100%;
        display: flex;
        flex-direction: column;
        padding: 0;
        margin: 0;
    }
</style>
<body>
    <c:import url="header.jsp" />
    <div class="page">
        <div class="content">
            <form class="search">
                <span class="error_message">${requestScope.get('error')}</span>
                <span class="info_message">${requestScope.get('message')}</span>
                <div class="search_block">
                    <div>
                        <label for="priceOrder">Price order</label>
                        <select name="price" id="priceOrder">
                            <c:choose>
                                <c:when test="${param.priceOrder=='asc'}">
                                    <option value="asc" selected>Asc</option>
                                    <option value="desc">Desc</option>
                                    <option value="none">None</option>
                                </c:when>
                                <c:when test="${param.priceOrder=='desc'}">
                                    <option value="asc">Asc</option>
                                    <option value="desc" selected>Desc</option>
                                    <option value="none">None</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="none" selected>None</option>
                                    <option value="asc">Asc</option>
                                    <option value="desc">Desc</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div>
                        <label for="seats">Seat order</label>
                        <select name="seats" id="seats">
                            <c:choose>
                                <c:when test="${param.seats=='asc'}">
                                    <option value="asc" selected>Asc</option>
                                    <option value="desc">Desc</option>
                                    <option value="none">None</option>
                                </c:when>
                                <c:when test="${param.seats=='desc'}">
                                    <option value="asc">Asc</option>
                                    <option value="desc" selected>Desc</option>
                                    <option value="none">None</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="none" selected>None</option>
                                    <option value="asc">Asc</option>
                                    <option value="desc">Desc</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div class="options">
                        <c:set var="types" value="${fn:join(paramValues.get('types'), '-')}"/>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(types, 'ROOM')}">
                                    <input type="checkbox" value="ROOM" checked name="types">
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="ROOM" name="types">
                                </c:otherwise>
                            </c:choose>
                            <span>Room</span>
                        </label>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(types, 'VIP')}">
                                    <input type="checkbox" value="VIP" name="types" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="VIP" name="types">
                                </c:otherwise>
                            </c:choose>
                            <span>Vip</span>
                        </label>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(types, 'LUX')}">
                                    <input type="checkbox" value="LUX" name="types" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="LUX" name="types">
                                </c:otherwise>
                            </c:choose>
                            <span>Lux</span>
                        </label>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(types, 'PRESIDENT')}">
                                    <input type="checkbox" value="PRESIDENT" name="types" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="PRESIDENT" name="types">
                                </c:otherwise>
                            </c:choose>
                            <span>President</span>
                        </label>
                    </div>
                    <div class="options">
                        <c:set var="statuses" value="${fn:join(paramValues.get('statuses'), '-')}"/>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(statuses, 'AVAILABLE')}">
                                    <input type="checkbox" value="AVAILABLE" checked name="statuses">
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="AVAILABLE" name="statuses">
                                </c:otherwise>
                            </c:choose>
                            <span>Available</span>
                        </label>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(statuses, 'REPAIR')}">
                                    <input type="checkbox" value="REPAIR" name="statuses" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="REPAIR" name="statuses">
                                </c:otherwise>
                            </c:choose>
                            <span>Repair</span>
                        </label>
                        <label>
                            <c:choose>
                                <c:when test="${fn:contains(statuses, 'BOOKED')}">
                                    <input type="checkbox" value="BOOKED" name="statuses" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" value="BOOKED" name="statuses">
                                </c:otherwise>
                            </c:choose>
                            <span>Booked</span>
                        </label>
                    </div>
                </div>
                <button type="submit" class="button">Search</button>
            </form>
            <div class="result">
                <c:choose>
                    <c:when test="${not empty requestScope.get('page')}">
                        <c:set var="rooms" value="${requestScope.get('page').getData()}"/>
                        <c:if test="${rooms.size() < 1}">
                            <span>No rooms found :(</span>
                        </c:if>
                        <c:forEach items="${rooms}" var="room">
                            <a class="room" href="${pageContext.request.contextPath}/order?room=${room.getNumber()}">
                                <span>Number: ${room.getNumber()}</span>
                                <span>Type: ${room.getType()}</span>
                                <span>Seat number: ${room.getSeatNumber()}</span>
                                <span>Status: ${room.getStatus()}</span>
                                <span>Price: ${room.getPrice()}</span>
                            </a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <span>No rooms found :(</span>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="pagination">
                <jsp:useBean id="queryFormatter" class="com.kpi.lab4.utils.QueryParametersFormatter" scope="request">
                    <jsp:setProperty name="queryFormatter" property="parameters" value="${paramValues}" />
                </jsp:useBean>
                <c:if test="${requestScope.page.page > 1}">
                    <a class="pag_item" href="${pageContext.request.contextPath}/browse${queryFormatter.getFormattedQuery("page", requestScope.page.page - 1)}">&lt;</a>
                </c:if>
                <c:forEach var="p" varStatus="status" begin="1" end="${requestScope.pages}" step="1">
                    <c:choose>
                        <c:when test="${status.count == requestScope.page.page}">
                            <a class="pag_item selected" href="${pageContext.request.contextPath}/browse${queryFormatter.getFormattedQuery("page", status.count.toString())}">${status.count}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="pag_item" href="${pageContext.request.contextPath}/browse${queryFormatter.getFormattedQuery("page", status.count.toString())}">${status.count}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${requestScope.page.page < requestScope.pages}">
                    <a class="pag_item" href="${pageContext.request.contextPath}/browse${queryFormatter.getFormattedQuery("page", requestScope.page.page + 1)}">&gt;</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
