//main.js file from Assignment 3 solution code 

//called in login.jsp, file_chooser.jsp, and sign_up.jsp
//parameters:
//servletName -- servlet that the ajax call will go to
//jspName -- window location to navigate to if there was no error
//paramArgs -- array of ids of all the parameters that need to be included in ajax request
//numArgs -- number of elements in paramArgs
//errorDivName -- id of the error div
function errorCheck (servletName, jspName, paramArgs, numArgs, errorDivName){
	
	
	//gets the path
	var path = "/"+window.location.pathname.split("/")[1];
	//create url with first parameter from paramArgs
	var url = path + servletName+"?"+paramArgs[0]+"="+document.getElementById(paramArgs[0]).value;
	//append the rest of the elements in paramArgs
	for (let i = 1; i<numArgs; i++){
		url += "&"+paramArgs[i]+"="+document.getElementById(paramArgs[i]).value;
	}
	console.log(url);
	
	//send synchronous ajax call to servelt
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", url, true);
	xhttp.onreadystatechange = function () {
		if(xhttp.readyState == 4 && xhttp.status == 200) { 
			if (xhttp.responseText.trim().length > 0) {
				//alert(xhttp.responseText);
				document.getElementById(errorDivName).innerHTML = xhttp.responseText;
			}
			else{
				//otherwise navigate to the destination jsp
				window.location.href = path + "/jsp/"+jspName;
			}
		}
	}
	xhttp.send(null);	
}


function validate() {
	document.getElementById("success").innerHTML = "";
	document.getElementById("error").innerHTML = ""
	var url = "../ChangePictureServlet?newimgURL="+document.getElementById('newimgURLField').value;
	console.log(url);
	// create AJAX request
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			if(req.responseText === "noerror") { //if there is no error
				document.getElementById("success").innerHTML = "Success! Picture changed!";
			}
		}
	}
	req.send(null);
}