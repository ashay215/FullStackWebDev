<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  	<head>
    	<title>Login Menu</title>
    	<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">

		<link href="../css/partials/main.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
    	<link rel="stylesheet" type="text/css" href="../css/loginpage.css" />
    	<script>
	    	function validate() {
    			var url = "/Whiteboard/LoginServlet?usernameField="+document.getElementById('usernameField').value+"&passwordField="+document.getElementById('passwordField').value;
    			// create AJAX request
	    		var req = new XMLHttpRequest();
	    		req.open("GET", url, true);
	    		req.onreadystatechange = function () {
	    			if(req.readyState == 4 && req.status == 200) { 
	    				if(req.responseText === "No Error") { //if there is no error
	    					approve(); //approve login credentials
	    				}
	    				else { //else print error
	    					document.getElementById("error").innerHTML = req.responseText;
	    				}
	    			}
	    		}
	    		req.send(null);
	    	}
	    	function approve() {
	    		var url = "homepage.jsp";
	    		document.location.href = url;
	    	}
    	</script>
  	</head>
  	<body>
    	<header>
			<div class="header__wrapper">
				<div class="header__logo-container">
					<a class="logo-container__logo" href="../html/LandingPage.html"></a>
				</div>
			</div>
		</header>	
      	<section class="login-container">
      		<h1>Log In</h1>

			<input type="text" placeholder="Username" id="usernameField"/>
			<input type="password" id="passwordField"/>

			<div class="login__footer">
				<button type="submit" name="login" onclick="validate()">Log In</button>
				<div class="login__error-message"></div>
			</div>
      	</section>
  	</body>
</html>