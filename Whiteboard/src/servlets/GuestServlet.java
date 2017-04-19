package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datastorage.DataStorage;
import users.RegisteredUser;
import users.User;

/**
 * Servlet implementation class GuestServlet
 */
@WebServlet("/GuestServlet")
public class GuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		DataStorage ds = new DataStorage();
		ds.retrieveData();
		
		RegisteredUser guestUser = ds.getUser(Integer.toString(0));
		
		HttpSession session = request.getSession();		
		session.setAttribute("currUser", guestUser); // Set session attribute for guest user;
		response.sendRedirect("jsp/homepage.jsp");
	}

}
