<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="vipinkum_CS201_assignment2.*, java.util.ArrayList" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cinemate</title>
		<link rel="stylesheet" type="text/css" href="main.css"/>
		
<%
	String errormessage = "";
	String filepath = request.getParameter("filepath");
	
	if (request.getParameter("filepath") != null){

		Database database = new Database(filepath);
		if(!database.isParsed()){
			errormessage = database.getError();
		}
		else{						
			response.sendRedirect("authentication.jsp");
			
			session = (HttpSession) request.getSession();
			session.setAttribute("database", database);
		}
	}
	
%>
	</head>
	<body>	
		<form name="getxml" method="POST">
			<div id ="toptitle">
				Cinemate		
			</div>	
			<div id = "toptext">
				<br>
				Welcome to Cinemate, a Movie Social Media Medium.
				<br>
				Please input a file so that you may begin your experience.	
			</div>	
			<br>
			<div id = "middle">
				<br>
				<div id = "centerbox" style="color:red" style="text-align:center">
					<input type="text" name="filepath">
					<br />
					<br />
				 	<input type="submit" value="Submit">
				 	<br />
				 	<br />
				 	<%=errormessage %>
				</div>
			</div>
			<br />
			<br />
			<br />
		</form>
	</body>
</html>