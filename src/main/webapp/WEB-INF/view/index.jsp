<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="dictionaries" type="java.util.List"--%>
<html>
<head>
    <title>Dictionaries</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Dictionary</th>
    </tr>
    <c:forEach items="${dictionaries}" var="dictionary">
        <tr>
            <td><a href="${dictionary}">${dictionary}</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
