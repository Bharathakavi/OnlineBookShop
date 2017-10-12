$(document).ready(function() {
	
		alert("clicked");
		//e.preventDefault();
		getlabelcount();
});
/**
 * author Bharathakavi since 5.0
 * 
 * @returns
 */
function getlabelcount() {
	var jobj = {
		"operation" : "count",
	};
	alert(jobj);
	// var d=$("id").val();
	var d = JSON.stringify(jobj);
	alert(d);
	ajaxObj = {
		type : "POST",
		url : "rest/book/labels",
		data : d,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			alert("error");
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			alert("success " + data.all);
			var theTemplateScript = $("#label-template").html();

			// Compile the template
			var theTemplate = Handlebars.compile(theTemplateScript);

			// Define our data object
			var context = {
			"all" : data.all,"nonlabeled":data.nonlabeled,"priviledged":data.priviledged
			};

			// Pass our data to the template
			var theCompiledHtml = theTemplate(context);

			// Add the compiled html to the page
			$('.labelcount').html(theCompiledHtml);

		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
		dataType : "json" // request JSON
	};

	$.ajax(ajaxObj);

}
