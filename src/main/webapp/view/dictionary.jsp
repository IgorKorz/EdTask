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
    function putRecord() {
        var key = document.getElementById("putKey").value;
        var value = document.getElementById("putValue").value;
        var record = { key: key, value: value };

        $.ajax({
            type: 'POST',
            contentType : "application/json",
            url:'/word-dictionary/records',
            data: JSON.stringify(record),
            dataType: "json"
        });

        location.reload();
    }

    function updateRecord() {
        var key = document.getElementById("updateKey").value;
        var oldValue = document.getElementById("updateValue").value;
        var newValue = document.getElementById("updateNewValue").value;
        var record = { key: key, oldValue: oldValue, newValue: newValue };

        $.ajax({
            type: 'PUT',
            contentType : "application/json",
            url:'/word-dictionary/records',
            data: JSON.stringify(record),
            dataType: "json"
        });

        location.reload();
    }

    function removeKey(key) {
        var removedKey = { key: key };

        $.ajax({
            type: 'DELETE',
            contentType : "application/json",
            url:'/word-dictionary/records/keys',
            data: JSON.stringify(removedKey),
            dataType: "json"
        });

        location.reload();
    }

    function removeRecord(key, value) {
        var record = { key: key, value: value };

        $.ajax({
            type: 'DELETE',
            contentType : "application/json",
            url:'/word-dictionary/records',
            data: JSON.stringify(record),
            dataType: "json"
        });

        location.reload();
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

<table>
    <tr>
        <td>Put record to dictionary</td>
    </tr>
    <tr>
        <td>Key</td>
        <td>Value</td>
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
        <td>Key</td>
        <td>Value</td>
        <td>New value</td>
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
</body>
</html>
