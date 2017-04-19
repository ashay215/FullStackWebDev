package content;

public class Action {
	private String post;
	private String course;
	
	public String getPost() {
		return post;
	}
	
	public String getCourse() {
		return course;
	}
	
	public Action(String post, String course) {
		this.post = post;
		this.course = course;
	}
}
