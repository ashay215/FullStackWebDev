<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Registration</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">

		<link href="../css/partials/main.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="../css/Registration.css"/>
		<script>
	    	function validate() {
	    		if(document.getElementById("fullname").value === "" || document.getElementById("username").value === "" || document.getElementById("password").value === "" || document.getElementById("imageurl").value === "" || document.getElementById("email").value === "") {
	    			document.getElementById("error").innerHTML = "ERROR: One or more of the requested fields is empty";

	    		}
	    		else if(document.getElementById("fullname").value === "Full Name" || document.getElementById("username").value === "Username" || document.getElementById("password").value === "Password" || document.getElementById("imageurl").value === "Image URL" || document.getElementById("email").value === "Email") {
	    			document.getElementById("error").innerHTML = "ERROR: One or more of the requested fields is empty";
	    		}
	    		else {
	    			var url = "/Whiteboard/RegistrationServlet?fullname="+document.getElementById('fullname').value+"&username="+document.getElementById('username').value+"&password="+document.getElementById('password').value+"&imageurl="+document.getElementById('imageurl').value+"&email="+document.getElementById('email').value;
	    			// create AJAX request
		    		var req = new XMLHttpRequest();
		    		req.open("GET", url, true);
		    		req.onreadystatechange = function () {
		    			if(req.readyState == 4 && req.status == 200) { 
		    				if(req.responseText === "No Error") { //if there is no error
		    					var url2 = "homepage.jsp";
		    		    		document.location.href = url2;
		    		    	}
		    				else { //else print error
		    					document.getElementById("error").innerHTML = req.responseText;
		    				}
		    			}
		    		}
		    		req.send(null);
	    		}
	    	}
    	</script>
	</head>
	<body>
		<header>
			<div class="header__wrapper">
				<div class="header__logo-container">
					<a class="logo-container__logo" href="../jsp/homepage.jsp"></a>
				</div>
			</div>
		</header>	
    	<div class="wrapper">
      		<div id="head">
				<h5 style="padding-bottom: 1cm;">Please enter your information.</h5>
      		</div>
      	</div>
      	<div id="main">
			<input style="margin-bottom: 7px; margin-top: 7px; width: 100%;" type="text" id="fullname" onfocus="if (this.value=='Full Name') this.value = ''" value="Full Name"/>
			<input style="margin-bottom: 7px; margin-top: 7px; width: 100%;" type="text" id="email" onfocus="if (this.value=='Email') this.value = ''" value="Email"/>
			<input style="margin-bottom: 7px; margin-top: 7px; width: 100%;" type="text" id="username" onfocus="if (this.value=='Username') this.value = ''" value="Username"/>
			<input style="margin-bottom: 7px; margin-top: 7px; width: 100%;" type="text" id="password" onfocus="if (this.value=='Password') this.value = ''" value="Password"/>
			<input style="margin-bottom: 7px; margin-top: 7px; width: 100%;" type="text" id="imageurl" onfocus="if (this.value=='Image URL') this.value = ''" value="Image URL"/>
			<button style= "margin-top: 10px; width: 100%; color: black;" type="submit" name="signup" onclick="validate()">Sign Up</button>
			<div id="error" style="color:red; font-size: 12px;"> </div>
      	</div>
      	<div id="foot">
      	</div>
  	</body>
</html>