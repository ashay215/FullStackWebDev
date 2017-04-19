<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="notifications.*, users.*, java.util.Collections" %>

<%	
	RegisteredUser curUser = (RegisteredUser) session.getAttribute("currUser");

	if (curUser == null) response.sendRedirect("error.jsp");

	String username = curUser.getUsername();

	String classID = (String)request.getParameter("classID");
	String courseName = "";

	if (classID != null) {
		SQLConnection sqlCon = new SQLConnection();
		sqlCon.connect();
		courseName = sqlCon.getcoursename(classID);
		sqlCon.stop();
	} else {
		response.sendRedirect("error.jsp");
		return;
	}
%>

<html lang="en" dir="ltr">
	<head>
		<title>Whiteboard</title>
		<script src="https://use.typekit.net/ofv3bwh.js"></script>
		<script>try{Typekit.load({ async: true });}catch(e){}</script>
		<script src="../js/logout.js" type="text/javascript"></script>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="/Whiteboard/favicon.png">

		<link href="../css/whiteboard.css" rel="stylesheet" type="text/css">
		<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
	</head>
	<body ontouchstart>
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
		<section class="tabs">
			<div class="tabs__wrapper">
				<h1><%= courseName %></h1>
				<ul class="tabs__container">
					<li><a href = "whiteboard.jsp?classID=<%=classID%>"><button class="tab__button tab__button--selected">Whiteboard</button></a></li>
				<li><a href = "forum.jsp?classID=<%=classID%>"><button class="tab__button">Forum</button></a></li>
				<li><a href = "documents.jsp?classID=<%=classID%>"><button class="tab__button">Docs</button></a></li>
				</ul>
			</div>
		</section>
		<section class="whiteboard">
			<div class="whiteboard__toolbar-container">
				<button class="toolbar__color toolbar__color--black toolbar__color--selected" data-color="black"></button>
				<button class="toolbar__color toolbar__color--red" data-color="#DB0060"></button>
				<button class="toolbar__color toolbar__color--orange" data-color="#F0712B"></button>
				<button class="toolbar__color toolbar__color--green" data-color="#00DB92"></button>
				<button class="toolbar__color toolbar__color--blue" data-color="#0079DB"></button>
				<button class="toolbar__color toolbar__color--white" data-color="#EEE" data-width="50"></button>
			</div>
			<div class="whiteboard__canvas-wrapper">
				<canvas id="whiteboard__canvas" width="2000px" height="2000px"></canvas>
			</div>
		</section>

		<!-- BEGIN CHAT MODULE -->
		<section class="chat">
			<div class="chat__header"><h1>Chat</h1></div>
			<div class="chat__scrollview">
				<template class="chat__message">
					<h2 class="chat__message--sender">Sender</h2>
					<div class="chat__message--message">The quick brown fox jumps over the lazy dog</div>
				</template>
			</div>
			<div class="chat__input-container">
				<input class="chat__message-input" type="text" placeholder="Type a message..." />
				<button class="chat__message-send">Send</button>
			</div>
		</section>
		<!-- END CHAT MODULE -->

		<!-- Scripts -->
		<script type="text/javascript">
			const SESSION_USERNAME = `<%= username %>`
			const SESSION_COURSENAME = `<%= courseName %>`
		</script>

		<script src="../js/jquery-3.2.1.min.js" type="text/javascript"></script>
		<script src="../js/WBSocketMessage.js" type="text/javascript"></script>
		<script src="../js/whiteboard.js" type="text/javascript"></script>
		<script src="../js/chat.js" type="text/javascript"></script>
	</body>
</html>