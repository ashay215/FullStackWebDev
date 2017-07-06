package backend;
import java.util.ArrayList;

public class Movie {
	public Movie(String title, String director, ArrayList<String> writers, int year, String genre, String description, ArrayList<Actor> actors, String poster_url, int rating_total, int rating_count){
		this.title = title;
		this.director = director;
		this.writers = writers;
		this.year = year;
		this.genre = genre;
		this.description = description;
		this.actors = actors;
		this.posterurl = poster_url;
		this.ratingtotal = rating_total;
		this.ratingcount = rating_count;
	}
	
	String title;
	String director;
	ArrayList<String> writers;
	int year;
	String genre;
	String description;
	ArrayList<Actor> actors;
	String posterurl;
	int ratingtotal;
	int ratingcount;
	
	public ArrayList<Actor> getActors() {
		return actors;
	}

	public String getTitle() {
		return title;
	}	
	
	public String getGenre() {
		return genre;
	}

	public String getPosterurl() {
		return posterurl;
	}

	public String getDirector() {
		return director;
	}

	public ArrayList<String> getWriters() {
		return writers;
	}

	public int getYear() {
		return year;
	}

	public String getDescription() {
		return description;
	}

	public int getRatingtotal() {
		return ratingtotal;
	}

	public int getRatingcount() {
		return ratingcount;
	}
	
	
}
