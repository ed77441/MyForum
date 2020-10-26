function sendPutRequest(self) {	
	let form = $(self);
	
	if (self.checkValidity()) {		
	    $.ajax({
	        url: form.attr("action"),
	        type: "put",
	        data: form.serialize(),
	        success: function (msg) {
	        	$(location).attr("href", msg);
	        },
	        error: function (xhr) {
	        	alert(xhr.status);
	        }
	    });	
	}
    
    return false;
}