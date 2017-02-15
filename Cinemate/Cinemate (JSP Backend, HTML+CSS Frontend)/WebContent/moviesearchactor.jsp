<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Movie Search Actor</title>
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
			String actorplacement="";
			ArrayList<String> foundmovies = new ArrayList<String>();
			String tosearch = request.getParameter("searchbar");
			if (request.getParameter("searchbar") != null){
				actorplacement = " for: " + tosearch;
				Database database = (Database) session.getAttribute("database");
				ArrayList<Movie> moviebase = database.getMoviebase();			
				
				for(int i=0; i< moviebase.size(); i++){//for all movies
					ArrayList<String> tempactors = moviebase.get(i).getActors();
					//System.out.println(moviebase.get(i).getTitle());
					for(int j = 0; j < tempactors.size(); j++){//for all actors in the movie
						//System.out.println(tempactors.get(j));
						if(tosearch.equalsIgnoreCase(tempactors.get(j))){
							foundmovies.add(moviebase.get(i).getTitle());
							
						}
					}
				}
			}
				
				
			
		%>
	</head>
	<body>
		<form name="authentication" method="POST">
			<div id ="toptitle">
				<div class = "titlehover" ><a href = "mainmenu.jsp" >Cinemate</a></div>	
			</div>				
			<div id = "middle">
				
				<div id = "centerbox" style="text-align:left">		
				Searching movies by actor <%=actorplacement %>			
					<table>
						<tr>
							<td><input type="text" name="searchbar"></td>
							<td>                                    </td>
						 	<td><input type="submit" value="Search"></td>
					 	</tr>
					</table>				
					<br />
					<div id="scrollbox">
						<div style="background-color:white; color:black">Search Results</div>
						<table style="width:100%" class="resultstable">
						
							<% 
								if(foundmovies.size() == 0 && request.getParameter("searchbar") != null){
									out.print("<tr><td>No results!</td></tr");
								}
								for(int i=0; i < foundmovies.size(); i++){
									out.print("\n<tr><td>\n");
									out.println(foundmovies.get(i));
									out.print("</td></tr>\n");
								}
								
								
							%>
						</table>
					</div>
				</div>
			</div>	
			<br />
			<br />
			<br />			
		</form>				
	</body>
</html>