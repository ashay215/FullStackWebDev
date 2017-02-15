<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Search Movies</title>
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
			ArrayList<Movie> userbase = database.getMoviebase();
			ArrayList<String> foundmovies = new ArrayList<String>();
		%>
	</head>
	<body>
		<form name="authentication" method="POST">
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
					<a href="moviesearchactor.jsp"> 1. Search by Actor</a>
					<br/>
					<a href="moviesearchtitle.jsp">2. Search by Title</a>
					<br />
					<a href="moviesearchgenre.jsp">3. Search by Genre</a>
					<br/>
					<a href="mainmenu.jsp">4. Return to Login Menu</a>
				</div>				
			</div>
			<br />
			<br />
			<br />
		</form>				
	</body>
</html>