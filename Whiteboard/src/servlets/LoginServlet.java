package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datastorage.DataStorage;
import sql.SQLConnection;
import users.RegisteredUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("usernameField"); //get usernameField
		String password = request.getParameter("passwordField"); //get passwordField
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();
		String errorMsg;
		if(sqlCon.validCredentials(username, password)) {
			errorMsg = "No Error";
			
			String userID = sqlCon.getUserID(username);
			
			DataStorage ds = new DataStorage();
			ds.retrieveData();
			RegisteredUser currUser = ds.getUser(userID);
			
			
			HttpSession session = request.getSession();
			session.setAttribute("currUser", currUser); // Set session attribute for current user;
		}
		else {
			errorMsg = "Error, invalid login credentials";
		}
		
		response.setContentType("text/plain");  // Set content type of the response
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(errorMsg);  // write error as response body.
	}

}
