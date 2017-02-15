package javafiles;
import java.util.ArrayList;

public class User {

	public User (String username, String password, String fname, String lname, ArrayList<Event> feed, ArrayList<String> following){
		this.Username = username;
		this.Password = password;
		this.Fname = fname;
		this.Lname = lname;
		this.Feed = feed;
		this.Following = following;
	}

	private String Username;
	private String Password;
	private String Fname;
	private String Lname;
	private ArrayList<Event> Feed;
	private ArrayList<String> Following;

	public String getUsername() {
		return Username;
	}
	public String getPassword() {
		return Password;
	}
	public String getFname() {
		return Fname;
	}
	public String getLname() {
		return Lname;
	}
	public ArrayList<Event> getFeed() {
		return Feed;
	}
	public ArrayList<String> getFollowing() {
		return Following;
	}

	public boolean isFollowing(String tosearch){//checks if this User object is following the user "tosearch"

		for(int i=0; i< this.Following.size(); i++){
			if(Following.get(i).equals(tosearch)) return true;
		}
		return false;
	}

}
