package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backend.DataStorage;
import backend.Movie;
import backend.SQLConnection;
import backend.User;


/**
 * Servlet implementation class RatingEventServlet
 */
@WebServlet("/RatingEventServlet")
public class RatingEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		SQLConnection conn = new SQLConnection(); 
		int rating =  Integer.parseInt(request.getParameter("rating"));
		//update the rating total and count fields of the movie and add a rated event to logged in user
		conn.changeRating(title, rating);
		//write back the new average rating
		DataStorage ds = new DataStorage();
		ArrayList<Movie> movies = ds.getMoviebase();
		int mindex = -1;
		for(int i=0; i < movies.size(); i++){
			if(movies.get(i).getTitle().equals(title)){
				mindex = i;
			}
		}
		HttpSession session = request.getSession();
		User currUser = (User) session.getAttribute("currUser");
		int curruserid = currUser.getUserID();
		
		conn.addEvent(curruserid, title, rating, "Rated");
		response.getWriter().write(Double.toString(movies.get(mindex).getAverageRating()));
	}

}
