package backend;

public class Event {
	public Event(String action, String movie, double rating){
		this.action = action;
		this.movie = movie;
		this.rating = rating;
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
}
