package backend;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class Parser {
	String Filename;
	Document doc;
	boolean success;//to indicate whether XML error present to terminate
	String error ="";
	
	public Parser(String filename) throws java.io.FileNotFoundException{
		Filename = filename;
		success = true;
		try {			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = factory.newDocumentBuilder();
			doc = dbuilder.parse(new File(Filename));
		}		
		catch (SAXException | IOException e) {
			//e.printStackTrace();
			//System.out.println(e.getMessage());
			error += "\nProblem with parsing DOM structure!";
			success = false;
		}
		catch (ParserConfigurationException e) {
			//System.out.println(e.getMessage());
			//e.printStackTrace();
			error += "\nProblem with parsing DOM structure!";
			success = false;
		} 		
	}
	public String getError() {
		return error;
	}
	public ArrayList<String> getGenres(){		
		ArrayList<String> Genres = new ArrayList<String>();

		NodeList nListg = doc.getElementsByTagName("genres");
		Node nNode = nListg.item(0);
		//System.out.println("\nElement :" + nNode.getNodeName() );
		
		NodeList nList = nNode.getChildNodes();		
		for(int i=0; i< nList.getLength(); i++){
			Node node = nList.item(i);
			if(node.getNodeType() == 1){
				//System.out.println("\nCurrent Element :" + node.getTextContent() );
				Genres.add(node.getTextContent());
			}            
		}		
//		for(int i=0; i< Genres.size(); i++){
//			System.out.println(Genres.get(i));
//		}
		return Genres;
		
	}
	public ArrayList<String> getActions(){		
		ArrayList<String> Actions = new ArrayList<String>();

		NodeList nListg = doc.getElementsByTagName("actions");
		Node nNode = nListg.item(0);
		
		NodeList nList = nNode.getChildNodes();		
		for(int i=0; i< nList.getLength(); i++){
			Node node = nList.item(i);
			if(node.getNodeType() == 1){
				//System.out.println("\nCurrent Element :" + node.getTextContent() );
				Actions.add(node.getTextContent());
			}            
		}		
//		for(int i=0; i< Actions.size(); i++){
//			System.out.println(Actions.get(i));
//		}
		return Actions;
		
	}
	
	public ArrayList<User> getUsers(){
		
		ArrayList<User> Users = new ArrayList<User>();
		
		NodeList usersnodel = doc.getElementsByTagName("users");
		Node usersnode = usersnodel.item(0);//users parent node
		
		NodeList userlist = usersnode.getChildNodes();
		for(int i=0; i < userlist.getLength(); i++){//for every user			
			
			Node user = userlist.item(i);
			if(user.getNodeType() == 3) continue;//ignoring textnodes
			
			String username= "";
			String password = "";
			String fname = "";
			String lname = "";
			String imgurl = "";
			ArrayList<String> following = new ArrayList<String>();
			ArrayList<Event> feed = new ArrayList<Event>();
			
			NodeList userfields = user.getChildNodes();//all the user fields
			for(int j=0; j < userfields.getLength(); j++){//for each field
				Node userfield = userfields.item(j);
				if(userfield.getNodeType() == 3) continue;//ignoring textnodes
				
				if(userfield.getNodeName().equals("username")){
					username = userfield.getTextContent();
				}
				else if(userfield.getNodeName().equals("password")){
					password = userfield.getTextContent();
				}
				else if(userfield.getNodeName().equals("fname")){
					fname = userfield.getTextContent();
				}
				else if(userfield.getNodeName().equals("lname")){
					lname = userfield.getTextContent();
				}
				else if(userfield.getNodeName().equals("image")){
					imgurl = userfield.getTextContent();
				}
				else if(userfield.getNodeName().equals("following")){
					NodeList follows = userfield.getChildNodes();
					for(int k=0; k < follows.getLength(); k++){
						if(follows.item(k).getNodeType() == 1  ){
							following.add(follows.item(k).getTextContent());
						}						
					}
				}
				else if(userfield.getNodeName().equals("feed")){
					NodeList events = userfield.getChildNodes();
					for(int k=0; k < events.getLength(); k++){//for each event
						Node event = events.item(k);
						if(event.getNodeType() == 3) continue; //ignoring textnodes
						
						String action = "";
						String movie = "";
						double rating = -1;
												
						NodeList eventfields = event.getChildNodes();//all the event fields
						for(int l =0; l< eventfields.getLength(); l++){//for each event field
							Node eventfield = eventfields.item(l);
							if(eventfield.getNodeType() == 3) continue; //ignoring textnodes
							
							if(eventfield.getNodeName().equals("action")){
								action = eventfield.getTextContent();
							}
							else if(eventfield.getNodeName().equals("movie")){
								movie = eventfield.getTextContent();
							}
							else if(eventfield.getNodeName().equals("rating")){
								if(eventfield.getTextContent() != ""){
									try{
										rating = Double.parseDouble(eventfield.getTextContent());
									}
									catch(NumberFormatException e){
										System.out.println("String Value in Event Rating for : " + username + "s event: " + action + " " + movie +" !" );
										success = false;
										error += "\nUser Event Rating field in XML file cannot be a string!";
									}
								}								
							}
						}
						if(action.equals("Rated") && rating == -1){								
								System.out.println("RatingAction+NoRatingERROR with " + movie);
								error += "\nUser's event has the action of 'rating' but no rating field!";
								success = false;
						}
						if(!action.equals("Rated") && rating != -1){
							System.out.println("NoRatingAction+RatingERROR with " + movie);
							error += "\nUser's event does not have the action of 'rating' but has a rating listed!";
							success = false;
						}
						feed.add(new Event(action, movie, rating));
					}
				}
			}
//			System.out.println(username + password + fname + lname);
//			for(int j =0; j < following.size(); j++){
//				System.out.println(following.get(j) + " ");
//			}
//			for(int k=0; k<feed.size(); k++){
//				System.out.println(feed.get(k).action + " " + feed.get(k).movie + " " + feed.get(k).rating );
//			}
//			System.out.print("\n");
			Users.add(new User(username, password, fname, lname, feed, following, imgurl ));
		}		
		
		for(int i=0; i < Users.size(); i++){//for each user
			User curruser = Users.get(i);
			ArrayList<String> currfollowing = curruser.getFollowing();//get following of curruser
			for(int j=0; j < currfollowing.size(); j++){//for each followed user
				String followeduser = currfollowing.get(j);
				boolean exists=false;
				for(int k=0; k< Users.size(); k++){//check if followeduser exists
					String tempuname = Users.get(k).getUsername();
					if(tempuname.equals(followeduser)) exists=true;
				}
				if(!exists){//if username not found and flag not triggered
					success = false;
					System.out.println("Nonexistent followed user: " + followeduser);
					error+= "\nNonexistent followed user!";
				}
			}
		}
		
		return Users;
		
	}
	
	public ArrayList<Movie> getMovies(){
		ArrayList<Movie> Movies = new ArrayList<Movie>();
		
		NodeList moviesnode = doc.getElementsByTagName("movies");
		Node moviesparent = moviesnode.item(0);//movies parent node
		NodeList movielist=null;
		try{
			movielist = moviesparent.getChildNodes();
		}
		catch(java.lang.NullPointerException e){
			System.out.println("No Movies found!");
			error += "\nNo Movies found in XML file!";
			success= false;
			return Movies;
		}
		
		for(int i=0; i < movielist.getLength(); i++){//for every movie		
			
			Node movie = movielist.item(i);
			if(movie.getNodeType() == 3) continue;//ignoring textnodes
			
			String title = "";
			String director = "";
			int year = 0;
			String genre = "";
			int ratingtotal =0;
			int ratingcount = 0;
			String description = "";
			ArrayList<String> writers = new ArrayList<String>();
			ArrayList<Actor> actors = new ArrayList<Actor>();
			String posterurl = "";
			
			NodeList moviefields = movie.getChildNodes();//all the movie fields
			for(int j=0; j < moviefields.getLength(); j++){//for each field
				
				Node moviefield = moviefields.item(j);
				if(moviefield.getNodeType() == 3) continue;//ignoring textnodes
				
				if(moviefield.getNodeName().equals("title")){
					title = moviefield.getTextContent();
				}
				else if(moviefield.getNodeName().equals("director")){
					director = moviefield.getTextContent();
				}
				else if(moviefield.getNodeName().equals("genre")){
					genre = moviefield.getTextContent();
				}
				else if(moviefield.getNodeName().equals("description")){
					description = moviefield.getTextContent();
				}
				else if(moviefield.getNodeName().equals("year")){
					try{
						year = Integer.parseInt(moviefield.getTextContent() );
					}					
					catch(NumberFormatException e){
						System.out.println("String Value in Movie Year for : " + title);
						error += "\n Movie's Year field cannot be a string!";
						success = false;
					}
				}
				else if(moviefield.getNodeName().equals("image")){
					posterurl = moviefield.getTextContent();
				}
				else if(moviefield.getNodeName().equals("rating-total")){
					String rawfield = moviefield.getTextContent();
					if(rawfield != ""){
						ratingtotal = Integer.parseInt(rawfield);	
					}						
				}
				else if(moviefield.getNodeName().equals("rating-count")){	
					String rawfield = moviefield.getTextContent();
					if(rawfield != ""){
						ratingcount = Integer.parseInt(rawfield);	
					}
								
			}
				else if(moviefield.getNodeName().equals("actors")){
					NodeList actorlist = moviefield.getChildNodes();//all actors
					for(int k=0; k < actorlist.getLength(); k++){//for each actor								
						
						Node actor = actorlist.item(k);
						if(actor.getNodeType() == 3) continue; //ignoring textnodes
						NodeList actorfields = actor.getChildNodes();//all actorfields
						String firstname = "";
						String lastname = "";
						String imgurl = "";
						
						for(int l=0; l < actorfields.getLength(); l++ ){
							Node actorfield = actorfields.item(l);
							
							if(actorfield.getNodeName().equals("fname")){
								firstname = actorfield.getTextContent();
								
							}
							else if(actorfield.getNodeName().equals("lname")){
								lastname = actorfield.getTextContent();
							}
							else if(actorfield.getNodeName().equals("image")){
								imgurl = actorfield.getTextContent();
							}							
						}						
						actors.add(new Actor(firstname, lastname, imgurl));										
					}
				}
				else if(moviefield.getNodeName().equals("writers")){
					NodeList writerlist = moviefield.getChildNodes();//all writers
					for(int k=0; k < writerlist.getLength(); k++){//for each writer
						Node writer = writerlist.item(k);
						if(writer.getNodeType() == 3) continue; //ignoring textnodes
						writers.add(writer.getTextContent());										
					}
				}				
			}	
//			System.out.println(title + director + genre + "\n" + description + "\n" + year);
//			for(int j =0; j < writers.size(); j++){
//				System.out.println(writers.get(j) + " ");
//			}
//			for(int j =0; j < actors.size(); j++){
//				System.out.println(actors.get(j) + " ");
//			}
//			System.out.print(rating + "\n");
			Movies.add(new Movie(title, director, writers, year, genre, description, actors, posterurl, ratingtotal, ratingcount));
		}	
		if(Movies.size() == 0){
			System.out.println("No Movies found-size0!!!");
			error += "\nNo Movies found in XML file!";
			success= false;
		}
		return Movies;
	}
	public boolean isSuccess() {
		return success;
	}
	
	public void checkmoviegenres( ArrayList<String> genrebase, ArrayList<Movie> moviebase){
		
		for( int i=0; i < moviebase.size(); i++){//for each movie
			boolean allgood = false;
			Movie currmovie = moviebase.get(i);
			String currgenre = currmovie.getGenre();
			
			for(int j=0; j <genrebase.size(); j++){
				//System.out.println( currgenre + " vs " + genrebase.get(j) );
				if(genrebase.get(j).equals(currgenre)) allgood = true;
			}
			if( allgood == false ){//if flag not triggered after checking all genres
				success = false;	
				error += "\nMovie's Genre field contains an invalid genre!";
				System.out.println("InvalidGenreMovie: " + currgenre);
			}
		}
	}
	
	public void checkfeedactions(ArrayList<String> actionbase, ArrayList<User> userbase){
		
		for(int i=0; i<userbase.size(); i++){//for all users
			User curruser = userbase.get(i);
			ArrayList<Event> currfeed = curruser.getFeed();
			
			for(int j=0; j< currfeed.size(); j++){//for each event
				boolean verified = false;
				Event currevent = currfeed.get(j);
				String curraction = currevent.getAction();
				
				for(int k=0; k < actionbase.size(); k++){//checking if in actionbase
					if(actionbase.get(k).equals(curraction)) verified= true;
				}
				
				if( verified == false ){//if flag not triggered after checking all genres
					success = false;
					error += "\nMovie's Action field contains an invalid action!";
					System.out.println("NonExistentActionEvent: " + curraction + " from " + currevent.getMovie());
				}
			}			
		}
	}
	
}
