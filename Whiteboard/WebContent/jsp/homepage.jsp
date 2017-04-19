<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="notifications.*, users.*, java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Vector" %>
<%@ page import="course.*" %>

<%	
	SQLConnection sqlCon = new SQLConnection();
	sqlCon.connect();
	ArrayList<Notification> notifs=null;
	String username =null;
	//System.out.println("here1");
 	RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");

 	try{
 		curruser.getUserID();
 		curruser.getUsername();
 	}
 	catch(NullPointerException e){
 		sqlCon.stop();
 		response.sendRedirect("error.jsp");
 		return;
 	}

 		username = curruser.getUsername();
 		notifs = sqlCon.getNotifs(username);
 		Collections.sort(notifs);//to sort by date	 	
		sqlCon.stop();
%>
<html>
	<head>
		<title>Whiteboard</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>
		<script src="../js/logout.js" type="text/javascript"></script>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">

		<link href="../css/forum.css" rel="stylesheet" type="text/css">
		<link href="../css/chat.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
		<link href="../css/homepage.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<header>
				<div class="header__wrapper">
					<div class="header__logo-container">
						<a class="logo-container__logo" href="../jsp/homepage.jsp"></a>
					</div>
					<ul class="header__navigation-container">
						<form style = "display:inline-block;" action="/Whiteboard/SearchServlet" method="get">
						<ul class="header__navigation-container">
						<li><input style="float: left" type="text" name="searchField"></li>
						<li><button class="navigation__search" type="submit"><i class="fa fa-search"></i></button></li>
						</ul>
						</form>
						<li><a href="courses.jsp" class="navigation__courses">Courses</a></li>
						<li><a href="settings.jsp" class="navigation__settings">Settings</a></li>
						<li><a href="profile.jsp" class="navigation__settings">Profile</a></li>
						<li><button onclick="logout()" class="logout"><i class="fa fa-sign-out"  aria-hidden="true"></i></button></li>
					</ul>
				</div>
		</header>	
		
		<div class="main-wrapper">
			<section class="content">
				<h1>Welcome, <%=username %></h1>
			
				<div class="feed-container">	
					<div id="error" class="error-message"></div>
					<% 	if(notifs != null){
							for (Notification n : notifs) { 
								String title = n.getContentname();
								String fullname = n.getFullname();
								String actiontype = n.getActionType();
								String coursename = n.getCoursename();
					%>
								<div class="feed-element">
									<div class="feed-element__username"><%=fullname %></div>

									<%if (actiontype.equals("Post")) {%>
										posted in
										<div class="feed-element__coursename"><%=coursename %></div>
										<div class="feed-element__title"><%=title %></div>
									<% } %>
								</div>
							<% } %>
					<% } %>
				</div>
			</section>

			<section class="sidebar">
				<div class="sidebar__container">
					<h1>My Courses</h1>

					<%
						SQLConnection sql = new SQLConnection();
						sql.connect();
						int UserID = Integer.parseInt(curruser.getUserID());
						Vector<Course> courses  = sql.getUserCourses(UserID);
						for(int i = 0; i < courses.size(); i++) {
					%>
							<h3><a href="forum.jsp?classID=<%= courses.get(i).getCourseID() %>"><%=courses.get(i).getName() %></a></h3>
					<% 
						}
					%>				
				</div>
				<div class="sidebar__container">
					<h1>Moderating</h1>
					<h3>CSCI270</h3>
				</div>
			</section>
		</div>

		<!-- Scripts -->
		<script src="../js/forum.js" type="text/javascript"></script>
	</body>
</html>