$(document).ready(function () {

    $("#uploadBtn").click(function (upload) {
    	upload.preventDefault();
    	uploadFileAjax();
    });

});

function uploadFileAjax() {

    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    data.append("CustomField", "This is some extra data, testing");

    $("#uploadBtn").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/api/upload",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#uploadBtn").prop("disabled", false);

        },
        error: function (e) {
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#uploadBtn").prop("disabled", false);
        }
    });
}