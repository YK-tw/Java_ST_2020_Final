<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 22.01.2021
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html title="Регистрация в систему">
<link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>

<h2>Вход в систему</h2>
<c:url value="/register.html" var="registerUrl"/>
<form action="${registerUrl}" method="post">
    <label for="login">Имя пользователя:</label>
    <input type="text" id="login" name="login">
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password">
    <button type="submit">Войти</button>
</form>
<c:url value="/index.html" var="indexUrl"/>
<a href="${indexUrl}">На главную</a>
</html>
