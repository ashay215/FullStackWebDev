package javafiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Database {
	ArrayList<String> genrebase;
	ArrayList<String> actionbase;
	ArrayList<User> Userbase;
	ArrayList<Movie> Moviebase;

	public ArrayList<User> getUserbase() {
		return Userbase;
	}

	boolean parsed=true;
	String error ="";

	public String getError() {
		return error;
	}

	public boolean isParsed() {
		return parsed;
	}

	public ArrayList<Movie> getMoviebase() {
		return Moviebase;
	}

	public Database(String filepath){
		Parser parser;
		try{
			parser = new Parser(filepath);
			genrebase = parser.getGenres();
			actionbase = parser.getActions();
			Userbase = parser.getUsers();
			Moviebase = parser.getMovies();

		}
		catch(NullPointerException | FileNotFoundException e){
			//e.printStackTrace();
			//System.out.println(e.getMessage());
			System.out.println("\n XML file could not be found!");
			parsed=false;
			error+= "XML file could not be found!";
			return;
		}
		parser.checkmoviegenres(genrebase, Moviebase);
		parser.checkfeedactions(actionbase, Userbase);

		if (!parser.isSuccess()){
			parsed=false;
			error += parser.getError();
		}
	}


}
