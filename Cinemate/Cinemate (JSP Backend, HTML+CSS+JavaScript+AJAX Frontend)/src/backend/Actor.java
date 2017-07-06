package backend;

public class Actor {
	String firstname;
	String lastname;
	String pictureurl;
	
	Actor(String fname, String lname, String url){
		this.firstname = fname;
		this.lastname = lname;
		this.pictureurl = url;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPictureurl() {
		return pictureurl;
	}
}
