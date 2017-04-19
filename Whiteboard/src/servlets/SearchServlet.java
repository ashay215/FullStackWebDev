package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import course.Course;
import sql.SQLConnection;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String searchField = request.getParameter("searchField"); //get search
		
		SQLConnection sql = new SQLConnection();
		sql.connect();
		Vector<Course> courses = sql.getCourses(searchField);
		sql.stop();
		
        response.setContentType("text/html; charset=UTF-8");
		session.setAttribute("searchResults", courses); //set search results attribute
		response.sendRedirect("/Whiteboard/jsp/courseResults.jsp");  
	}

}
