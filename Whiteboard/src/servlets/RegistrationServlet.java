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
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname = request.getParameter("fullname"); //get fullname
    	String username = request.getParameter("username"); //get username
    	String password = request.getParameter("password"); //get passwordfield
		String imageurl = request.getParameter("imageurl"); //get image
		String email = request.getParameter("email"); //get email
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();
		
		if(sqlCon.usernameExists(username)) {
			sqlCon.stop();
			response.setContentType("text/plain");  // Set content type of the response
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("Error, this username already exists");  // write error as response body.
		}
		else {
			sqlCon.addUser(username, password, fullname, imageurl, email);
	
			String sql_userID = sqlCon.getUserID(username); // Get SQL's generated userID;
			
			RegisteredUser newUser = new RegisteredUser(password, email, fullname, username, imageurl, sql_userID); // Create new user object
			HttpSession session = request.getSession();
			session.setAttribute("currUser", newUser); // Set session attribute for current user;
									
			sqlCon.stop();
			
			response.setContentType("text/plain");  // Set content type of the response
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("No Error");  // write error as response body.
		}
	}

}
