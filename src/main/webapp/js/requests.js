function removeRecord(key, value) {
    $.ajax({
        url: '/word-dictionary',
        type: 'DELETE',
        data: {key: key, value: value}
    })
        .done(function (msg) {
            alert("Record removed: " + msg);
        });
}