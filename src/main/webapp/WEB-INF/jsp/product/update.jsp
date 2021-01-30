<%--
  Created by IntelliJ IDEA.
  User: YK-TW
  Date: 21.01.2021
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/custom-func.tld" prefix="fn" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename='locale.locale'/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/inputForm.css"/>
</head>
<body>
<c:url value="/product/save.html" var="productSaveUrl"/>
<form action="${productSaveUrl}" method="post" enctype="multipart/form-data"
      onsubmit="return validateSaveProduct(this)">
    <input type="text" name="id" value="${product.id}" style="display: none">
    <label>Name</label>
    <input type="text" name="name" value="${product.name}">
    <label>Price</label>
    <input type="text" name="price" value="${product.price}">
    <label>Existence</label>
    <input type="checkbox" name="existence" checked="${product.existence}">
    <label>Description</label>
    <input type="text" name="description" value="${product.description}">
    <label>Visibility</label>
    <input type="checkbox" name="visibility" checked="${product.visibility}">
    <label>Image</label>
    <input type="file" name="file"/>
    <c:forEach items="${attributes}" var="attribute">
        <div class="characteristic">
            <p class="charact-name"><fmt:message key="${attribute.name}"/>:</p>
            <p class="charact-value"><fmt:message key="${attribute.value}"/></p>
            <c:choose>
                <c:when test="${product != null}">
                    <c:choose>
                        <c:when test="${fn:contains(product.attributes, attribute)}">
                            <input name="attribute" value="${attribute.id}" type="checkbox" checked="checked">
                        </c:when>
                        <c:otherwise>
                            <input name="attribute" value="${attribute.id}" type="checkbox">
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <input name="attribute" value="${attribute.id}" type="checkbox">
                </c:otherwise>
            </c:choose>


        </div>

    </c:forEach>
    <input type="submit" class="submit" value="Submit">
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>
</body>
</html>
