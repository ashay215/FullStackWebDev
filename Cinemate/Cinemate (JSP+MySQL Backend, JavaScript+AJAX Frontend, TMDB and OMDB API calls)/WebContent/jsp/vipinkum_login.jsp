<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Login Page</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		<script src="../js/main.js" type="text/javascript"></script>
		<script>
		
		function login(){
			var uname = document.getElementById("usernameField").value;
			var pass = document.getElementById("passwordField").value;
			
			if(uname.length==0 || pass.length==0){
				document.getElementById("error_message").innerHTML = "Fill in all fields!";
			}
			else{
				return errorCheck('/LoginServlet', 'viewfeed.jsp', ['usernameField', 'passwordField'], 2, 'error_message');
			}		
		}	
		</script>	
		
	</head>
	<body>
		
		<div id ="toptitle">
			<div class = "titlehover" ><a href = "vipinkum_login.jsp" >Cinemate</a></div>			
		</div>	
		<div id = "toptext">
			<br>
			Please log in.		
		</div>	
		<br>
		<div id = "middle">
			<br>
			<div id = "centerbox">
				<div style="text-align:left">Username</div>
					<input type="text" name="username" id="usernameField">
					<div style="text-align:left">Password</div>
					<input type="text" name="password" id="passwordField" >
					<br />				
					<br /> 
					<input type="submit" value="Login" onclick= login()>
					<br />
					<br />
					<a href = "signup.jsp"><button type="button">Sign up</button></a>
					<br />
					<div class=error_message id = "error_message" style="color:red"></div>
			</div>				
		</div>
		<br />
		<br />
		<br />		
	</body>
</html>