$(document).ready(function() {

	$("#avail").click(function(e) {
		alert("clicked");
		e.preventDefault();
		getbook();

	});
});
/**
 * author Bharathakavi since 5.0
 * 
 * @returns
 */
function getbook() {
	var jobj = {
		"operation" : "display",
	};
	alert(jobj);
	// var d=$("id").val();
	var d = JSON.stringify(jobj);
	alert(d);
	ajaxObj = {
		type : "POST",
		url : "rest/book/display",
		data : d,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			alert("error");
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			alert("success " + data[0].name);
			var theTemplateScript = $("#available-template").html();

			// Compile the template
			var theTemplate = Handlebars.compile(theTemplateScript);

			// Define our data object
			var context = {
				"Book" : data
			};

			// Pass our data to the template
			var theCompiledHtml = theTemplate(context);

			// Add the compiled html to the page
			$('.content-placeholder').html(theCompiledHtml);

		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
		dataType : "json" // request JSON
	};

	$.ajax(ajaxObj);

}
/*
 * function to display in handle bars. $(function() { // Grab the template
 * script var theTemplateScript = $("#available-template").html(); // Compile
 * the template var theTemplate = Handlebars.compile(theTemplateScript); //
 * Define our data object var context = { "Book" : [ { "book" : "London
 * Journey", "author" : "Barath", "price" : "$ 150" }, { "book" : "London
 * Journey", "author" : "Barath", "price" : "$ 150" }, { "book" : "London
 * Journey", "author" : "Barath", "price" : "$ 150" } ] }; // Pass our data to
 * the template var theCompiledHtml = theTemplate(context); // Add the compiled
 * html to the page $('.content-placeholder').html(theCompiledHtml); });
 */
