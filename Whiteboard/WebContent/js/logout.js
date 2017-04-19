/**
 * 
 */
function logout()
{
	var url = "../LogoutServlet";
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			window.location = "../html/LandingPage.html";
		}
	}
	req.send(null);
}
