<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--@elvariable id="nameDictionary" type="java.lang.String"--%>
<html>
<head>
    <title>${nameDictionary}</title>
</head>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<%--<script src="<c:url value="../resources/js/requests.js"/>">Don't work</script>--%>
<script><%@include file="../resources/js/requests.js"%></script>

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
<table>
    <tr>
        <td>Put record to dictionary</td>
    </tr>
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <tr>
        <td><input id="putKey" name="key" type="text" value=""></td>
        <td><input id="putValue" name="value" type="text" value=""></td>
    </tr>
    <tr>
        <td><button onclick="putRecord()">Put</button></td>
    </tr>
</table>
<table>
    <tr>
        <td>Update dictionary record</td>
    </tr>
    <tr>
        <th>Key</th>
        <th>Value</th>
        <th>New value</th>
    </tr>
    <tr>
        <td><input id="updateKey" name="key" type="text" value=""></td>
        <td><input id="updateValue" name="value" type="text" value=""></td>
        <td><input id="updateNewValue" name="newValue" type="text" value=""></td>
    </tr>
    <tr>
        <td><button onclick="updateRecord()">Update</button></td>
    </tr>
</table>
<table>
    <tr>
        <td>Search by key</td>
    </tr>
    <tr>
        <th>Key</th>
    </tr>
    <tr>
        <td><input id="findKey" name="key" type="text" value=""></td>
    </tr>
    <tr>
        <td><button onclick="findByKey()">Search</button></td>
    </tr>
</table>
<table>
    <tr>
        <td>Search by value</td>
    </tr>
    <tr>
        <th>Value</th>
    </tr>
    <tr>
        <td><input id="findValue" name="key" type="text" value=""></td>
    </tr>
    <tr>
        <td><button onclick="findByValue()">Search</button></td>
    </tr>
</table>
<a href="/">Back to dictionaries list</a>
</body>
</html>