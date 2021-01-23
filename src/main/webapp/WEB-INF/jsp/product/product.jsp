<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 19.01.2021
  Time: 21:31
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css"/>

</head>
<body>
<div class="header-green">
    <div class="dropdown">
        <button onclick="dropdownFunction('dropdownLang')" class="dropbtn lang">${cookie['lang'].value}</button>
        <div id="dropdownLang" class="dropdown-content lang-dropdown">
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
        <div class="images">
            <img class="main-img" src="${pageContext.request.contextPath}/img/main/Rectangle%2039.jpg"/>
        </div>
        <div class="info">
            <p class="product-name">${product.name}</p>
            <div class="existence">
                <p class="existence-white"><fmt:message key="existence"/>:</p>

                <c:choose>
                    <c:when test="${product.existence == true}">
                        <p class="existence-green"><fmt:message key="existence.value"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="existence-red"><fmt:message key="existence.notvalue"/></p>
                    </c:otherwise>
                </c:choose>

            </div>
            <p class="size-text"><fmt:message key="size.text"/></p>
            <div class="sizes">
                <p>15</p>
                <p>15.5</p>
                <p>16</p>
                <p>16.5</p>
                <p>17</p>
                <p>17.5</p>
                <p>18</p>
            </div>
            <p class="price">${product.price} BYN</p>
            <p class="description-name"><fmt:message key="descr.text"/></p>
            <p class="description-value">${product.description}</p>
            <p class="description-name"><fmt:message key="charact.name"/></p>

            <c:forEach items="${product.attributes}" var="attribute">

                <div class="characteristic">
                    <p class="charact-name"><fmt:message key="${attribute.name}"/>:</p>
                    <p class="charact-value"><fmt:message key="${attribute.value}"/></p>
                </div>

            </c:forEach>

            <button class="order"><fmt:message key="order"/></button>
            <c:choose>
                <c:when test="${(sessionScope.authorizedUser.role.ordinal() != 0) && (sessionScope.authorizedUser != null)}">
                    <c:url value="/product/delete.html" var="productDelUrl"/>
                    <c:url value="/product/update.html" var="productUpdUrl"/>
                    <a href="${productUpdUrl}?id=${product.id}" class="change"><fmt:message key="change"/></a>
                    <a href="${productDelUrl}?id=${product.id}" class="change"><fmt:message key="delete"/></a>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
