<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Main Menu</title>
		<link rel="stylesheet" type="text/css" href="main.css"/>
		
		<%		
			int usertestindex = -1;
			try{
				String currusername = (String) session.getAttribute("username");
				Database database = (Database) session.getAttribute("database");
				
				ArrayList<User> userbase = database.getUserbase();
				for(int i=0; i < userbase.size(); i++){
					if(userbase.get(i).getUsername().equals(currusername)) usertestindex=i;
				}				
			}
			catch(NullPointerException e){
				System.out.println("already exited!");
				response.sendRedirect("alreadyexited.jsp");
				return;
			}
			if(usertestindex == -1){
				System.out.println("already logged out!");
				response.sendRedirect("alreadyloggedout.jsp");
				return;
			}
			session = request.getSession();
			Database database = (Database) session.getAttribute("database");
			String curruser = (String) session.getAttribute("username");
		%>
	</head>
	<body>
		<form name="mainmenu" method="POST">
			<div id ="toptitle">				
				<div class = "titlehover" ><a href = "mainmenu.jsp" >Cinemate</a></div>	
			</div>	
			<div id = "toptext">
				<br>
				Logged in, what would you like to do?	
			</div>	
			<br>
			<div id = "middle">
				<br>
				<div id = "centerbox" style="text-align:left">
					<a href="searchusers.jsp">1. Search Users</a>
					<br/>
					<a href="searchmovies.jsp">2. Search Movies</a>
					<br />
					<a href="viewfeed.jsp">3. View Feed</a>
					<br/>
					<a href="viewprofile.jsp">4. View Profile</a>
					<br/>
					<a href="logout.jsp">5. Logout</a>
					<br/>
					<a href="exit.jsp">6. Exit</a>
				</div>				
			</div>
			<br />
			<br />
			<br />
		</form>		
	</body>
</html>