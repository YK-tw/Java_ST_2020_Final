<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 16.01.2021
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename='locale.locale'/>
<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
<head>
    <meta charset="utf-8"/>
    <title>POYNAC</title>
    <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalog.css"/>

</head>
<body>
<div class="header-green">
    <div class="dropdown">
        <button onclick="dropdownFunction('dropdownLang')" class="dropbtn lang">${cookie['lang'].value}</button>
        <div id="dropdownLang" class="dropdown-content lang-dropdown">
            <c:url value="/catalog.html" var="catalogUrl"/>
            <a href="?cookieLocale=ru&${pageContext.request.queryString}">RU</a>
            <a href="?cookieLocale=en&${pageContext.request.queryString}">EN</a>
        </div>
    </div>
    <div class=" dropdown">
        <button onclick="dropdownFunction('dropdownMoney')" class="dropbtn money">BYN</button>
        <div id="dropdownMoney" class="dropdown-content money-dropdown">
            <a href="#BYN">BYN</a>
            <a href="#USD">USD</a>
        </div>
    </div>
    <img src="${pageContext.request.contextPath}/img/main/svg/name_white.svg" class="header-name-img"
         alt="Poynac logo"/>
    <input id="dropdownSearch" type="text"/>
    <button class="search" onclick="searchShowFunction('dropdownSearch')"></button>
    <c:url value="/login.html" var="loginUrl"/>
    <a class="header-link profile" href="${loginUrl}"></a>
    <a class="header-link basket" href="#basket"></a>
</div>
<div class="container">
    <div class="row">
        <div class="sidebar">
            <form>
                <div class="filter"><fmt:message key="filter"/></div>
                <!-- Widget -->
                <div class="widget-sidebar">
                    <div class="widget-sidebar-title">
                        <fmt:message key="material"/>
                    </div>
                    <div class="widget-sidebar-body">
                        <label class="radio">
                            <input type="radio" class="radio__real" name="material">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="cupronickel"/></span>
                        </label>
                        <label class="radio">
                            <input type="radio" class="radio__real" name="material">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="nickelsilver"/></span>
                        </label>
                        <label class="radio">
                            <input type="radio" class="radio__real" name="material">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="brass"/></span>
                        </label>
                    </div>
                </div>
                <!-- //Widget -->
                <!-- Widget -->
                <div class="widget-sidebar">
                    <div class="widget-sidebar-title">
                        <fmt:message key="inlay"/>
                    </div>
                    <div class="widget-sidebar-body">
                        <label class="radio">
                            <input type="radio" class="radio__real" name="inlay">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="stone"/></span>
                        </label>
                        <label class="radio">
                            <input type="radio" class="radio__real" name="inlay">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="wood"/></span>
                        </label>
                        <label class="radio">
                            <input type="radio" class="radio__real" name="inlay">
                            <span class="radio__fake"></span>
                            <span class="radio__title"><fmt:message key="pearl"/></span>
                        </label>
                    </div>
                </div>
                <!-- //Widget -->
                <button class="filter-button"><fmt:message key="submit"/></button>
                <button class="filter-button" type="reset"><fmt:message key="reset"/></button>
                <c:choose>
                    <c:when test="${(sessionScope.authorizedUser.role.ordinal() != 0) && (sessionScope.authorizedUser != null)}">
                        <c:url value="/product/update.html" var="productUpdUrl"/>
                        <a href="${productUpdUrl}" class="filter-button"><fmt:message key="create"/></a>
                    </c:when>
                </c:choose>
            </form>
        </div>
        <div class="main">
            <!-- Widget -->
            <div class="widget">
                <div class="widget-title"><fmt:message key="show"/><input type="number" value="24"></div>
            </div>
            <!-- //Widget -->
            <c:choose>
                <c:when test="${empty products}">
                    <p class="not-found"
                       style="color: white;font-family: Raleway;font-size: 20px;text-align: center;width: 100%;">
                        <fmt:message key="notfound"/></p>
                </c:when>
                <c:otherwise>
                    <div class="cards-holder">
                        <c:url value="/product/read.html" var="productUrl"/>
                        <c:forEach items="${products}" var="product">
                            <a class="card-link" href="${productUrl}?id=${product.id}">
                                <div class="card">
                                    <div class="card__img">
                                        <img src="${pageContext.request.contextPath}/img/main/Rectangle%2039.jpg"
                                             alt="product"/>
                                    </div>
                                    <div class="card__content">
                                        <div class="card__title">${product.name}</div>
                                        <div class="card__price">${product.price} BYN</div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="pagination">
                <c:forEach var="i" begin="1" end="3">
                    <c:choose>
                        <c:when test="${param.page == i}">
                            <a class="active" href="?page=${i}&attribute=${param['attribute']}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="?page=${i}&attribute=${param['attribute']}">${i}</a>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>