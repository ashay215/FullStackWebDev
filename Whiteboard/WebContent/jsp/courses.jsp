<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="course.*, users.*" %>
<%@ page import="sql.SQLConnection" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
	
		try{
			curruser.getUserID();
			curruser.getUsername();
		}
		catch(NullPointerException e){
			response.sendRedirect("error.jsp");
			return;
		}
%>
<html>
	<head>
		<title>Courses</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>
	
		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">
	
		<link href="../css/forum.css" rel="stylesheet" type="text/css">
		<link href="../css/chat.css" rel="stylesheet" type="text/css">
		<link href="../css/courses.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
		<script>
			function addCourse(courseID) {
		   			var url = "/Whiteboard/CourseAddServlet?courseID="+courseID;
		   			// create AJAX request
		    		var req = new XMLHttpRequest();
		    		req.open("GET", url, true);
		    		req.onreadystatechange = function () {
		    			if(req.readyState == 4 && req.status == 200) { 
		    				if(req.responseText === "You have already added this course") { //if there is no error
		    					alert(req.responseText);
		    				}
		    			}
		    		}
		    		req.send(null);
		    	}
		</script>
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
		<div id="courseheader">Course Catalog</div>
		<div id="note">*Click on a course name to add it*</div>
		<table id="courselist">
			<%
				SQLConnection sql = new SQLConnection();
				sql.connect();
				Vector<Course> courses = sql.getCourseList();
				for(int i = 0; i < courses.size(); i++) {
			%>
				<tr><td><button id="add" onclick="addCourse(<%=courses.get(i).getCourseID() %>)"><%=courses.get(i).getName() %></button></td></tr>
			<%
				}
			%>
		</table>
	</body>
</html>