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
		String tofollow = request.getParameter("tofollow");
		String curruser = request.getParameter("curruser");
		String followtype = request.getParameter("type");
		
		SQLConnection conn = new SQLConnection();
		String userfID = conn.getUserID(curruser);
		String followingID = conn.getUserID(tofollow);
		
		
 		if(followtype.equals("unfollow")){
 			conn.unfollow(Integer.parseInt(userfID), Integer.parseInt(followingID));
 		}
 		else{
 			conn.follow(Integer.parseInt(userfID), Integer.parseInt(followingID));
 		}
	%>
</body>
</html>