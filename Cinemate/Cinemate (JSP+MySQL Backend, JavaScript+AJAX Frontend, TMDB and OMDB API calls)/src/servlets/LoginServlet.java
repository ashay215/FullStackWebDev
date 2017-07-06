package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backend.*;

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
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("usernameField"); //get usernameField
		String password = request.getParameter("passwordField"); //get passwordField
		
		//System.out.println(username + password);
		
		SQLConnection sqlCon = new SQLConnection(); 
		String errorMsg="";
		String result = sqlCon.checklogin(username, password);
		//System.out.println(result);
		if(result.equals("noerror")) {
						
			DataStorage ds = new DataStorage();
			User currUser = ds.getUser(username);			
			
			HttpSession session = request.getSession();
			session.setAttribute("currUser", currUser); // Set session attribute for current user;
		}
		else if(result.equals("invalidpassword")) {
			errorMsg = "Error, invalid password!";
		}
		else if(result.equals("invalidusername")) {
			errorMsg = "Error, invalid username!";
		}
		
		response.setContentType("text/plain");  // Set content type of the response
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(errorMsg);  // write error as response body.
	}

}