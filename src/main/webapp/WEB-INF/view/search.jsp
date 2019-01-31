<%--@elvariable id="toDictionary" type="java.lang.String"--%>
<%--@elvariable id="nameDictionary" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${nameDictionary}</title>
</head>
<body>
<ul>
    ${nameDictionary}
    <ul>
        <%--@elvariable id="dictionary" type="java.util.List"--%>
        <c:forEach items="${dictionary}" var="property">
        <li>${property.key} <button onclick="removeKey('${property.key}')">remove</button>
            <ul>
                <c:forEach items="${property.values}" var="value">
                <li>${value} <button onclick="removeRecord('${property.key}', '${value}')">remove</button>
                    </c:forEach>
            </ul>
            </c:forEach>
    </ul>
</ul>
<a href="${toDictionary}">Back to dictionary</a>
</body>
</html>
