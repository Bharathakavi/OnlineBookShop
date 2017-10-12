/*Method used to intimate the user to register in the website for normal users.*/
function message() {
	alert("You must login to Buy this Book");
}

/*Method for creating content in new child window*/
var popupwindow;
function poptastic(url)
{
	popupwindow=window.open(url,'name','height=400,width=200');
	
}
function parent_disable() {
	if(popupWindow && !popupWindow.closed)
	popupWindow.focus();
	}

function rate(name,a,p){
	var book=name;
	var author=a;
	var price=p;
	alert("Book Name:  "+book+"\nAuthor Name:  "+author+"\nPrice:  $ "+price+".50\nAvailable");
}