package sql;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Vector;

import content.Action;
import content.File;
import content.Post;
import course.Course;
import notifications.Notification;

public class SQLConnection {
	private Connection conn;
	private final static String addPost = "INSERT INTO Whiteboard.Posts(contentID, userID, classID, Title, Body) VALUES(?, ?, ?, ?, ?)";
	private final static String addReply = "INSERT INTO Whiteboard.Posts(contentID, userID, classID, Title, Body, parentID) VALUES(?, ?, ?, ?, ?, ?)";
	private final static String addUser = "INSERT INTO Users(username, pass, fullname, image, email) VALUES(?, ?, ?, ?, ?)";
	private final static String addNotif = "INSERT INTO Notifications(ActionType, ActionID, FullName, ContentName, CourseName, username) VALUES(?, ?, ?, ?, ?, ?)";
	private final static String addDocument = "INSERT INTO Documents(courseID, userID, docPath, docname, time_stamp) VALUES(?, ?, ?, ?, ?)";
	private final static String addStudent = "INSERT INTO Students(courseID, userID) VALUES(?, ?)";

	private final static String getAllCourses = "SELECT * FROM Courses";
	private final static String getUserCourses = "SELECT c.CourseID, c.CourseName, c.CoursePrefix FROM Courses c, Students s WHERE s.userID = ? AND c.CourseID = s.courseID";
	private final static String getStudent = "SELECT * FROM Students WHERE courseID = ? AND userID = ?";
	private final static String getCourse = "SELECT * FROM Courses WHERE CoursePrefix = ?";
	private final static String getPostID= "SELECT * FROM Posts WHERE userID = ? AND classID = ? AND Title=? ";
	private final static String getDocuments = "SELECT * FROM Documents WHERE courseID = ?";
	private final static String getCourseName = "SELECT * FROM Courses WHERE CourseID = ?";
	private final static String getDocs = "SELECT * FROM Documents WHERE CourseID = ?";
	private final static String findUser = "SELECT * FROM Users WHERE username = ?";
	private final static String getUserID = "SELECT userID FROM Users WHERE username = ?";
	private final static String getUsername = "SELECT username FROM Users WHERE userID = ?";
	private final static String getUser = "SELECT * FROM Users WHERE userID = ?";
	private final static String getNotif = "SELECT * FROM Notifications WHERE username = ?";
	private final static String getPosts = "SELECT * FROM Posts WHERE parentID = 0 AND ClassID = ? ORDER BY TimePosted DESC";
	private final static String getReplies = "SELECT * FROM Posts WHERE parentID = ?";
	private final static String upvotePost = "UPDATE Whiteboard.Posts "+
											"SET score = score + 1 WHERE contentID = '";
	private final static String getPost = "SELECT * FROM Posts WHERE contentID = ?";
	private final static String downvotePost = "UPDATE Whiteboard.Posts "+
			"SET score = score - 1 WHERE contentID = '";
	private final static String deletePost = "DELETE FROM Posts WHERE contentID = ?";
	private final static String deleteDoc = "DELETE FROM Documents WHERE documentID = ?";
	private final static String getLoginCredentials = "SELECT pass FROM Users WHERE username = ?";
	private final static String isModerator = "SELECT moderator FROM Users WHERE userID = ?";
	private final static String isAdmin = "SELECT admin FROM Users WHERE userID = ?";
	private final static String isTA = "SELECT ta FROM Whiteboard.Students WHERE userID = ? AND courseID = ?";
	private final static String isInstructor = "SELECT teacher FROM Whiteboard.Students WHERE userID = ? AND courseID = ?";

	private final static String changePassword = "UPDATE Users SET pass = ? WHERE userID = ?";
	private final static String changePicture = "UPDATE Users SET image = ? WHERE userID = ?";
	private final static String deleteUser = "DELETE FROM Users WHERE userID = ?";

	private final static String getUserPosts = "SELECT c.CourseName, p.Title FROM Posts p, Users u, Courses c WHERE u.userID = ? AND c.CourseID = p.classID AND u.userID = p.userID";
	private final static String getFriends = "SELECT * FROM Friends WHERE user1_ID = ? OR user2_ID = ? ";

