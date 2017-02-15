package javafiles;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class Parser {
	String Filename;
	Document doc;

	public Parser(String filename){
		Filename = filename;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = factory.newDocumentBuilder();
			doc = dbuilder.parse(new File(Filename));
		}
		catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
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
									rating = Double.parseDouble(eventfield.getTextContent());
								}
							}
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
			Users.add(new User(username, password, fname, lname, feed, following ));
		}

		return Users;

	}

	public ArrayList<Movie> getMovies(){
		ArrayList<Movie> Movies = new ArrayList<Movie>();

		NodeList moviesnode = doc.getElementsByTagName("movies");
		Node moviesparent = moviesnode.item(0);//movies parent node

		NodeList movielist = moviesparent.getChildNodes();
		for(int i=0; i < movielist.getLength(); i++){//for every movie

			Node movie = movielist.item(i);
			if(movie.getNodeType() == 3) continue;//ignoring textnodes

			String title = "";
			String director = "";
			int year = 0;
			String genre = "";
			double rating =-1;
			String description = "";
			ArrayList<String> writers = new ArrayList<String>();
			ArrayList<String> actors = new ArrayList<String>();

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
					year = Integer.parseInt(moviefield.getTextContent() );
				}
				else if(moviefield.getNodeName().equals("rating")){
					if(moviefield.getTextContent() != ""){
						rating = Double.parseDouble(moviefield.getTextContent() );
					}
				}
				else if(moviefield.getNodeName().equals("actors")){
					NodeList actorlist = moviefield.getChildNodes();//all actors
					for(int k=0; k < actorlist.getLength(); k++){//for each actor
						Node actor = actorlist.item(k);
						if(actor.getNodeType() == 3) continue; //ignoring textnodes
						actors.add(actor.getTextContent());
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
			Movies.add(new Movie(title, director, writers, year, genre, description, actors, rating));
		}

		return Movies;
	}
}
