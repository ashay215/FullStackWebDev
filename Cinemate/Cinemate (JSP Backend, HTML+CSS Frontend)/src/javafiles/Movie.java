package javafiles;
import java.util.ArrayList;

public class Movie {
	public Movie(String title, String director, ArrayList<String> writers, int year, String genre, String description, ArrayList<String> actors, double rating ){
		this.title = title;
		this.director = director;
		this.writers = writers;
		this.year = year;
		this.genre = genre;
		this.description = description;
		this.actors = actors;
		this.averagerating = rating;
	}

	String title;
	String director;
	ArrayList<String> writers;
	int year;
	String genre;
	String description;
	ArrayList<String> actors;

	public ArrayList<String> getActors() {
		return actors;
	}

	int numratings;
	public String getTitle() {
		return title;
	}

	double totalratings;
	double averagerating;


	public String getGenre() {
		return genre;
	}


}
