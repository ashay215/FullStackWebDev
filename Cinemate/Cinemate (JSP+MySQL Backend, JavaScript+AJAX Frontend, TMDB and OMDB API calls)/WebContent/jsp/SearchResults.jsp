<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList, java.net.URL, org.json.*" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate Search </title>
		<link rel="stylesheet" type="text/css" href="../css/main.css"/>
		
		<%		/*
		
		new user, ajax
rating stars, ajax
average rating

center cinemate lol

test ordering of feed*/
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
			
			String userurl = "\"userpage.jsp?uname=" + currusername + "\"";
			
			int curruserid = currUser.getUserID();
			String imgurl = currUser.getImgURL();
			String currfname = currUser.getFname();
			String currlname = currUser.getLname();
			
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
						
			DataStorage ds = new DataStorage();
			ArrayList<User> userbase = ds.getUserbase();
			
			//ArrayList<Movie> moviebase = database.getMoviebase();			
			ArrayList<String> foundresults = new ArrayList<String>();
			String urlstart = "";
			
			
			if(searchtype.equals("by movie")){
				if(!invalidinput){
					urlstart = "moviepage.jsp?title=";
					String criteria = raw.substring(0, colonindex);
					//System.out.println("Criteria:" + criteria);
					if(criteria.equals("actor")){
						
					}
					else if(criteria.equals("title")){
						try {
							URL url = new URL("http://www.omdbapi.com/?s=" + tosearch);			
							JSONTokener tokener = new JSONTokener(url.openStream());
							JSONObject json = new JSONObject(tokener);
							//System.out.println(json.toString());
							if(!json.isNull("Response")){
								JSONArray ja = json.getJSONArray("Search");
								for(int i=0; i < ja.length(); i++){
									JSONObject objectInArray = ja.getJSONObject(i);
									if(!objectInArray.isNull("Title")){
										foundresults.add((String)objectInArray.get("Title"));
									}
								}
								//String title = json.get("")
							}
							
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}				
			}
			if(searchtype.equals("by user")){
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