	public SQLConnection() {
		try {
			new com.mysql.jdbc.Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://______/Whiteboard?user=___&password=____&useSSL=false");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUser(String username, String pass, String fullname, String image, String email) {
		try {
			PreparedStatement ps = conn.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, pass);
			ps.setString(3, fullname);
			ps.setString(4, image);
			ps.setString(5, email);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void addStudent(int courseID, int userID) {
		try {
			PreparedStatement ps = conn.prepareStatement(addStudent);
			ps.setInt(1, courseID);
			ps.setInt(2, userID);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isStudent(int courseID, int userID) {
		try {
			PreparedStatement ps = conn.prepareStatement(getStudent);
			ps.setInt(1, courseID);
			ps.setInt(2, userID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addDocument(int courseID, int userID, String docPath, String docname) {
		try {
			PreparedStatement ps = conn.prepareStatement(addDocument);
			ps.setInt(1, courseID);
			ps.setInt(2, userID);
			ps.setString(3, docPath);
			ps.setString(4, docname);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date = new Date();
			String time_stamp = dateFormat.format(date);
			ps.setString(5, time_stamp);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public String getcoursename(String classID){
		String cname=null;
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getCourseName);
			ps.setString(1, classID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				cname = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING coursename");
		}

		return cname;
	}

	public ArrayList<Action> getUsersPosts(int userID){
		ArrayList<Action> actions = new ArrayList<Action>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getUserPosts);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String course = rs.getString("c.CourseName");
				String post = rs.getString("p.title");
				Action temp = new Action(post, course);
				actions.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING coursename");
		}
		return actions;
	}

	public Vector<Course> getCourses(String CoursePrefix) {
		Vector<Course> courses = new Vector<Course>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getCourse);
			ps.setString(1, CoursePrefix);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int CourseID = rs.getInt("CourseID");
				String CourseName = rs.getString("CourseName");
				Course temp = new Course(CourseID, CourseName, CoursePrefix);
				courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public Vector<Course> getCourseList() {
		Vector<Course> courses = new Vector<Course>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getAllCourses);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int CourseID = rs.getInt("CourseID");
				String CourseName = rs.getString("CourseName");
				String CoursePrefix = rs.getString("CoursePrefix");
				Course temp = new Course(CourseID, CourseName, CoursePrefix);
				courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public Vector<Course> getUserCourses(int userID) {
		Vector<Course> courses = new Vector<Course>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getUserCourses);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int CourseID = rs.getInt("c.CourseID");
				String CourseName = rs.getString("c.CourseName");
				String CoursePrefix = rs.getString("c.CoursePrefix");
				Course temp = new Course(CourseID, CourseName, CoursePrefix);
				courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public void addNotif(String actiontype, String actionID, String userID, String contentname, String classID) {

		String username=null, fullname = null, coursename=null;
		try {
			PreparedStatement ps = conn.prepareStatement(getUser);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				username = rs.getString(2);
				fullname = rs.getString(4);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING userinfo");
		}

		coursename = getcoursename(classID);
		System.out.println(coursename);

		try {
			PreparedStatement ps = conn.prepareStatement(addNotif);

			ps.setString(1, actiontype);
			ps.setString(2, actionID);
			ps.setString(3, fullname);
			ps.setString(4, contentname);
			ps.setString(5, coursename);
			ps.setString(6, username);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public String addReply(int classID, String title, String body, String userID, String parentID) {
		int id = Integer.parseInt(userID);
		System.out.println("SQLCONNECTION FOR REPLY");
		try {
			PreparedStatement ps = conn.prepareStatement(addReply);
			UUID idOne = UUID.randomUUID();
			ps.setString(1, idOne.toString());
			ps.setInt(2, id);
			ps.setInt(3, classID);
			ps.setString(4, title);
			ps.setString(5, body);
			ps.setString(6, parentID);
			ps.executeUpdate();
			return idOne.toString();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addPost(int classID, String title, String body, String userID) {
		int id = Integer.parseInt(userID);
		try {
			PreparedStatement ps = conn.prepareStatement(addPost);
			UUID idOne = UUID.randomUUID();
			ps.setString(1, idOne.toString());
			ps.setInt(2, id);
			ps.setInt(3, classID);
			ps.setString(4, title);
			ps.setString(5, body);
			ps.executeUpdate();
			return idOne.toString();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Post getPost(String postID)
	{
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getPost);
			ps.setString(1, postID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				java.sql.Date sqlDate = new java.sql.Date(rs.getTime(6).getTime());
				content.Post newPost = new content.Post(rs.getString(1), Integer.toString(rs.getInt(2)), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5), sqlDate);
				newPost.setScore(rs.getInt(7));
				return newPost;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public ArrayList<File> getDocs(String ClassID){

		//TODO

		ArrayList<File> docs = new ArrayList<File>();

		try {
			String getdocsforclass = getDocs;//just our own notifications
			PreparedStatement ps;
			ps = conn.prepareStatement(getdocsforclass);
			ps.setString(1, ClassID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				/*java.sql.Date sqlDate = new java.sql.Date(rs.getTime(8).getTime());
				File newFile = new File(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(7), rs.getString(6), rs.getString(5), sqlDate);
				newFile.setNotificationID(rs.getString(1));
				notifs.add(newNotif);*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING docs");
		}

		return docs;
	}

	public ArrayList<Notification> getNotifs(String username){

		ArrayList<Notification> notifs = new ArrayList<Notification>();

		try {
			String getNotifsforUser = getNotif;//just our own notifications
			PreparedStatement ps;
			ps = conn.prepareStatement(getNotifsforUser);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				java.sql.Date sqlDate = new java.sql.Date(rs.getTime(8).getTime());
				Notification newNotif = new Notification(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(7), rs.getString(6), rs.getString(5), sqlDate);
				newNotif.setNotificationID(rs.getString(1));
				notifs.add(newNotif);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING NOTIFS");
		}

		return notifs;
	}

	public List<content.Post> getPosts(int classID)
	{
		try {
			String getPostsForClass = getPosts;
			PreparedStatement ps;
			ps = conn.prepareStatement(getPostsForClass);
			ps.setLong(1, classID);
			ResultSet rs = ps.executeQuery();
			List<content.Post> posts = new ArrayList<content.Post>();
			while(rs.next())
			{
				java.sql.Date sqlDate = new java.sql.Date(rs.getTime(6).getTime());
				content.Post newPost = new content.Post(rs.getString(1), Integer.toString(rs.getInt(2)), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5), sqlDate);
				newPost.setScore(rs.getInt(7));
				posts.add(newPost);
			}
			return posts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING POSTS");
		}
		return null;
	}
	public List <content.Post> getReplies(String postID)
	{
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getReplies);
			ps.setString(1, postID);
			ResultSet rs = ps.executeQuery();
			List<content.Post> posts = new ArrayList<content.Post>();
			while(rs.next())
			{
				java.sql.Date sqlDate = new java.sql.Date(rs.getTime(6).getTime());
				content.Post newPost = new content.Post(rs.getString(1), Integer.toString(rs.getInt(2)), Integer.toString(rs.getInt(3)), rs.getString(4), rs.getString(5), sqlDate);
				newPost.setScore(rs.getInt(7));
				posts.add(newPost);
			}
			return posts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING POSTS");
		}
		return null;
	}
	public void upvote(String postID) {
		try {
			String withID = upvotePost+postID+"'";
			PreparedStatement ps;
			ps = conn.prepareStatement(withID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void downvote(String postID) {
		try {
			String withID = downvotePost+postID+"'";
			System.out.println(withID);
			PreparedStatement ps;
			ps = conn.prepareStatement(withID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserID(String username) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getUserID);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			String userID = "";
			if (rs.next()) { // Loop to get all result sets
				userID = rs.getString("userID");
			}
			return userID;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<File> getDocuments(String courseID) {
		ArrayList<File> Documents = new ArrayList<File>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getDocuments);
			ps.setString(1, courseID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) { // Loop to get all result sets
				String userID = rs.getString("userID");
				String documentID = rs.getString("documentID");
				String docPath = rs.getString("docPath");
				String docname = rs.getString("docname");
				String date = rs.getString("time_stamp");
				File temp = new File(documentID, userID, courseID, date, docname, docPath);
				Documents.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Documents;
	}

	public String getUsername(String userID) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getUsername);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			String username = "";
			if (rs.next()) { // Loop to get all result sets
				username = rs.getString("username");
			}
			return username;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean usernameExists(String username) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(findUser);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) { // Loop to get all result sets
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validCredentials(String username, String password) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getLoginCredentials);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			String pass = "";
			if (rs.next()) { // Loop to get all result sets
				pass = rs.getString("pass");
				if(pass.equals(password)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getPostID(String UserID, String ClassID, String title, String post) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getPostID);
			ps.setString(1, UserID);
			ps.setString(2, ClassID);
			ps.setString(3, title);
			ResultSet rs = ps.executeQuery();
			String postID = "";
			if (rs.next()) { // Loop to get all result sets
				postID = rs.getString(1);
			}
			return postID;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Boolean isPrivileged(String UserID, String classID)
	{
		return isModerator(UserID) || isAdmin(UserID) || isTAForClass(UserID, Integer.parseInt(classID)) ||isInstructorForClass(UserID, Integer.parseInt(classID));
	}
	public Boolean isModerator(String UserID) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(isModerator);
			ps.setString(1, UserID);
			ResultSet rs = ps.executeQuery();
			int s = 0;
			if (rs.next()) {
				s = rs.getInt("moderator");
				System.out.println(getUsername(UserID)+" is a mod:"+s );
				return s == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean isAdmin(String UserID) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(isAdmin);
			ps.setString(1, UserID);
			ResultSet rs = ps.executeQuery();
			int s = 0;
			if (rs.next()) {
				s = rs.getInt("admin");
				return s == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean isTAForClass(String UserID, int classID)
	{
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(isTA);
			ps.setString(1, UserID);
			ps.setInt(2, classID);
			ResultSet rs = ps.executeQuery();
			int s = 0;
			if (rs.next()) {
				s = rs.getInt("ta");
				return s == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean isInstructorForClass(String UserID, int classID)
	{
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(isInstructor);
			ps.setString(1, UserID);
			ps.setInt(2, classID);
			ResultSet rs = ps.executeQuery();
			int s = 0;
			if (rs.next()) {
				s = rs.getInt("teacher");
				return s == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void changePassword(String UserID, String newpass) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(changePassword);
			ps.setString(1, newpass);
			ps.setInt(2, Integer.parseInt(UserID));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void deletePost(String postID) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(deletePost);
			ps.setString(1, postID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String UserID){
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(deleteUser);
			ps.setInt(1, Integer.parseInt(UserID));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changePicture(String UserID, String newimgURL) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(changePicture);
			ps.setString(1, newimgURL);
			ps.setInt(2, Integer.parseInt(UserID));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteDoc(String docID) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(deleteDoc);
			ps.setString(1, docID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

public ArrayList<String> getFriends(String userID) {

		ArrayList<String> frienduserIDs = new ArrayList<String>();

		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getFriends);
			ps.setString(1, userID);
			ps.setString(2, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String frienduname = null;
				String uname1 = rs.getString(2);
				String uname2 = rs.getString(3);
				if(uname1.equals(userID)){
					frienduname = uname2;
				}
				else{
					frienduname = uname1;
				}

				if(frienduname != null) frienduserIDs.add(frienduname);
			}
		}
		catch (SQLException e) {
				e.printStackTrace();
				System.out.println("SQL ERROR WHILE FETCHING POSTS");
				return null;
		}

		return frienduserIDs;
	}



}
