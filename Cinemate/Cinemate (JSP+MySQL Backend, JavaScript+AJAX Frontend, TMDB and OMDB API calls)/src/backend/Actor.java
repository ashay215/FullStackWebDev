package backend;

public class Actor {
	String firstname;
	String lastname;
	String pictureurl;
	
	Actor(String fname, String lname, String pic){
		this.firstname = fname;
		this.lastname = lname;
		this.pictureurl = pic;
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

	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
}
