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
@WebServlet("/ForumServlet")
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public List<content.Post> getPosts(String classID)
    {
		SQLConnection sqlCon = new SQLConnection();
		sqlCon.connect();
		return sqlCon.getPosts(Integer.parseInt(classID));
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("type").equals("submit"))
		{
			String title = request.getParameter("title"); //get title
	    	String body = request.getParameter("body"); //get body
	    	int classID = Integer.parseInt(request.getParameter("classID"));
			SQLConnection sqlCon = new SQLConnection();
			sqlCon.connect();
			
			HttpSession session = request.getSession();
			RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");
			String postID = sqlCon.addPost(classID, title, body, curruser.getUserID());
			String sql_actionID = sqlCon.getPostID(curruser.getUserID(), Integer.toString(classID), title, body);
			sqlCon.addNotif("Post", sql_actionID, curruser.getUserID(), title, Integer.toString(classID));
			sqlCon.stop();
			response.setContentType("text/plain");  // Set content type of the response
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(postID);
		}
		else if(request.getParameter("type").equals("upvote"))
		{
			String postID = request.getParameter("postID");
			SQLConnection sqlCon = new SQLConnection();
			sqlCon.connect();
			sqlCon.upvote(postID);
			sqlCon.stop();
		}
		else if(request.getParameter("type").equals("downvote"))
		{
			String postID = request.getParameter("postID");
			SQLConnection sqlCon = new SQLConnection();
			sqlCon.connect();
			sqlCon.downvote(postID);
			sqlCon.stop();
		}
		else
		{
			List<content.Post> posts = getPosts(request.getParameter("classID"));
			request.setAttribute("posts", posts);
	        request.getRequestDispatcher("/jsp/forum.jsp?classID="+request.getParameter("classID")).forward(request, response);
		}
	}
}
