<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Signup Page</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		<script src="../js/main.js" type="text/javascript"></script>	
		<script>
		
		function signup(){
			var fname = document.getElementById("fname").value;
			var lname = document.getElementById("lname").value;
			var uname = document.getElementById("username").value;
			var pass = document.getElementById("password").value;
			var img = document.getElementById("imgurl").value;
			
			//alert(fname);
			if(fname.length == 0 || lname.length ==0 || uname.length==0 || pass.length==0 || img.length==0){
				document.getElementById("error_message").innerHTML = "Fill in all fields!";
			}
			else{
				return errorCheck('/RegistrationServlet', 'viewfeed.jsp', ['fname', 'lname', 'username', 'password', 'imgurl'], 5, 'error_message');
			}
			
			
		}
		
		</script>	
	</head>
	<body>
		<div id ="toptitle">
			<div class = "titlehover" ><a href = "entry_vipinkum.jsp" >Cinemate</a></div>			
		</div>	
		<div id = "toptext">
			<br>
			File parsed, please log in.		
		</div>	
		<br>
		<div id = "middle">
			<br>
			<div id = "centerbox">
				<h3>Please enter your information.</h3>
				<div style="text-align:left">First name</div>
				 <input type="text" id="fname" >
				 <br />				
				 <br /> 
				 <div style="text-align:left">Last name</div>
				 <input type="text" id="lname" >
				 <br />				
				 <br /> 
				 <div style="text-align:left">Username</div>
				 <input type="text" id="username"  >
				 <br />				
				 <br /> 
				 <div style="text-align:left">Password</div>
				 <input type="text" id="password"  >
				 <br />				
				 <br /> 
				 <div style="text-align:left">Image URL</div>
				 <input type="text" id="imgurl"  >
				 <br />				
				 <br /> 
				 <input type="submit" value="Sign Up" onclick= signup()>
				 <br />
				 <br />
				 
				<div class=error_message id = "error_message" style="color:red"></div>
			</div>				
		</div>
		<br />
		<br />
		<br />	
	</body>
</html>