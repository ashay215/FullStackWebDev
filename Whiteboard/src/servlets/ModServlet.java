package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sql.SQLConnection;

/**
 * Servlet implementation class ModServlet
 */
@WebServlet("/ModServlet")
public class ModServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ModServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("type").equals("post"))
		{
			String postID = request.getParameter("id");
			SQLConnection sqlCon = new SQLConnection();
			sqlCon.connect();
			sqlCon.deletePost(postID);
		}
		if(request.getParameter("type").equals("doc"))
		{
			String docID = request.getParameter("id");
			SQLConnection sqlCon = new SQLConnection();
			sqlCon.connect();
			sqlCon.deleteDoc(docID);
		}
	}

}
