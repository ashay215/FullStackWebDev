<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Movie Search Actor</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		
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
			
			Database database = (Database) session.getAttribute("database");
			String currusername = (String) session.getAttribute("username");
			String userurl = "\"userpage.jsp?uname=" + currusername + "\"";
			
			int userindex = -1;
			ArrayList<User> userbase = database.getUserbase();
			for(int i=0; i < userbase.size(); i++){
				if(userbase.get(i).getUsername().equals(currusername)) userindex=i;
			}
			
			User currentuser = userbase.get(userindex);
			String imgurl = currentuser.getImgURL();
			String currfname = currentuser.getFname();
			String currlname = currentuser.getLname();
			
			String placement="";
			String raw = request.getParameter("searchbar");		
			String searchtype = request.getParameter("searchtype");
			String tosearch =raw;
			
			boolean invalidinput = false;
			int colonindex = -1;
			if(searchtype.equals("by movie")){				
				colonindex = raw.indexOf(':');
				if(colonindex != -1){
					if(raw.charAt(colonindex+1) == ' '){
						raw = raw.substring(0, colonindex) + raw.substring(colonindex + 1);
					}
					tosearch = raw.substring(colonindex+1);
				}
				else{
					invalidinput = true;
				}	
			}		
			
			//System.out.println("Tosearch:" + tosearch);
			placement = " for: " + tosearch;
						

			ArrayList<Movie> moviebase = database.getMoviebase();			
			ArrayList<String> foundresults = new ArrayList<String>();
			String urlstart = "";
			
			
			if(searchtype.equals("by movie")){
				if(!invalidinput){
					urlstart = "moviepage.jsp?title=";
					String criteria = raw.substring(0, colonindex);
					//System.out.println("Criteria:" + criteria);
					if(criteria.equals("actor")){
						for(int i=0; i< moviebase.size(); i++){//for all movies
							ArrayList<Actor> tempactors = moviebase.get(i).getActors();
							//System.out.println(moviebase.get(i).getTitle());
							for(int j = 0; j < tempactors.size(); j++){//for all actors in the movie
								//System.out.println(tempactors.get(j));
								String fullname = tempactors.get(j).getFirstname() + " "+ tempactors.get(j).getLastname();
								if(tosearch.equalsIgnoreCase(fullname)){
									foundresults.add(moviebase.get(i).getTitle());
								}
							}
						}
					}
					else if(criteria.equals("genre")){
						for(int i=0; i< moviebase.size(); i++){
							String temptitle = moviebase.get(i).getTitle();
							String tempgenre = moviebase.get(i).getGenre();
							if(tosearch.equalsIgnoreCase(tempgenre)){
								foundresults.add(temptitle);
							}
						}
					}
					else if(criteria.equals("title")){
						for(int i=0; i< moviebase.size(); i++){
							String temptitle = moviebase.get(i).getTitle();
							if(tosearch.equalsIgnoreCase(temptitle)){
								foundresults.add(temptitle);
							}
						}
					}
				}				
			}
			else if(searchtype.equals("by user")){
				urlstart = "userpage.jsp?uname=";
				for(int i=0; i< userbase.size(); i++){//for each user
					String tempusername = userbase.get(i).getUsername();
					String tempfname = userbase.get(i).getFname();
					String templname = userbase.get(i).getLname();
					if(tosearch.equalsIgnoreCase(tempusername)){
						foundresults.add(tempusername);
					}
					else if(tosearch.equalsIgnoreCase(tempfname)){
						foundresults.add(tempusername);
					}
					else if(tosearch.equalsIgnoreCase(templname)){
						foundresults.add(tempusername);
					}
				}
			}
			
			
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
			<a href="exit.jsp"><img src="../img/exit_icon.jpg" style="float:right; cursor:pointer" title="Exit"></a>
			<a href="logout.jsp"><img src="../img/logout_icon.png" style="float:right; cursor:pointer" title="Log out"></a>			
		</div>
		<div id = "middle">
				
			<div id = "centerbox" style="text-align:left">		
			Searching <%=searchtype%> <%=placement %>							
			<br />
			<br />
			<div id="scrollbox">
				<div style="background-color:white; color:black">Search Results</div>
					<table style="width:100%" class="resultstable">
					
						<% 
							if(foundresults.size() == 0 && request.getParameter("searchbar") != null){
								out.print("<tr><td>No results!</td></tr");
							}
							for(int i=0; i < foundresults.size(); i++){
								out.print("\n<tr><td>\n");
								out.print("<a href = \"" + urlstart + foundresults.get(i) + "\">") ;
								out.println(foundresults.get(i));
								out.print("</a>");
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
	</body>
</html>