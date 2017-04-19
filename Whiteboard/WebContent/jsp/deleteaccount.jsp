<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="content.Post, users.*" %>
<%@ page import="java.util.List, java.util.Collections" %>

<%	
	SQLConnection sqlCon = new SQLConnection();
	sqlCon.connect();
	
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
	
%>
<html>
	<head>
		<title>Whiteboard</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>
		<script src="../js/forum.js"></script>
		<link href="../css/forum.css" rel="stylesheet" type="text/css">
		<link href="../css/whiteboard.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="../css/loginpage.css" />		
		<script>
			function verify() {
				document.getElementById("deleteuser").style.backgroundColor = "red";
				document.getElementById("deleteuser").style.border = "3px solid red";
				document.getElementById("deleteuser").innerHTML = "Delete Account";
				document.getElementById("deleteuser").onclick = function(){
					window.location = "../DeleteUserServlet";
				} 
			}
    	</script>
	</head>
	<body>
		<header>
			<form action="../SearchServlet" method="get">
				<div class="header__wrapper">
					<div class="header__logo-container">
						<a class="logo-container__logo" href="../jsp/homepage.jsp"></a>
					</div>
					<ul class="header__navigation-container">
						<li><input style="float: left" type="text" name="searchField"></li>
						<li><button class="navigation__search" type="submit"><i class="fa fa-search"></i></button></li>
						<li><a href="courses.jsp" class="navigation__courses">Courses</a></li>
						<li><a href = "settings.jsp" class="navigation__settings">Settings</a></li>
						<li><a href="profile.jsp" class="navigation__settings">Profile</a></li>
					</ul>
				</div>
			</form>	
		</header>		
		<%if(!username.equals("guest")){%>
		<section class="tabs">
			<h1>Delete Account</h1>
			<h1>Are you sure you want to delete your account? This will be permanent.</h1>
			<br>
			<br>
			<h1 id = "success" style="color:#00DB92"></h1>
			<ul class="tabs__container">
				<li><a><button id= "deleteuser" onclick= verify() class="tab__button tab__button--selected" style="text-align:center">Yes, I'm sure!</button></a></li>
				
			</ul>
			<br>
			<br>
			
		</section>
		<%} %>
	</body>
</html>