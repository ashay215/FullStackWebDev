package backend;

import java.sql.Date;
import java.sql.Timestamp;

public class Event implements Comparable<Event>{
	public Event(String action, String movie, double rating){
		this.action = action;
		this.movie = movie;
		this.rating = rating;
	}
	
	public Event(String action, String movie, double rating, Timestamp time){
		this.action = action;
		this.movie = movie;
		this.rating = rating;
		this.time = time;
	}
	
	private Timestamp time;
	
	@Override
	public int compareTo(Event c) {
		return c.getTime().compareTo(getTime());
	}
	
	String action;
	String movie;
	double rating;
	
	public double getRating() {
		return rating;
	}
	
	public String getAction() {
		return action;
	}
	public String getMovie() {
		return movie;
	}

	public String getTime() {
		return time.toString();
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}
