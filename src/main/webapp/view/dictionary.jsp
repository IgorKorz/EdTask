<%@ page import="org.springframework.http.HttpRequest" %>
<%@ page import="java.net.URI" %>
<%@ page import="org.springframework.http.HttpHeaders" %>
<%@ page import="org.springframework.web.multipart.support.RequestPartServletServerHttpRequest" %>
<%@ page import="com.sun.deploy.net.HttpResponse" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="com.sun.deploy.net.MessageHeader" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="nameDictionary" type="java.lang.String"--%>
<%--@elvariable id="dictionary" type="java.util.Map"--%>
<html>
<head>
    <title>${nameDictionary}</title>
</head>
<%--<script type="text/javascript" src="<c:url value="../js/requests.js"/>"></script>--%>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
    function removeKey(key) {
        $.ajax({
            method: 'DELETE',
            url: '/world-dictionary/records?key=' + key,
            success: function () {
                alert("Key removed");
            }
        });
    }

    function removeRecord(key, value) {
        $.ajax({
            method: 'DELETE',
            url: '/world-dictionary/records',
            data: { key: key, value: value },
            success: function () {
                alert("Key removed");
            }
        });
    }

    function putRecord(key, value) {
        var record = { key: key, values: value };

        $.ajax({
            type: 'POST',
            contentType : "application/json",
            url:'/word-dictionary/records',
            data: JSON.stringify(record),
            dataType: "json"
        })
    }
</script>

<body>
<ul>
    ${nameDictionary}
    <ul>
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
<button onclick="putRecord('test', 'test')">Put</button>
<%
    URL url = new URL(request.getContextPath() + "/values/test");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();

    InputStream stream = connection.getInputStream();
    Scanner scanner = new Scanner(stream);

    JspWriter a;

    while (scanner.hasNext()) {
        out.println()
    }
%>
</body>
</html>
