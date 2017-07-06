package backend;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Vector;

import backend.*;

public class SQLConnection {
	private Connection conn;
	
	
	private final static String checkLogin = "SELECT password FROM Users WHERE username=?";
	private final static String getUsers = "SELECT * FROM Users";
	private final static String getMovies = "SELECT * FROM Movies";
	private final static String addUser = "INSERT INTO Users(username, password, fname, lname, imgurl) VALUES(?, ?, ?, ?, ?)";
	private final static String getFollowing = "SELECT followingID FROM Follows WHERE userfID = ?";
	private final static String getFollowers = "SELECT userfID FROM Follows WHERE followingID = ?";	
	private final static String getUserID = "SELECT userID FROM Users WHERE username = ?";
	private final static String getUsername = "SELECT username FROM Users WHERE userID = ?";
	private final static String getFeed = "SELECT * FROM Events WHERE userID = ?";
	private final static String addEvent = "INSERT INTO Events(userID, movie, rating, action) VALUES(?, ?, ?, ?)";
	private final static String follow = "INSERT INTO Follows(userfID, followingID) VALUES(?, ?)";
	private final static String unfollow = "DELETE FROM Follows WHERE userfID = ? AND followingID = ?";
	private final static String addMovie = "INSERT INTO Movies(title, totalrating, totalvotes) VALUES(?, ?, ?)";
	private final static String updateRating = "UPDATE Movies SET totalrating = totalrating + ?, totalvotes = totalvotes+1 WHERE title = ?";
	
	public SQLConnection() {
		try {
			ArrayList<String> jdbcinfo = getJDBCinfo();
			String ipaddress = jdbcinfo.get(0);
			String db = jdbcinfo.get(1);
			String user = jdbcinfo.get(2);
			String password = jdbcinfo.get(3);
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ipaddress+"/"+db+"?user="+user+"&password="+password+"&useSSL=false");
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		}
	}
	
	public void stop() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getJDBCinfo(){
		ArrayList<String> info = new ArrayList<String>(4);
		for(int i=0; i < 4; i++) info.add("");
		try{
			//String path = "C:\\Users\\Ashay\\workspace\\hw_vipinkum\\vipinkum_CS201_assignment5\\rsrc\\config.txt";
			//"../../../rsrc/config.txt";
			URL path = SQLConnection.class.getResource("config.txt");
			File f = new File(path.getFile());
			BufferedReader br = new BufferedReader(new FileReader(f));
//			FileInputStream fis = (FileInputStream) getClass().getClassLoader().getResourceAsStream("config.txt");		 
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				
				String type = line.substring(0, line.indexOf(':'));
				String content = line.substring(line.indexOf(':')+1);
				//System.out.println(type + ":" +content);
				if(type.equals("ipaddress")){
					info.set(0, content);
				}
				else if(type.equals("db")){
					info.set(1, content);
				}
				else if(type.equals("user")){
					info.set(2, content);
				}
				else if(type.equals("password")){
					info.set(3, content);
				}
			}
		 
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	public String checklogin(String username, String password){
		
		//System.out.println(username + "dfd");
		String result="";
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(checkLogin);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			String pass = "";
			if (rs.next()) { // Loop to get all result sets
				pass = rs.getString("password");
				if(pass.equals(password)) {
					result = "noerror";
				}
				else{
					result="invalidpassword";
				}
			}
			else{
				result="invalidusername";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<User> getUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getUsers);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int userID = rs.getInt(1);
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String imgurl = rs.getString("imgurl");
				users.add(new User(userID, username, password, fname, lname,imgurl));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING users");
		}	
		return users;
	}
	
	public String addUser(String fname, String lname, String username, String password, String imgurl){
		
		ArrayList<User> users = getUsers();
		for(int i=0; i<users.size(); i++){
			if(users.get(i).getUsername().equals(username)){
				return "invalidusername";
			}
		}
		//System.out.println(username + "dfd");
		try {
			PreparedStatement ps = conn.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3,fname);
			ps.setString(4, lname);
			ps.setString(5, imgurl);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "noerror";
	}

	public ArrayList<Integer> getFollowing(int userID){//returns a list of all userIDs that this userID follows
		ArrayList<Integer> users = new ArrayList<Integer>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getFollowing);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int followingID = rs.getInt("followingID");
				users.add(followingID);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING following");
		}	
		return users;
	}
	
	public ArrayList<Integer> getFollowers(int userID){//returns a list of all userIDs that are following this userID is following
		ArrayList<Integer> users = new ArrayList<Integer>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getFollowers);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();			
			while(rs.next()) {
				int userfID = rs.getInt("userfID");
				users.add(userfID);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING followers");
		}	
		return users;
	}
	
	public String getUserID(String username) {
		try {
			PreparedStatement ps = conn.prepareStatement(getUserID);
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
	
	public String getUsername(int userID) {
		try {
			PreparedStatement ps = conn.prepareStatement(getUsername);
			ps.setInt(1, userID);
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
	
	public ArrayList<Event> getFeed(int userID)
	{
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getFeed);
			ps.setInt(1, userID);
			//System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) { 
				Timestamp ts = rs.getTimestamp(6);
				Event newevent = new Event(rs.getString(5), rs.getString(3), rs.getDouble(4),ts);
				events.add(newevent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return events;
	}
	
	public void addEvent(int userID, String movie, double rating, String action ) {
	
		try {			
			PreparedStatement ps = conn.prepareStatement(addEvent);
			
			ps.setString(1, Integer.toString(userID));
//			java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
//			ps.setTimestamp(2, sqlDate);			
			ps.setString(2, movie);
			ps.setDouble(3, rating);
			ps.setString(4, action);
			//System.out.println(ps.toString());
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void follow(int userfID, int followingID) {
		
		try {			
			PreparedStatement ps = conn.prepareStatement(follow);
			
			ps.setInt(1, userfID);
			ps.setInt(2, followingID);		
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void unfollow(int userfID, int followingID) {
		
		try {			
			PreparedStatement ps = conn.prepareStatement(unfollow);
			
			ps.setInt(1, userfID);
			ps.setInt(2, followingID);		
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Movie> getMovies (){
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getMovies);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				int totalrating = rs.getInt("totalrating");
				int totalvotes = rs.getInt("totalvotes");
			
				movies.add(new Movie(title, totalrating, totalvotes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR WHILE FETCHING movies");
		}	
		return movies;
	}
	
	public void addMovie(String title) {
		
		try {			
			PreparedStatement ps = conn.prepareStatement(addMovie);
			
			ps.setString(1, title);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changeRating(String title, int rating) {
		
		try {			
			PreparedStatement ps = conn.prepareStatement(updateRating);
			ps.setString(2, title);
			ps.setInt(1, rating);
			
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
