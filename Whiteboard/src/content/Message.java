package content;

import java.sql.Date;

public class Message extends Content{
	
	private String messagetext;

	public Message(String contentID, String userID, String classID, Date time, String content){
		super(contentID, userID, classID, time);
		this.messagetext = content;
	}

	public String getContent() {
		return messagetext;
	}
}
