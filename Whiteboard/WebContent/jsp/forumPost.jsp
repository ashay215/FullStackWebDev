 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="content.Post" %>
<%@ page import="notifications.*, users.*" %>
<%@ page import="java.util.List, java.util.Collections" %>
<%
	RegisteredUser curUser = (RegisteredUser) session.getAttribute("currUser");
	if (curUser == null){
		response.sendRedirect("error.jsp");
		return;
	}

	SQLConnection sqlCon = new SQLConnection();
	sqlCon.connect();
	Post post = null;
	String classID = request.getParameter("classID");
	String courseName = sqlCon.getcoursename(classID);
	int score = 0;
	String userID = "";
	String username = "";

	List<content.Post> replies=null;
	if(request.getParameter("postID") != null){
		String postID = request.getParameter("postID");
		post = sqlCon.getPost(postID);
		score = post.getScore();
		String title = post.getTitle();
		String body = post.getBody();
		userID = post.getUserID();
		username = sqlCon.getUsername(userID);
		String postername = sqlCon.getUsername(userID);
		replies = sqlCon.getReplies(postID);
		Collections.sort(replies);//to sort by date
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Whiteboard: <%=title %></title>
	<script src="https://use.typekit.net/ofv3bwh.js"></script>
	<script>try{Typekit.load({ async: true });}catch(e){}</script>
		<script src="../js/logout.js" type="text/javascript"></script>

	<meta name="viewport" content="initial-scale=1.0, user-scalable=yes, width=device-width">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<link rel="shortcut icon" href="/Whiteboard/favicon.png">

	<link href="../css/forum.css" rel="stylesheet" type="text/css">
	<link href="../css/chat.css" rel="stylesheet" type="text/css">
	<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
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
			<ul style = "position: relative: left:2%;" class="tabs__container">
				<li><a href = "whiteboard.jsp?classID=<%=classID%>"><button class="tab__button">Whiteboard</button></a></li>
				<li><a href = "forum.jsp?classID=<%=classID%>"><button class="tab__button tab__button--selected">Forum</button></a></li>
				<li><a href = "documents.jsp?classID=<%=classID%>"><button class="tab__button">Docs</button></a></li>
			</ul>
		</div>
	</section>
	<ul style = "list-style: none;">
		<li class="post-in-list" style="margin-top: 10px; background-color: #00DB92; min-height:200px; margin-bottom: 30px;">
			<div class = "ballot-box">
				<button class = "upvote-in-box" onclick = "upvote('<%=postID%>')">
					<i class="fa fa-arrow-up" aria-hidden="true"></i>
				</button>
				<text id="score<%=postID %>" class = "score-in-box">
					<%=score %>
				</text>	
				<button class = "downvote-in-box">
					<i class="fa fa-arrow-down" aria-hidden="true" onclick = "downvote('<%=postID%>')"></i>
				</button>
			</div>	
			<img></img>
			<a class = "post-title-on-page"><%=title%></a><br>

			<a style = "color: black; margin-left: 20px;">posted by: <%=postername %></a>
			<div class="post-body-box" ><%=body %></div>
		</li>
		<li><br></li>
	<li>
		<input type ="hidden" id = "title" class = "title-input" value = "reply"/>
		<input type="text" id="body" class = "reply-input"  placeholder = "Reply to this Post."/>
		<input type="submit" name="Reply" onclick="reply('<%=postID %>', '<%=username %>')" class = "submit-reply-input"/>
	</li>
	</ul>
	<ul id = "reply-list">
	<% 
	if(replies != null){
		for(content.Post reply : replies){ 
			int replyScore = reply.getScore();
			String replyTitle = reply.getTitle();
			String replyBody = reply.getBody();
			String replyPostID = reply.getContentID();
			String replyUserID = reply.getUserID();
			String replyUsername = sqlCon.getUsername(replyUserID);
	%>
	<li>
		<%if(sqlCon.isModerator(userID) || sqlCon.isAdmin(userID)
				|| sqlCon.isTAForClass(userID, Integer.parseInt(request.getParameter("classID")))
				|| sqlCon.isInstructorForClass(userID, Integer.parseInt(request.getParameter("classID"))))
		{ %>
		<button id = 'delete' onclick = "deletePost('<%=replyPostID %>')" class = 'delete-button' style = "float:right;"><i class="icon-remove-sign">x</i></button><br>
		<%} %>
		<button class = "upvote" onclick = "upvote('<%=replyPostID%>')">
			<i class="fa fa-arrow-up" aria-hidden="true"></i>
		</button>
		<button class = "downvote">
			<i class="fa fa-arrow-down" aria-hidden="true" onclick = "downvote('<%=replyPostID%>')"></i>
		</button>
		<text id = "score<%=replyPostID%>" class = "score"> <%=replyScore %> </text>
		<text class = "post-reply"><%=replyBody %></text><br>
		<text class = 'poster-name'>posted by: <%=replyUsername %></text>
	</li>
	<%}} %>
	</ul>
	
	<!-- Scripts -->
	<script src="../js/forum.js" type="text/javascript"></script>
	<script src="../js/forumPost.js" type="text/javascript"></script>
	<script src="../js/mod.js" type="text/javascript"></script>
</body>
</html>
<%}%>