<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate View Feed</title>
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
			String currusername = (String) session.getAttribute("username");
			
			int userindex = -1;
			ArrayList<User> userbase = database.getUserbase();
			for(int i=0; i < userbase.size(); i++){
				if(userbase.get(i).getUsername().equals(currusername)) userindex=i;
			}
			
			User currentuser = userbase.get(userindex);
			String imgurl = currentuser.getImgURL();
			String currfname = currentuser.getFname();
			String currlname = currentuser.getLname();
			ArrayList<String> following = currentuser.getFollowing();
			
			ArrayList<Event> userfeed = currentuser.getFeed();
		%>
	</head>
	<body>
		<form name="viewprofile" method="POST">
			<div id ="toptitle">
				<div class = "titlehover" ><a href = "mainmenu.jsp" >Cinemate</a></div>		
			</div>	
			<br>
			<div id= "profiletop">
				<img class="img-circle" src = <%=imgurl %> alt = <%=imgurl %> />
				<h1><%=currfname %> <%=currlname %> </h1>
				<h2>@<%=currusername %></h2>
			</div>			
			<div id = "profilemiddle">
				<table cellspacing= 40 id = follows align=center>
					<tr>
						<th style="color:#2E5C9D; background-color:#749ACA">Feed</th>
					</tr>
					<%
						
						for(int i=0; i< userfeed.size(); i++){//printing user's feed
							String printout = "";
							printout += currentuser.getUsername() + " " + userfeed.get(i).getAction() +  " the movie " + userfeed.get(i).getMovie();
							if(userfeed.get(i).getAction().equals("Rated") && userfeed.get(i).getRating() != -1 ){
								printout += " (Rating: " + userfeed.get(i).getRating() + ")";
							}
							printout +=  "\n";
							out.print("<tr><td>");
							out.print(printout);
							out.print("</td></tr>");
						}		
						
						for(int i=0; i< currentuser.getFollowing().size(); i++){//printing followed users' feeds
							
							String tempuname = currentuser.getFollowing().get(i);//username of current followed user
							int tempindex = -1;
							for(int j=0; j < userbase.size(); j++){
								if(userbase.get(j).getUsername().equals(tempuname)) tempindex=j;
							}
							
							ArrayList<Event> usertempfeed = userbase.get(tempindex).getFeed();
							for(int j=0; j< usertempfeed.size(); j++){//printing followed user's feed
								String printout = "";
								printout += tempuname + " " + usertempfeed.get(j).getAction() +  " the movie " + usertempfeed.get(j).getMovie();
								if(usertempfeed.get(j).getAction().equals("Rated") && usertempfeed.get(j).getRating() != -1){
									printout += " (Rating: " + usertempfeed.get(j).getRating() + ")";
								}
								printout +=  "\n";
								out.print("<tr><td>");
								out.print(printout);
								out.print("</td></tr>");
							}
						}		
						
					
				    %>
				</table>
			</div>
			<br />
			<br />
			<br />
		</form>					
	</body>
</html>