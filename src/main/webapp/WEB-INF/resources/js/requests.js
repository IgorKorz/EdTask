function putRecord() {
    var key = document.getElementById("putKey").value;
    var value = document.getElementById("putValue").value;
    var record = { key: key, value: value };

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: location.href + '/records',
        data: JSON.stringify(record),
        dataType: 'json'
    });

    location.reload();
}

function findByKey() {
    var key = document.getElementById("findKey").value;
    var loca = location.href;

    $.ajax({
        type: 'GET',
        contentType: 'text/html',
        url: location.href += 'values/' + key
    });

    var toBack = document.createElement("a");
    toBack.innerHTML = "Back to dictionary";
    toBack.setAttribute("href", loca);

    var body = document.getElementById("body");
    document.body.insertBefore(toBack, body);
}

function findByValue() {
    var value = document.getElementById("findValue").value;
    var loca = location.href;

    $.ajax({
        type: 'GET',
        contentType: 'text/html',
        url: location.href += 'keys/' + value
    });

    var toBack = document.createElement("a");
    toBack.innerHTML = "Back to dictionary";
    toBack.setAttribute("href", loca);

    var body = document.getElementById("body");
    document.body.insertBefore(toBack, body);
}

function updateRecord() {
    var key = document.getElementById("updateKey").value;
    var oldValue = document.getElementById("updateValue").value;
    var newValue = document.getElementById("updateNewValue").value;
    var record = { key: key, oldValue: oldValue, newValue: newValue };

    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: location.href + '/records',
        data: JSON.stringify(record),
        dataType: 'json'
    });

    location.reload();
}

function removeKey(key) {
    var removedKey = { key: key };

    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: location.href + '/records/keys',
        data: JSON.stringify(removedKey),
        dataType: 'json'
    });

    location.reload();
}

function removeRecord(key, value) {
    var record = { key: key, value: value };

    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: location.href + '/records',
        data: JSON.stringify(record),
        dataType: 'json'
    });

    location.reload();
}