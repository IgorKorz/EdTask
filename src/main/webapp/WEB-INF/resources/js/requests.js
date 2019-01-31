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

    $.ajax({
        type: 'GET',
        contentType: 'text/html',
        url: location.href += '/values/' + key
    });
}

function findByValue() {
    var value = document.getElementById("findValue").value;

    $.ajax({
        type: 'GET',
        contentType: 'text/html',
        url: location.href += '/keys/' + value
    });
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