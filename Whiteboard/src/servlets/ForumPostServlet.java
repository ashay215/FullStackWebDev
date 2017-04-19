package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.SQLConnection;
import users.RegisteredUser;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet("/ForumPostServlet")
public class ForumPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("forum post servlet");
		String title = request.getParameter("title"); //get title
    	String body = request.getParameter("body"); //get body
    	int classID = Integer.parseInt(request.getParameter("classID"));
		String parentID = request.getParameter("parentID"); //get title

		SQLConnection sqlCon = new SQLConnection();
		sqlCon.connect();
		
		HttpSession session = request.getSession();
		RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
		String postID = sqlCon.addReply(classID, title, body, curruser.getUserID(), parentID);
		String sql_actionID = sqlCon.getPostID(curruser.getUserID(), Integer.toString(classID), title, body);
		sqlCon.addNotif("Post", sql_actionID, curruser.getUserID(), title, Integer.toString(classID));
		sqlCon.stop();
        response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");  // Set content type of the response
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(postID);
	}
}
