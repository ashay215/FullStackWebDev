package backend;

import java.util.ArrayList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class DataStorage {
	ArrayList<String> genrebase;
	ArrayList<String> actionbase;
	ArrayList<User> Userbase;
	ArrayList<Movie> Moviebase;
	
	public DataStorage(){
		
		SQLConnection conn = new SQLConnection();
		Userbase = conn.getUsers();
		Moviebase = conn.getMovies();
	}
	
	public User getUser(String username){
		User tempuser = null;
		for(int i=0; i < Userbase.size(); i++){
			if(Userbase.get(i).getUsername().equals(username)){
				tempuser = Userbase.get(i);
				break;
			}
		}
		return tempuser;
	}		
	
	public ArrayList<User> getUserbase() {
		return Userbase;
	}

	public ArrayList<Movie> getMoviebase() {
		return Moviebase;
	}


}
