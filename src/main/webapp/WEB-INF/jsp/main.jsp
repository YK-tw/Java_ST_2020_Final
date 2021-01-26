<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 11.01.2021
  Time: 21:10
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
    <a class="header-link basket" href="#basket"></a>
</div>
<div class="header-dark-green">
    <c:url value="/catalog.html" var="catalogUrl"/>
    <a href="${catalogUrl}?page=1" class="header-link-list"><fmt:message key="catalog"/></a>
    <a href="#production" class="header-link-list"><fmt:message key="production"/></a>
    <a href="#certificate" class="header-link-list"><fmt:message key="certificates"/></a>
    <a href="#sale" class="header-link-list">SALE</a>
</div>
<div class="choose-block">
    <a href="${catalogUrl}?attribute=earrings&page=1" class="choose-section first">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle12.jpg">
        <p><fmt:message key="earrings"/></p></a>
    <a href="${catalogUrl}?attribute=ring&page=1" class="choose-section first">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle13.jpg">
        <p><fmt:message key="rings"/></p></a>
    <a href="${catalogUrl}?attribute=pendant&page=1" class="choose-section first">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle14.jpg">
        <p><fmt:message key="pendants"/></p></a>
    <a href="${catalogUrl}?attribute=chain&page=1" class="choose-section">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle15.jpg">
        <p><fmt:message key="chains"/></p></a>
    <a href="${catalogUrl}?attribute=cuff&page=1" class="choose-section">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle16.jpg">
        <p><fmt:message key="cuffs"/></p></a>
    <a href="${catalogUrl}?attribute=bracelet&page=1" class="choose-section">
        <img src="${pageContext.request.contextPath}/img/main/Rectangle17.jpg">
        <p><fmt:message key="bracelets"/></p></a>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
