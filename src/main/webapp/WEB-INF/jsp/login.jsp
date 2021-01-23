<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 06.01.2021
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${sessionScope.authorizedUser == null}">
        <html title="Вход в систему">
        <head>
            <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
            <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
        </head>
        <h2>Вход в систему</h2>
        <c:url value="/login.html" var="loginUrl"/>
        <c:url value="/index.html" var="indexUrl"/>
        <a href="${indexUrl}">На главную</a>
        <form action="${loginUrl}" method="post">
            <label for="login">Имя пользователя:</label>
            <input type="text" id="login" name="login" value="${param.login}">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password">
            <button type="submit">Войти</button>
        </form>
        <c:url value="/register.html" var="registerUrl"/>
        <a href="${registerUrl}">Register</a>
        </html>
    </c:when>
    <c:otherwise>
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
        <c:url value="/logout.html" var="logoutUrl"/>
        <a href="${logoutUrl}">Logout</a>
        <c:choose>
            <c:when test="${sessionScope.authorizedUser.role.ordinal() == 2}">
                <table style="border: 2px solid gray; margin-top: 10px;">
                    <c:url value="/register.html" var="registerUrl"/>
                    <a href="${registerUrl}">Create</a>
                    <tr>
                        <th>Login</th>
                        <th>Role</th>
                        <th colspan="2"></th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.login}</td>
                            <td>${user.role.getName()}</td>
                            <c:url value="/user/upgrade.html" var="upgradeUrl"/>
                            <td><a href="${upgradeUrl}?id=${user.id}">Upgrade</a></td>
                            <c:url value="/user/downgrade.html" var="downgradeUrl"/>
                            <td><a href="${downgradeUrl}?id=${user.id}">Downgrade</a></td>
                            <c:url value="/user/delete.html" var="deleteUrl"/>
                            <td><a href="${deleteUrl}?id=${user.id}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
        </c:choose>
        <c:url value="/index.html" var="indexUrl"/>
        <a href="${indexUrl}">На главную</a>
    </c:otherwise>
</c:choose>

