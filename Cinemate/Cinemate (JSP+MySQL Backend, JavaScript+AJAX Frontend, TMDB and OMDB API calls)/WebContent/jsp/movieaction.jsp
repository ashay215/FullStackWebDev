<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="backend.*, java.util.ArrayList, java.lang.Math" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		//System.out.print("HERE");
		String title = request.getParameter("title");
		String curruser = request.getParameter("curruser");
		String actiontype = request.getParameter("type");
		
		SQLConnection conn = new SQLConnection();
		
		User currUser = (User) session.getAttribute("currUser");
		int curruserid = currUser.getUserID();
		
		conn.addEvent(curruserid, title, -1, actiontype);
		
	%>
</body>
</html>