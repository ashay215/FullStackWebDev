package users;

import java.util.ArrayList;

import content.Content;
import notifications.Notification;

/*
 * Registered User Class
 */
public class RegisteredUser extends User {
	private String password; // User password
	private String email; // User email
	private String fullname; // User fullname
	private String username; // User username
	private String image; // User image;
	private ArrayList<String> friends; // List of user's friends
	private ArrayList<String> classes; // List of user's classes
	private ArrayList<Notification> actions; // List of user's notifications
	
	// Constructor
	public RegisteredUser() {
		friends =  new ArrayList<String>();
		classes = new ArrayList<String>();
		actions = new ArrayList<Notification>();
	}
	
	public RegisteredUser(String password, String email, String fullname, String username, String image, String userID) {
		friends =  new ArrayList<String>();
		classes = new ArrayList<String>();
		actions = new ArrayList<Notification>();
		this.password = password;
		this.email = email;
		this.username = username;
		this.fullname = fullname;
		this.image = image;
		this.userID = userID;
	}
	
	// Getters and Setters
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	// Method to add a new class
	public void addClass(String classID) {
		classes.add(classID);
	}
	
	// Method to create a new post
	public void post(String postID, Content content) {
		
	}
	
	// Method to publish a new file
	public void publish(String classID, String file) {
		
	}
	
	// Method to send a new chat message
	public void chat(String classID, Content content) {
		
	}
	
	// Method to create a new whiteboard drawing
	public void draw(String classID, Content content) {
		
	}
	
	// Method to add a new friend
	public void addFriend(String userID) {
		friends.add(userID);
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
