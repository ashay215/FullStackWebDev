package notifications;

import java.sql.Date;
/*
 * Notification Class
 */
public class Notification implements Comparable<Notification> {
	private String NotificationID;
	private String actionType; // Type of action
	private String actionID; // Content key of action
	private String fullname;
	private String username;
	private String contentname;//name of actual content (e.g. filename)
	private String coursename;
	private Date time;
	
	@Override
	public int compareTo(Notification n) {
		return n.getTime().compareTo(getTime());
  }
	
	public Notification(String actionID, String actiontype, String fullname, String username, String coursename, String contentname, Date time ){
		this.actionID = actionID;
		this.actionType = actiontype;
		this.fullname = fullname;
		this.coursename = coursename;
		this.contentname = contentname;
		this.username = username;
		this.time = time;
	}
	
	// Getters and Setters
	public String getActionType() {
		return actionType;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getActionID() {
		return actionID;
	}

	public String getContentname() {
		return contentname;
	}

	public void setContentname(String contentname) {
		this.contentname = contentname;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getNotificationID() {
		return NotificationID;
	}

	public void setNotificationID(String notificationID) {
		NotificationID = notificationID;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getTime() {
		return time;
	}
	
}
