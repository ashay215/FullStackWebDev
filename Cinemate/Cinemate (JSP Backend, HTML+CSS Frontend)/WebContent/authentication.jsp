<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Login Page</title>
		<link rel="stylesheet" type="text/css" href="main.css"/>
		
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
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			String error = "";
			if (request.getParameter("username") != null)
			{
				int userindex = -1;
				ArrayList<User> userbase = database.getUserbase();
				for(int i=0; i < userbase.size(); i++){
					if(userbase.get(i).getUsername().equals(username)) userindex=i;
				}
				
				boolean verified = true;
				if(userindex != -1) { //verifying username
					if(userbase.get(userindex).getPassword().equals(password)){//if valid username and matching password
						System.out.println("verified password");
						
					}
					else{
							verified = false;
							error = "Password does not match username!";
					}
				}
				else{ 
					verified = false;
					error = "Username does not exist!";
				}		
				
				if(verified){
					response.sendRedirect("mainmenu.jsp");
					session.setAttribute("username", username);
				}				
			}		
			
		%>		
		
	</head>
	<body>
		<form name="authentication" method="POST">
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
					<div style="text-align:left">Username</div>
					 <input type="text" name="username">
					 <div style="text-align:left">Password</div>
					 <input type="text" name="password" >
					 <br />				
					 <br /> 
					 <input type="submit" value="Login" >
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