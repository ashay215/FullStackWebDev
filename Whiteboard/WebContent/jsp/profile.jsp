<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="users.*" %>
<%@ page import="content.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="course.*" %>
<%@ page import="sql.SQLConnection" %>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String pageuser= (String)request.getAttribute("curruser");

	SQLConnection sqlCon = new SQLConnection();
	sqlCon.connect();
	//String pagefullname = sqlCon.getUser
	String username =null;
	//System.out.println("here1");
	RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
	//System.out.println(curruser.getUsername());
	if(curruser != null){
		username = curruser.getUsername();
	}
	else{
		sqlCon.stop();
		response.sendRedirect("error.jsp");
		return;
	}
	
		
	String userID = curruser.getUserID();
	ArrayList<String> uIDs = sqlCon.getFriends(userID);
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Profile Page</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">

		<link href="../css/partials/main.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
		<link href="../css/profile.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<header>
			<form action="/Whiteboard/SearchServlet" method="get">
				<div class="header__wrapper">
					<div class="header__logo-container">
						<a class="logo-container__logo" href="../jsp/homepage.jsp"></a>
					</div>
					<ul class="header__navigation-container">
						<li><input style="float: left" type="text" name="searchField"></li>
						<li><button class="navigation__search" type="submit"><i class="fa fa-search"></i></button></li>
						<li><a href="courses.jsp" class="navigation__courses">Courses</a></li>
						<li><a href="settings.jsp" class="navigation__settings">Settings</a></li>
						<li><a href="profile.jsp" class="navigation__settings">Profile</a></li>
					</ul>
				</div>
			</form>	
		</header>	
		<div>
			<% 
				RegisteredUser User = (RegisteredUser) session.getAttribute("currUser");
				String prof_image = User.getImage();
				String fullname = User.getFullname();
			%>
			<div id="userInfo">
				<img id="profImage" src=<%=prof_image %> alt="some_text" style="width:125px; height:125px;">
				<h1 id="userName"><%=fullname %></h1>
				<h2 id="userCourses">Courses</h2>
				<%
						SQLConnection sql = new SQLConnection();
						sql.connect();
						int UserID = Integer.parseInt(User.getUserID());
						Vector<Course> courses  = sql.getUserCourses(UserID);
						for(int i = 0; i < courses.size(); i++) {
					%>
							<h3 id="courseName" ><a href = "forum.jsp?classID=<%= courses.get(i).getCourseID() %>"><%=courses.get(i).getName() %></a></h3>
					<% 
						}
					%>					
			</div>
			<div id="userActions">Actions:
					<%
						SQLConnection sql2 = new SQLConnection();
						sql2.connect();
						ArrayList<Action> actions  = sql2.getUsersPosts(UserID);
						for(int i = 0; i < actions.size(); i++) {
					%>
							<h3 id="post" >You posted a post with the title <%=actions.get(i).getPost()%> in <%=actions.get(i).getCourse()%>.</h3>
					<% 
						}
					%>				
			
			</div>
			<h3> Friends:</h3>
			<%
				for(int i=0; i < uIDs.size(); i++){
					String curruserid = sqlCon.getUsername(uIDs.get(i));
					String uID = uIDs.get(i);
					System.out.println(uID);
					%>
					<a href="profile.jsp?curruser=<%=uID%>" ><%=curruserid%> </a>
				<% }%>
			
		</div>
	</body>
</html>