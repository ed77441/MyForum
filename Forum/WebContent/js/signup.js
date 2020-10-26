$(document).ready(function() {

	let form = $("#signup");

	form.on("input", function() {
		let password = $("#password");
		let confirmPassword = $("#password-confirm");
		let msg = password.val() !== confirmPassword.val() ? "error" : "";
		
		confirmPassword[0].setCustomValidity(msg);
	});
});