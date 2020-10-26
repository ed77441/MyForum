function sendDeleteRequest(url) {	
    $.ajax({
        url: url,
        type: "delete",
        success: function (msg) {
        	$(location).attr("href", msg);
        },
        error: function (xhr) {
        	alert(xhr.status);
        }
    });	
}