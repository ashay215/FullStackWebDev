package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import sql.SQLConnection;
import users.RegisteredUser;

/**
 * Servlet implementation class BoardStateServlet
 */

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB

@WebServlet("/BoardStateServlet")
public class BoardStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardStateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String CourseID = (String) session.getAttribute("currclassID");
		String imageData = (String) request.getParameter("image");
		System.out.println(imageData);
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();
		String courseName = sqlCon.getcoursename(CourseID);
		
		// constructs the directory path to store upload file
	    String uploadPath = "/Users/redbanhammer/Repos/Whiteboard/boardStates";
	    
	    System.out.println("Upload path is " + uploadPath);
	 
	    uploadPath += "/" + courseName;
	    
		 // Create course folder if it doesn't exist
	    File real_uploadDir = new File(uploadPath);
	    if (!real_uploadDir.exists()) {
	        real_uploadDir.mkdir();
	    }
	    
	    String base64Image = imageData.split(",")[1];
	    byte[] data = Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.UTF_8));
	    try (OutputStream stream = new FileOutputStream(uploadPath + File.separator + "board.png")) {
	        stream.write(data);
	    }
	    
	    sqlCon.stop();
	}
}
