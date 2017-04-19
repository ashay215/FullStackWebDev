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
 * Servlet implementation class ChangePictureServlet
 */
@WebServlet("/ChangePictureServlet")
public class ChangePictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePictureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
		String username = curruser.getUsername();		
		String newimgURL = request.getParameter("newimgURL");
		//System.out.println(newimgURL + username);
		String errorMsg=null;
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();		
		
		String userID = sqlCon.getUserID(username);
		sqlCon.changePicture(userID, newimgURL);
		
		DataStorage ds = new DataStorage();
		ds.retrieveData();
		RegisteredUser currUser = ds.getUser(userID);			
		
		session.setAttribute("currUser", currUser); // Set session attribute for current user;
		errorMsg = "noerror";
		response.setContentType("text/plain");  // Set content type of the response
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(errorMsg);  // write error as response body.
	    
	    sqlCon.stop();
	}

}
