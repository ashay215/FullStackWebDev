package users;

/* 
 * Base User Class
 */
public abstract class User {
	protected String userID; // userID key
	
	// Getters and Setters
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
