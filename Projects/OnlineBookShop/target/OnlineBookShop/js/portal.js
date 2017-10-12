$(document).ready(function() {
	
		alert("clicked");
		//e.preventDefault();
		getfile();
});
/**
 * author Bharathakavi since 5.0
 * 
 * @returns
 */
function getfile() {
	var jobj = {
		"operation" : "display",
	};
	alert(jobj);
	// var d=$("id").val();
	var d = JSON.stringify(jobj);
	alert(d);
	ajaxObj = {
		type : "POST",
		url : "rest/book/displayFile",
		data : d,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			alert("error");
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			alert("success " + data.all);
			var theTemplateScript = $("#file-template").html();

			// Compile the template
			var theTemplate = Handlebars.compile(theTemplateScript);

			// Define our data object
			var context = {
				"Files" : data
			};

			// Pass our data to the template
			var theCompiledHtml = theTemplate(context);

			// Add the compiled html to the page
			$('.placeholder').html(theCompiledHtml);

		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
		dataType : "json" // request JSON
	};

	$.ajax(ajaxObj);

}
