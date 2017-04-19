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
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
		String username = curruser.getUsername();
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();
		
		String userID = sqlCon.getUserID(username);
		
		sqlCon.deleteUser(userID);
		session.setAttribute("currUser", null);		
		
		response.setContentType("text/plain");  // Set content type of the response
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("noerror");  // write error as response body.
	    response.sendRedirect("jsp/accountdeleted.jsp");
	    
	    sqlCon.stop();
	}

}
