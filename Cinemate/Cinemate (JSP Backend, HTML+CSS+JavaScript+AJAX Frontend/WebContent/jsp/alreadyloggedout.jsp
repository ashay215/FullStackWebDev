<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Logout Error</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>

	</head>
	<body>
		<div id ="toptitle">				
			<div class = "titlehover" ><a href = "mainmenu.jsp" >Cinemate</a></div>	
		</div>	
		<div id = "toptext">
			<br>
			Error: Already logged out!
		</div>	
		<br>
		<div id = "middle">
			<br>
			<div id = "centerbox" style="text-align:left">
				Whoops! You appear to be logged out, so you can't access that page!
				<br>
				Please click <a href="authentication.jsp" style="text-decoration:underline">here</a> to login again!
			</div>				
		</div>
		<br />
		<br />
		<br />
	</body>
</html>