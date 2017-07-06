package backend;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;

public class Movie {
	public Movie(String title, int rating_total, int rating_count){
		this.title = title;
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
	int imdbRating;
	
	public void getinfo(){
		try {
			URL url = new URL("http://www.omdbapi.com/?t=" + title);			
			JSONTokener tokener = new JSONTokener(url.openStream());
			JSONObject json = new JSONObject(tokener);
			director = (String) json.get("Director");
			description = (String) json.get("Plot");
			genre =  (String) json.get("Genre");
			year = Integer.parseInt((String)json.get("Year"));
			actors = new ArrayList<Actor>();
			writers = new ArrayList<String>();
			imdbRating = (int)Double.parseDouble((String)json.get("imdbRating"));
			posterurl = (String) json.get("Poster");
			

			String writerstring = (String) json.get("Writer");
			ArrayList<String> writerstrings = new ArrayList<String> (Arrays.asList(writerstring.split(",")));
			for(int i=0; i < writerstrings.size(); i++){				
				writers.add(writerstrings.get(i));
			}
			
			String actorstring = (String) json.get("Actors");
			ArrayList<String> actorstrings = new ArrayList<String>( Arrays.asList(actorstring.split(",")));
			for(int i=0; i < actorstrings.size(); i++){
				String temp = actorstrings.get(i);
				int index = temp.indexOf(" ");
				String lname = temp.substring(index+1);
				String fname = temp.substring(0, index);
				String posterurl = getAImage(temp);
				actors.add(new Actor(fname, lname, posterurl));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	
	public Double getAverageRating(){
		return (ratingcount == 0 ? 0 : ((double)ratingtotal/ratingcount));
	}
	
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

	public int getImdbRating() {
		return imdbRating;
	}
	
	
	public String getAImage(String actorname){
		
		actorname = actorname.replace(" ", "%20");
		String aimage=null;
		try {
			URL url = new URL("http://api.tmdb.org/3/search/person?api_key=6fcdd94b2c3de6dca333cce3a2789227&query=" + actorname);
			JSONTokener tokener = new JSONTokener(url.openStream());
			JSONObject json = new JSONObject(tokener);
			
			aimage =  "http://image.tmdb.org/t/p/w185/" + json.getJSONArray("results").getJSONObject(0).getString("profile_path").substring(1);
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return aimage;
	}
	
}
