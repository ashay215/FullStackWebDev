<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList, java.lang.Math" %>

<html>
	<head>
		<title>Cinemate View Movie</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		<script src="http://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
		<script src ="../js/movieaction.js"></script>
		<script src = "../js/movie_profile.js"></script>
		
		<%		
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
			
			String currimgurl = currUser.getImgURL();
			String currfname = currUser.getFname();
			String currlname = currUser.getLname();
		
			
			SQLConnection conn = new SQLConnection();
		
			String userurl = "\"userpage.jsp?uname=" + currusername + "\"";
			
			ArrayList<User> userbase = datastorage.getUserbase();
			ArrayList<Movie> moviebase = datastorage.getMoviebase();
			
			String title = request.getParameter("title");
			int movieindex = -1;
			for(int i=0; i < moviebase.size(); i++){
				if(moviebase.get(i).getTitle().equals(title)) movieindex=i;
			}
			
			Movie movie = null;
			
			if(movieindex != -1){
				 movie = moviebase.get(movieindex);
				 movie.getinfo();
			}
			else{
				movie = new Movie(title, 0 , 0);
				movie.getinfo();//makes call to api
				conn.addMovie(title);
			}			
			
			String description = movie.getDescription();
			String director = movie.getDirector();
			ArrayList<String> writers = movie.getWriters();
			int year = movie.getYear();
			String genre = movie.getGenre();
			ArrayList<Actor> actors = movie.getActors();
			String posterurl = movie.getPosterurl();
			
			int ratingtotal = movie.getRatingtotal();
			int ratingcount = movie.getRatingcount();
			Double rating = ( (double) ratingtotal / ratingcount);
			
			
		%>
	</head>
	<body>		
		<h2 id="hiddencurruser" style="display:none"><%=currusername%></h2>
		<h2 id="hiddentitle" style="display:none"><%=title%></h2>
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
		<br />
		<br />
		<div id= "movietop" >
			<div id= "leftside" style="float:left;padding-left:50px">
				<img src = "<%=posterurl %>" style="height:250px cursor:pointer" title= "<%=title %>" />
				<div id= "icons">
					<a href = ""><img id="watchicon" src= "../img/watched.png" style="height:40px; float:left; padding:10px; cursor:pointer" title= "Watched" /></a>
					<a href = ""><img id="likeicon" src= "../img/liked.png" style="height:40px; float:left; padding:10px; cursor:pointer" title="Like" /></a>
					<a href = ""><img id="dislikeicon" src= "../img/disliked.png" style="height:40px; float:left; padding:10px; cursor:pointer" title="Dislike" /></a>
				</div>
			</div>
			<div id="rightside" style="float:left; padding-left:50px;text-align:left">
				<h1><%=title %> (<%=year %>)</h1>
				<h4>Genre: <%=genre %></h4>
				<h4>Director: <%=director %></h4>
				<h4>Actors: 
				<%
					for(int i=0; i < actors.size(); i++){
						out.print(actors.get(i).getFirstname() + " " + actors.get(i).getLastname());
						if(i <actors.size()-1) out.print(", ");
					}
				%></h4>
				<h4>Writers:
				<%
					for(int i=0; i < writers.size(); i++){
						out.print(writers.get(i));
						if(i <writers.size()-1) out.print(", ");
					}
				%></h4>
				<div id = "movie_rating_container">
					<div id = "imdb_movie_rating">
						<h3 style = "display: inline-block"> IMDB:</h3>
	  					<% int iRating = movie.getImdbRating();
						
							for (int i = 0; i< iRating; i++) { %>
								 <span>★</span>
							<% }
							
							for (int j = 0; j < (10 - iRating); j++) { %>
								 <span>☆</span>
							<% } %>
							 
					</div>
					<br>
					<br>
					<h3 style= "display: inline-block"> Cinemate:</h3>
					<div id = "average_movie_rating">
	  					<% Double averageRating = movie.getAverageRating();
						
							for (int i = 0; i< averageRating.intValue(); i++) { %>
								 <span>★</span>
							<% }
							
							for (int j = 0; j < (10 - averageRating); j++) { %>
								 <span>☆</span>
							<% } %>
							 
					</div>
					<div id = "user_movie_rating">
	<!-- 			display stars for the user rating ability
					set the id of each star to be what the rating is if it was cicked
					the rateMovie function takes in the current star element, the movie title, and the strings to be used as the keys for the title and rating parameters in the ajax url -->
					<% for (int k = 0; k < 10; k++) { %>
						<span id = '<%=10-k%>' onclick = "rateMovie(this, '<%= title%>', 'title', 'rating', '<%=curruserid%>')">☆</span>
					<% } %>
					</div>
				</div>
				<br>
				<br>
				<br>
			
				<h4><%=description %></h4>
			</div>
		</div>			
		<div id = "moviemiddle" style="clear:both; background-color:#4973B0; padding:40px">
			<h2><u>Cast</u></h2>
			<table id = actors align=center style="width:30%">
				<%
					for(int i=0; i < actors.size(); i++){
						out.print("<tr><td><img style=\"height:100px;cursor:pointer\" title=\"" +actors.get(i).getFirstname() + " " + actors.get(i).getLastname() + "\"src=\"");
						out.print(actors.get(i).getPictureurl());
						out.print("\"></td>");
						out.print("<td><h3>");
						out.print(actors.get(i).getFirstname() + " " + actors.get(i).getLastname());
						out.print("</h3></td></tr>");
					}
				%>
			</table>
		</div>
		<br />
		<br />
		<br />		
	</body>
</html>