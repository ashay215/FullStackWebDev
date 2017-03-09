<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList, java.lang.Math" %>

<html>
	<head>
		<title>Cinemate View Profile</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		<script src="http://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
		<script src ="../js/follow.js"></script>
		
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
			String userurl = "\"userpage.jsp?uname=" + currusername + "\"";
			
			ArrayList<User> userbase = database.getUserbase();
			
			int userindex = -1;			
			for(int i=0; i < userbase.size(); i++){
				if(userbase.get(i).getUsername().equals(currusername)) userindex=i;
			}
			User curruser = userbase.get(userindex);
			String currimgurl = curruser.getImgURL();
			
			String pageuname = request.getParameter("uname");
			int pageuindex = -1;
			for(int i=0; i < userbase.size(); i++){
				if(userbase.get(i).getUsername().equals(pageuname)) pageuindex=i;
			}
			
			User pageuser = userbase.get(pageuindex);
			String imgurl = pageuser.getImgURL();
			String pagefname = pageuser.getFname();
			String pagelname = pageuser.getLname();
			ArrayList<String> following = pageuser.getFollowing();
			ArrayList<String> followers= new ArrayList<String>();
			ArrayList<Event> pagefeed = pageuser.getFeed();
			
			for(int i=0; i < userbase.size(); i++){				
				User tempuser = userbase.get(i);
				if(tempuser.isFollowing(pageuname)){
					followers.add(tempuser.getUsername());
				}
			}
			
			boolean sameuser = (userindex == pageuindex);//checking if on our userpage
			String followbutton = "";
			String followtype = "";
			if(!sameuser){
				
				boolean isfollowing = false;
				for(int i=0; i< followers.size(); i++ ){
					if(followers.get(i).equals(currusername)){
						isfollowing = true;
					}
				}
				if(isfollowing){
					//style=\"float:right\"
					followbutton = "<button type=\"button\" id = \"followbutton\"> Unfollow" +"</button>";
					followtype = "unfollow";
				}
				else{
					followbutton = "<button type=\"button\" id = \"followbutton\"> Follow" + "</button>";
					followtype = "follow";
				}
			}
			
		%>
		
	</head>
	<body>		
		<h2 id="hiddencurruser" style="display:none"><%=currusername%></h2>
		<h2 id="hiddenpageuser" style="display:none"><%=pageuname%></h2>
		<h2 id="hiddenfollowtype" style="display:none"><%=followtype%></h2>
		
		<div class = "menubar">			
			
			<a href="viewfeed.jsp"><img src = "../img/feed_icon.png" style="float:left; cursor:pointer;" title="View Feed"/> </a>
			<a href= <%=userurl%> ><img class="image-bar" src = <%=currimgurl %> alt = <%=currimgurl %> style="float:left; cursor:pointer" title="View Profile" /></a>
			<form name = "menusearchbar" method="POST" action="SearchResults.jsp">
				<input type = "text" id="searchbar" name = "searchbar" value="Search movies" style="width:150px; height:35px; float:left" onfocus="if (this.value != '') {this.value=''}">
				<input type = "hidden" id="searchtype" name= "searchtype" value="by movie" >
				<img id="toggleicon" src="../img/clapperboard_icon.png" style="float:left; cursor:pointer; margin-left: 0px" title="Toggle search type" onclick="changeIcon()" />
				<input name="submitsearch" type="image" src ="../img/search_icon.png" style="float:left; cursor:pointer" title="Search"/>
			</form>
			<script>
				var image =  document.getElementById("toggleicon");
				var hidden = document.getElementById("searchtype");
				var textbar = document.getElementById("searchbar");
	
	            function changeIcon(){
	                if (image.getAttribute('src') == "../img/clapperboard_icon.png"){	                
	                    image.src = "../img/user_icon.png";
	                    hidden.value = "by user";
	                    textbar.value = "Search users"
	                }
	                else{	                
	                    image.src = "../img/clapperboard_icon.png";
	                    hidden.value = "by movie";
	                    textbar.value="Search movies"
	                }
	                //console.log(hidden.value);
	            }
			</script>
			<div id="toptitle">Cinemate</div>
			<a href="exit.jsp"><img src="../img/exit_icon.jpg" style="float:right; cursor:pointer" title="Exit"></a>
			<a href="logout.jsp"><img src="../img/logout_icon.png" style="float:right; cursor:pointer" title="Log out"></a>			
		</div>
		<div id= "profiletop">
			<img class="image-page" src = <%=imgurl %> alt =<%=imgurl %>  />
			<h1><%=pagefname %> <%=pagelname %> </h1>
			<h2>@<%=pageuname %></h2>
			<%=followbutton%>
		</div>			
		<div id = "profilemiddle">
			<div id = "followers" style="float:left; width:20%">
				<table id = "followerstable">
					<tr><th style="color:#2E5C9D; background-color:#749ACA">Followers</th></tr>
					<% 
					for(int i=0; i< followers.size(); i++){
						out.println("<tr id = \"follower_"+ (String)followers.get(i)+ "\"><td>");
						out.print("<a href = \"" + "userpage.jsp?uname=" + (String)followers.get(i) + "\">") ;
				      	out.println((String)followers.get(i));
				      	out.print("</a>");
				    	out.println("</td></tr>");
					}				
				 	%>
				</table>
			</div>
			<div id = "events" style="float:left; width:60%">
				<table id = "eventstable">
					<tr><th style="color:#2E5C9D; background-color:#749ACA">Events</th></tr>
					<% 
					for(int i=0; i< pagefeed.size(); i++){
						String useraction = pagefeed.get(i).getAction();							
						String actionimg= "<img style=\" width:50px; \" src=\"";
						
						if(useraction.equals("Liked")){
							actionimg+= "../img/liked.png";
						}
						else if(useraction.equals("Disliked")){
							actionimg+= "../img/disliked.png";
						}
						else if(useraction.equals("Watched")){
							actionimg+= "../img/watched.png";
						}
						else if(useraction.equals("Rated")){
							Double rating = Math.ceil( 0.5 * pagefeed.get(i).getRating());
							
							if(rating == 1.0){
								actionimg+= "../img/rating1.png";
							}
							else if(rating == 2.0){
								actionimg+= "../img/rating2.png";
							}
							else if(rating == 3.0){
								actionimg+= "../img/rating3.png";
							}
							else if(rating == 4.0){
								actionimg+= "../img/rating4.png";
							}
							else if(rating == 5.0){
								actionimg+= "../img/rating5.png";
							}
							else if(rating == 0.0){
								actionimg+= "../img/rating0.png";
							}									
						}
						
						actionimg += "\">";		
						out.print("<tr><td>");
						out.print(actionimg);							
						out.print(useraction + ": ");
						out.print(pagefeed.get(i).getMovie());
						out.print("</td></tr>");
					}				
			    	%>
				</table>
			</div>
			<div id = "following" style="float:left; width:20%">
				<table id = "followingtable">
					<tr><th style="color:#2E5C9D; background-color:#749ACA">Following</th></tr>
					<% 
					for(int i=0; i< following.size(); i++){
						out.println("<tr id = \"following_"+ (String)following.get(i)+ "\"><td>");
						out.print("<a href = \"" + "userpage.jsp?uname=" + (String)following.get(i) + "\">") ;
				      	out.println((String)following.get(i));
				      	out.print("</a>");
				      	out.println("</td></tr>");
					}				
			    	%>
				</table>
			</div>
		</div>
		<br />
		<br />
		<br />		
	</body>
</html>