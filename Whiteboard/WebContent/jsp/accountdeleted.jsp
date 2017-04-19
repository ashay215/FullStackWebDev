<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="sql.SQLConnection" %>
<%@ page import="content.Post, users.*" %>
<%@ page import="java.util.List, java.util.Collections" %>

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
	</head>
	<body>
	
	<header>
			<div class="header__wrapper">
				<div class="header__logo-container">
					<a class="logo-container__logo" href="../html/LandingPage.html"></a>
				</div>
			</div>
		</header>	
			
		<section class="tabs">
			<h1>Account Deleted!</h1>
			<br>
			<br>
			<h1 id = "success" style="color:#00DB92"></h1>
			<ul class="tabs__container">
				<li><a href = "../html/LandingPage.html"><button class="tab__button tab__button--selected" >Go to Landing Page</button></a></li>
			</ul>
			<br>
			<br>
			
		</section>
	</body>
</html>