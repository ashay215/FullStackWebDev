<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="content.File" %>
<%@ page import="notifications.*, users.*" %>
<%@ page import="java.util.ArrayList, java.util.Collections" %>

<%	
	RegisteredUser curUser = (RegisteredUser) session.getAttribute("currUser");
	if (curUser == null){
		response.sendRedirect("error.jsp");
		return;
	}
	String username = curUser.getUsername();
	String userID = curUser.getUserID();
	SQLConnection sqlCon = new SQLConnection();
	sqlCon.connect();
	ArrayList<File> docs=null;
	int ClassID;
	String currclassID = "";
	String courseName = "";
	if (session.getAttribute("currclassID") != null){
		currclassID = (String) session.getAttribute("currclassID");
		docs = sqlCon.getDocs(currclassID);
		Collections.sort(docs);//to sort by date
		courseName = sqlCon.getcoursename(currclassID);
		//System.out.println(request.getParameter("currclassID") + "cid");
	} else {
		sqlCon.stop();
		response.sendRedirect("error.jsp");
	}
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
		<link href="../css/documents.css" rel="stylesheet" type="text/css">
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
		<section class="tabs">
			<div class="tabs__wrapper">
				<h1><%= courseName %></h1>
				<ul class="tabs__container">
					<li><a href = "whiteboard.jsp?classID=<%=currclassID%>"><button class="tab__button">Whiteboard</button></a></li>
					<li><a href = "forum.jsp?classID=<%=currclassID%>"><button class="tab__button">Forum</button></a></li>
					<li><a href = "documents.jsp?classID=<%=currclassID%>"><button class="tab__button tab__button--selected">Docs</button></a></li>
				</ul>
			</div>
		</section>
		<div class="upload-container">
			<h1>Upload Documents</h1>
			<form action="/Whiteboard/DocumentServlet" method="post" enctype="multipart/form-data">
				<input type="file" name="file" size="50" multiple />
				<input type="submit" value="Upload" />
			</form>
		</div>

		<div class="documents-container">
			<%
				SQLConnection sql = new SQLConnection();
				sql.connect();
				
				String courseID = (String) session.getAttribute("currclassID");
				ArrayList<File> Documents = sql.getDocuments(courseID);
				String file = "file://";
				for(int i = 0; i < Documents.size(); i++) {
			%>
			<div class="document">
				<%
					if(sql.isPrivileged(userID, currclassID))
					{
				%>
				<button class="document__remove-button" onclick="deleteDocument('<%=Documents.get(i).getContentID()%>')" >&times;</button>
				<%	} %>
				<a href="/Whiteboard/docUploads/<%= courseName + "/" + Documents.get(i).getFilename() %>" download class="document__title">
					<h1><%=Documents.get(i).getFilename()%></h1>
				</a>
				
				<div class="document__timestamp"><h2><%=Documents.get(i).getTimestamp()%></h2></div>
			</div>
			<%	
				}
			%>
		</div>

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
		<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
		<script src="../js/forum.js" type="text/javascript"></script>
		<script src="../js/WBSocketMessage.js" type="text/javascript"></script>
		<script src="../js/chat.js" type="text/javascript"></script>
		<script src="../js/mod.js" type="text/javascript"></script>
	</body>
</html>