function uuidv4() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}
	
$(document).ready(function(){
	let executed = false;
	
	let dropdown = $("#noti-dropdown");
	let status = 0;
	let currentID = registerNotification();
	
	function markNotificationSeen() {
		$("#unseen-badge").addClass("d-none");
		
	    $.ajax({
	        url: "/notification",
	        type: "post"
	    });	
	}
	
	function setNoteBox(msg) {
    	if (msg !== "no data" && msg !== "") {
            let obj = JSON.parse(msg);
            let notifications = obj.notifications;
            let noteBox = $("#notification-box");
            let unseenBadge = $("#unseen-badge");
            noteBox.empty();
            
            

            if (notifications.length != 0) {
            	for (let i in notifications) {
            		let notification = notifications[i];
            		let wrapper = $("<div class='p-3 notilink-wrapper'></div>")
            		let rowElement = $("<a class='text-secondary notilink'></a>");
            		
            		let caption = notification.caption;
            		let comment = notification.comment;
            		
            		
            		if (notification.caption.length > 10) {
            			caption = caption.substring(0, 10) + "...";
            		}
            		
            		if (notification.comment.length > 20) {
            			comment = comment.substring(0, 20) + "...";
            		}
            		
            		rowElement.text(notification.replyer + " 在 " + caption + " 說 " + comment);
            		rowElement.attr("href", notification.commentLocation);
            		wrapper.append(rowElement);
            		noteBox.append(wrapper);
            		noteBox.append($('<div class="dropdown-divider"></div>'));
            	}		            	
            }
            else {
            	noteBox.append($("<div class='m-3'>目前沒有任何通知</div>"));
        		noteBox.append($('<div class="dropdown-divider"></div>'));
            }
            
    		let trailing = 
    			$("<div class='p-3 notilink-wrapper'><a class='text-primary notilink' href='/all-notification'>查看所有通知</a></div>");
    		noteBox.append(trailing);

            if (obj.unseen == 0) {
            	unseenBadge.addClass("d-none");
            }
            else {
            	unseenBadge.removeClass("d-none");
            	
            	if (dropdown.hasClass("show")) {
            		markNotificationSeen();
            	} 
            }
            
            unseenBadge.text(obj.unseen);
            status = obj.status;
    	}

		if (msg !== "") {
			currentID = registerNotification();
		}
	}
	
	
	function registerNotification() {	
		let newID = uuidv4();
	    $.ajax({
	        url: "/notification?id=" + newID + "&status=" + status,
	        type: "get",
	        success: setNoteBox,
	        error: function (xhr) {
	        	alert(xhr.status);
	        },
	        timeout: function() {
	        	alert("server not responding");
	        }
	    });	
	    
	    return newID;
	}
	
	let cancelNotification = function(e){
		if(!executed){
			executed = true;

			fetch("/notification?id=" + currentID, {
			    method: 'delete',
			    keepalive: true,
			    mode: 'same-origin'
			});
		}				
	};
	
	$(document).on('click', '.dropdown-menu', function (e) {
		  e.stopPropagation();
	});
	
	dropdown.on("shown.bs.dropdown", markNotificationSeen);
	$(window).on("unload beforeunload", cancelNotification);	
});


