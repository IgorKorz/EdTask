<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="nameDictionary" type="java.lang.String"--%>
<%--@elvariable id="dictionary" type="java.util.Map"--%>
<html>
<head>
    <title>${nameDictionary}</title>
</head>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<%--<script type="text/javascript" src="<c:url value="../js/requests.js"/>"></script>--%>
<script>
    function removeKey(key) {
        $.ajax({
            url: '/world-dictionary/records',
            method: "DELETE",
            data: { key: key },
            contentType: "text/html"
        })
            .done(function () {
                alert("Key removed");
            });
    }

    function removeRecord(key, value) {
        $.ajax({
            url: '/word-dictionary/records',
            method: "DELETE",
            data: { key: key, oldValue: value }
        })
            .done(function () {
                alert("Record removed");
            });
    }
</script>

<body>
<ul>
    ${nameDictionary}
    <ul>
        <c:forEach items="${dictionary}" var="property">
            <li>${property.key} <button onclick="removeKey('${property.key}')">remove</button>
                <ul>
                    <c:forEach items="${property.value}" var="value">
                        <li>${value} <button onclick="removeRecord('${property.key}', '${value}')">remove</button>
                    </c:forEach>
                </ul>
        </c:forEach>
    </ul>
</ul>
</body>
</html>
