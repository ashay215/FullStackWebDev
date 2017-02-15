<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>

<html>
	<head>
		<title>Cinemate View Profile</title>
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
			ArrayList<String> followers= new ArrayList<String>();
			
			for(int i=0; i < userbase.size(); i++){				
				User tempuser = userbase.get(i);
				if(tempuser.isFollowing(currusername)){
					followers.add(tempuser.getUsername());
				}
			}
			
		%>
	</head>
	<body>
		<form name="viewprofile" method="POST">
			<div id ="toptitle">
				<div class = "titlehover" ><a href = "mainmenu.jsp" >Cinemate</a></div>			
			</div>	
			<br>
			<div id= "profiletop">
				<img class="img-circle" src = <%=imgurl %> alt = <%=imgurl %>  />
				<h1><%=currfname %> <%=currlname %> </h1>
				<h2>@<%=currusername %></h2>
			</div>			
			<div id = "profilemiddle">
				<table cellspacing= 40 id = follows align=center>
					<tr>
						<th style="color:#2E5C9D; background-color:#749ACA">Followers</th>
						<th style="color:#2E5C9D; background-color:#749ACA">Following</th>
					</tr>
					<%
						if(following.size() >= followers.size()){
							for(int i = 0; i < following.size(); i++)
						    {
						    	out.println("<tr><td>");
						      	out.println((String)following.get(i));
						      	out.println("</td><td>");
						      	if(i<followers.size()){
						      		out.println((String)followers.get(i));
						      	}						      
						    	out.println("</td></tr>");
						    }
						}
						else{
							for(int i = 0; i < followers.size(); i++)
						    {
						    	out.println("<tr><td>");
						    	if(i< following.size()){
						    		out.println((String)following.get(i));
						    	}						      	
						      	out.println("</td><td>");
						      	out.println((String)followers.get(i));
						    	out.println("</td></tr>");
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