<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Signup Page</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		
		<%
		
			try{
				Database database = (Database) session.getAttribute("database");
				
				ArrayList<User> userbase = database.getUserbase();			
			}
			catch(NullPointerException e){
				System.out.println("already exited!");
				response.sendRedirect("alreadyexited.jsp");
				return;
			}
			session = request.getSession();
			Database database = (Database) session.getAttribute("database");
			
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String imageurl = request.getParameter("image");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			String error = "";
			if (fname != null)
			{
				System.out.print("HRE");
				//int userindex = -1;
				/*
				ArrayList<User> userbase = database.getUserbase();
				
				boolean verified = true;
					
				
				if(verified){
					database.addUser(fname, lname, username, password, imageurl );
					session.setAttribute("username", username);
					response.sendRedirect("viewfeed.jsp");					
				}				
				*/
				database.addUser(fname, lname, username, password, imageurl);
				session.setAttribute("username", username);
				response.sendRedirect("viewfeed.jsp");	
			}		
			
		%>		
		
	</head>
	<body>
		<form name="signup" method="POST">
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
					 <input type="text" name="fname" value="First Name" onfocus="if (this.value != '') {this.value=''}" >
					 <br />				
					 <br /> 
					 <input type="text" name="lname" value="Last Name" onfocus="if (this.value != '') {this.value=''}" >
					 <br />				
					 <br /> 
					 <input type="text" name="Username" value="Username" onfocus="if (this.value != '') {this.value=''}" >
					 <br />				
					 <br /> 
					 <input type="text" name="password" value="Password" onfocus="if (this.value != '') {this.value=''}" >
					 <br />				
					 <br /> 
					 <input type="text" name="imgurl" value="Image URL" onfocus="if (this.value != '') {this.value=''}" >
					 <br />				
					 <br /> 
					 <input type="submit" value="Sign Up" >
					 <br />
					 <br />
					 <div style="color:red">
						<%=error %>
					 </div>
				</div>				
			</div>
			<br />
			<br />
			<br />
		</form>		
	</body>
</html>