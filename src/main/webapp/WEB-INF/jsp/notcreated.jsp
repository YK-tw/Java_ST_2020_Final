<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 07.02.2021
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename='locale.locale'/>

<html>
<head>
    <meta charset="utf-8"/>
    <title>POYNAC</title>
    <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>
</head>
<body>

<p class="header-text"><fmt:message key="header"/></p>
<div class="header-green">
    <div class="dropdown">
        <c:choose>
            <c:when test="${!(cookie['lang'].value.equals('en'))}">
                <button onclick="dropdownFunction('dropdownLang')" class="dropbtn lang">RU</button>
            </c:when>
            <c:otherwise>
                <button onclick="dropdownFunction('dropdownLang')" class="dropbtn lang">${cookie['lang'].value}</button>
            </c:otherwise>
        </c:choose>

        <div id="dropdownLang" class="dropdown-content lang-dropdown">
            <a href="?cookieLocale=ru">RU</a>
            <a href="?cookieLocale=en">EN</a>
        </div>
    </div>
    <div class="dropdown">
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
    <c:url value="/notcreated.html" var="notCreatedUrl"/>
    <a class="header-link basket" href="${notCreatedUrl}"></a>
</div>
<div class="header-dark-green">
    <c:url value="/catalog.html" var="catalogUrl"/>
    <a href="${catalogUrl}?page=1" class="header-link-list"><fmt:message key="catalog"/></a>
    <a href="${notCreatedUrl}" class="header-link-list"><fmt:message key="production"/></a>
    <a href="${notCreatedUrl}" class="header-link-list"><fmt:message key="certificates"/></a>
    <a href="${notCreatedUrl}" class="header-link-list">SALE</a>
</div>

<p style="color: white; font-family: Raleway; margin: 100px 30%; width: 40%; text-align:center; font-size: 30px;">
    Страница в разработке</p>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
