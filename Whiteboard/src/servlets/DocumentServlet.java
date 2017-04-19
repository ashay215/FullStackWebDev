package servlets;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

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
 * Servlet implementation class DocumentServlet
 */
@WebServlet("/DocumentServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String CourseID = (String) session.getAttribute("currclassID");
		
		SQLConnection sqlCon = new SQLConnection(); 
		sqlCon.connect();
		String courseName = sqlCon.getcoursename(CourseID);
		
		// constructs the directory path to store upload file
	    String uploadPath = "/Users/redbanhammer/Repos/Whiteboard/docUploads"; //getServletContext().getRealPath("/") + "docUploads";
	    
	    System.out.println("Upload path is " + uploadPath);

	    // Create docUploads folder if it doesn't exist
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }
	 
	    uploadPath += "/" + courseName;
	    
		 // Create course folder if it doesn't exist
	    File real_uploadDir = new File(uploadPath);
	    if (!real_uploadDir.exists()) {
	        real_uploadDir.mkdir();
	    }
	    
	    RegisteredUser curruser = (RegisteredUser) session.getAttribute("currUser");

	    for (Part part : request.getParts()) { // loop through parts and get filename
	        String fileName = getFileName(part);
	        fileName = new File(fileName).getName();
	        
	        String documentPath = uploadPath + File.separator + fileName;
	        new DocumentWriteThread(part, documentPath);
	        
	        int courseID = Integer.parseInt(CourseID);
		    int userID = Integer.parseInt(curruser.getUserID());
		    sqlCon.addDocument(courseID, userID, documentPath, fileName);
	    }
	    
	    sqlCon.stop();
	    
		response.sendRedirect("jsp/documents.jsp"); //redirect to documents page
	}
	
	private String getFileName(Part part) {
	    String contentDisp = part.getHeader("content-disposition");
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	        if (s.trim().startsWith("filename")) {
	            return s.substring(s.indexOf("=") + 2, s.length()-1);
	        }
	    }
	    return "";
	}
}

class DocumentWriteThread extends Thread {
	
	private Part part;
	private String filePath;
	
	public DocumentWriteThread(Part part, String filePath) {
		this.part = part;
		this.filePath = filePath;
		this.start();
	}
	
	public void run() {
		try {
			part.write(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
