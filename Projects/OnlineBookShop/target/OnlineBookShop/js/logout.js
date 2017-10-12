/*JQuery method for enabling the ajax call.*/
$(document).ready(function() {
	// var logout_link = $("#out");

	$("#out").click(function(e) {
		alert("click");
		e.preventDefault();
		getpns();
	});
});

/* Ajax method for calling the logout method in controller. */
function getpns() {
	alert("in");
	ajaxObj = {
		type : "POST",
		url : "rest/book/logout",
		error : function(jqXHR, textStatus, errorThrown) {
			alert("error");
			alert(jqXHR + " " + textStatus + " " + errorThrown);
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			var html_string = "";
			alert(data);
			location.assign("Homepage.html");
		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
	// dataType : "json" // request JSON
	};

	$.ajax(ajaxObj);

}