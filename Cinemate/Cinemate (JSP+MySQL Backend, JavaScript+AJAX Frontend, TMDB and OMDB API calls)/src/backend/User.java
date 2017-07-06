package backend;
import java.util.ArrayList;

public class User {
	
	public User (int UserID, String username, String password, String fname, String lname, String imgurl){
		this.UserID = UserID;
		this.Username = username;
		this.Password = password;
		this.Fname = fname;
		this.Lname = lname;
		this.imgURL = imgurl;
	}
	private int UserID;
	private String Username;
	private String Password;
	private String Fname;
	private String Lname;
	private String imgURL;
	
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
	
	public String getImgURL() {
		return imgURL;
	}
	public int getUserID() {
		return UserID;
	}
	
}
