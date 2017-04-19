package content;

import java.sql.Date;
import java.util.ArrayList;

public class Post extends Content{
	public String title;
	private String body;
	private ArrayList<Post> replies;
	private String parentID;
	public int score;

	public Post(String contentID, String userID, String classID, String title, String body, Date time) {
		super(contentID, userID, classID, time);
		this.setTitle(title);
		this.setBody(body);
		this.setReplies(new ArrayList<Post>());
		this.parentID = "0";
	}
	
	public Post(String contentID, String userID, String classID, String title, String body, Date time, String parentID) {
		super(contentID, userID, classID, time);
		this.setTitle(title);
		this.setBody(body);
		this.setReplies(new ArrayList<Post>());
		this.parentID = parentID;
	}

	public ArrayList<Post> getReplies() {
		return replies;
	}

	public void setReplies(ArrayList<Post> replies) {
		this.replies = replies;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addReply(Post reply)
	{
		replies.add(reply);
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
