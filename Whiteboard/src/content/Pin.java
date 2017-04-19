package content;

import java.sql.Date;

public class Pin extends Content{
	private int x;
	private int y;
	private String name;
	
	public Pin(String contentID, String userID, String classID, Date time, String name, int x, int y) {
		super(contentID, userID, classID, time);
		this.setX(x);
		this.setY(y);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}
