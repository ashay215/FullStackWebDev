package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.SQLConnection;
import users.RegisteredUser;

/**
 * Servlet implementation class CourseAddServlet
 */
@WebServlet("/CourseAddServlet")
public class CourseAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID")); //get courseID
		
		HttpSession session = request.getSession();
	 	RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
	 	int userID = Integer.parseInt(curruser.getUserID());
	 	
	 	SQLConnection sql = new SQLConnection();
	 	sql.connect();
	 	if(sql.isStudent(courseID, userID)) {
		 	sql.stop();
	 		response.setContentType("text/plain");  // Set content type of the response
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("You have already added this course");  // write error as response body.	 
	 	}
	 	else {
		 	sql.addStudent(courseID, userID);
		 	sql.stop();
		 	
		 	response.setContentType("text/plain");  // Set content type of the response
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("No Error");  // write error as response body.	
	 	}
	}

}
