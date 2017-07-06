<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList, java.lang.Math, java.util.Collections" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate View Feed</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		
		<%	
			//System.out.println("viewfeed");
			int usertestindex = -1;
			try{
				User currUser = (User) session.getAttribute("currUser");
				String currusername = currUser.getUsername();			
			}
			catch(NullPointerException e){
				System.out.println("already logged out!");
				response.sendRedirect("alreadyloggedout.jsp");
				return;
			}
			
			User currUser = (User) session.getAttribute("currUser");
			String currusername = currUser.getUsername();
			DataStorage datastorage = new DataStorage();
			int curruserid = currUser.getUserID();
			
			String imgurl = currUser.getImgURL();
			String currfname = currUser.getFname();
			String currlname = currUser.getLname();
		
			
			SQLConnection conn = new SQLConnection();
			
			String userurl = "\"userpage.jsp?uname=" + currusername + "\"";
			
		%>
	</head>
	<body>
		<div class = "menubar">			
			
			<a href="viewfeed.jsp"><img src = "../img/feed_icon.png" style="float:left; cursor:pointer;" title="View Feed"/> </a>
			<a href= <%=userurl%> ><img class="image-bar" src = <%=imgurl %> alt = <%=imgurl %> style="float:left; cursor:pointer" title="View Profile" />
			</a>
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
			<a href="logout.jsp"><img src="../img/exit_icon.jpg" style="float:right; cursor:pointer" title="Exit"></a>
			<a href="logout.jsp"><img src="../img/logout_icon.png" style="float:right; cursor:pointer" title="Log out"></a>			
		</div>
		<div id = "profilemiddle">
			<table cellspacing=70 id = feedtable align="center">
				<tr>
					<th style="color:#2E5C9D; padding:5px" colspan=3>My Feed <hr> </th>
				</tr>
				<%	
					ArrayList<User> userbase = conn.getUsers();
					ArrayList<Integer> following = conn.getFollowing(curruserid);
					for(int i=0; i< following.size(); i++){//printing followed users' feeds
						
						String tempuname = conn.getUsername(following.get(i).intValue());//username of current followed user
						int tempindex = -1;
						for(int j=0; j < userbase.size(); j++){
							if(userbase.get(j).getUsername().equals(tempuname)) tempindex=j;
						}
						
						User tempuser = userbase.get(tempindex);
						String fullname = tempuser.getFname() + " " + tempuser.getLname();
						int tempuserID = tempuser.getUserID();
													
						ArrayList<Event> usertempfeed = conn.getFeed(tempuserID);
						Collections.sort(usertempfeed);
						for(int j=0; j< usertempfeed.size(); j++){//printing followed user's feed
														
							String useraction = usertempfeed.get(j).getAction();
							String usermovie = usertempfeed.get(j).getMovie();
							String posterimg = "<img style=\"height:200px; \" src=\"";
							String tempdate = usertempfeed.get(j).getTime();
													
							/*for(int k=0; k < moviebase.size(); k++){
								Movie tempmovie = moviebase.get(k);
								if(usermovie.equals(tempmovie.getTitle())){
									posterimg += tempmovie.getPosterurl();									
								}
							}*/
							//posterimg +=;
									
							posterimg += "\">";
							
							String profileimg = "<img class=\"image-page\" style=\"height:150px; width:150px; \"" + "src=\"" + tempuser.getImgURL() + "\">" ;
							String actionimg= "<img style=\" width:150px; \" src=\"";
															
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
								Double rating = Math.ceil( 0.5 * usertempfeed.get(j).getRating());
								//System.out.println(rating);
								
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
							
							
							out.print("<tr><td style=\"cursor:pointer\" title=\"" + fullname + "\">");
							out.print("<a href= \"userpage.jsp?uname=" + tempuname + "\">");
							out.print(profileimg);			
							out.print("</a>");
							out.print("</td>");							
							
							out.print("<td style=\"cursor:pointer\" title=\"" + useraction + "\">");
							out.print(actionimg);
							out.print("</td>");
							
							out.print("<td style=\"cursor:pointer\" title=\"" + usermovie + "\">");
							out.print("<a href= \"moviepage.jsp?title=" + usermovie + "\">");
							out.print(posterimg);
							out.print("</a>");
							out.print("</td>");	
							
							out.print("<td>");
							out.print(tempdate);
							out.print("</td>");								
							out.print("</tr>");
						}
					}		
				
			    %>
			</table>
		</div>
		<br />
		<br />
		<br />				
	</body>
</html>