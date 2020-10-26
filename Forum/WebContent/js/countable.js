$(document).ready(function() {
	let textarea = $(".countable > textarea");
	let textcounter = $(".countable > .text-counter");
	let limit = textarea.attr("maxLength");
	
	let updateTextCounter = function() {
		textcounter.text(textarea.val().length + "/" + limit);
	};
	
	
	textarea.on("input", updateTextCounter);
	
	textarea.on("focus", function() {
		textcounter.removeClass("hide");
		
	});
	
	textarea.on("blur", function() {
		textcounter.addClass("hide");
	});
	
	updateTextCounter();
});
