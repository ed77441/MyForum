$(document).ready(function() {
	let timeDifference = function (current, previous) {

	    const msPerMinute = 60 * 1000;
	    const msPerHour = msPerMinute * 60;
	    const msPerDay = msPerHour * 24;
	    const msPerMonth = msPerDay * 30;
	    const msPerYear = msPerDay * 365;

	    let elapsed = current - previous;
	    
	    if (elapsed < msPerMinute) {
	         return Math.round(elapsed/1000) + "秒鐘前";   
	    }

	    else if (elapsed < msPerHour) {
	         return Math.round(elapsed/msPerMinute) + '分鐘前';   
	    }

	    else if (elapsed < msPerDay ) {
	         return Math.round(elapsed/msPerHour ) + '小時前';   
	    }

	    else if (elapsed < msPerMonth) {
	        return Math.round(elapsed/msPerDay) + '天前';   
	    }
	    else if (elapsed < msPerYear) {
	    	return Math.round(elapsed/msPerMonth) + '月前';
	    }
	    else {
	        return Math.round(elapsed/msPerYear) + '年前';
	    }
	} 
	
	
	let callback = function() {
		$(".time-box").each(
			function() {
				let ele = $(this);
				let timeValue = ele.find(".time-value");
				let timeDisplay = ele.find(".time-display");
				let timeMessage = timeDifference(new Date(), new Date(timeValue.text()));			
				
				if (timeMessage !== timeDisplay.text()) {
					timeDisplay.text(timeMessage);
				}
			}
		);
	};
	
	callback();
	setInterval(callback, 1000);
});
 