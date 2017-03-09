package backend;

import java.util.ArrayList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class Database {
	ArrayList<String> genrebase;
	ArrayList<String> actionbase;
	ArrayList<User> Userbase;
	ArrayList<Movie> Moviebase;
	String ogfilepath = "";
	String error ="";
	boolean parsed=true;
	
	public void addUser(String fname, String lname, String username, String password, String imageurl){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuilder=null;
		try {
			dbuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc=null;
		try {
			doc = dbuilder.parse(new File(ogfilepath));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList usersnodel = doc.getElementsByTagName("users");
		Node usersnode = usersnodel.item(0);//users parent node
		
		Element newUser = doc.createElement("user");
		
		Element newFname = doc.createElement("fname");
		newFname.appendChild(doc.createTextNode(fname));
		newUser.appendChild(newFname);
		
		Element newLname = doc.createElement("lname");
		newLname.appendChild(doc.createTextNode(lname));
		newUser.appendChild(newLname);
		
		Element newImage = doc.createElement("image");
		newImage.appendChild(doc.createTextNode(imageurl));
		newUser.appendChild(newImage);
		
		Element newUname = doc.createElement("username");
		newUname.appendChild(doc.createTextNode(username));
		newUser.appendChild(newUname);
		
		Element newPassword = doc.createElement("password");
		newPassword.appendChild(doc.createTextNode(password));
		newUser.appendChild(newPassword);
			
		Element newFollowing = doc.createElement("following");
		newUser.appendChild(newFollowing);
		Element newFeed = doc.createElement("feed");
		newUser.appendChild(newFeed);
	
		
		usersnode.appendChild(newUser);
		System.out.println("Added node!");
		
		try{			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ogfilepath));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		
		ArrayList<Event> newfeed = new ArrayList<Event>();
		ArrayList<String> newfollowing = new ArrayList<String>();
		
		Userbase.add(new User(username, password, fname, lname, newfeed, newfollowing, imageurl));
		
	}
	
	
	public void addAction(String currusername, String actiontype, String title){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuilder=null;
		try {
			dbuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc=null;
		try {
			doc = dbuilder.parse(new File(ogfilepath));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList usersnodel = doc.getElementsByTagName("users");
		Node usersnode = usersnodel.item(0);//users parent node
		
		NodeList userlist = usersnode.getChildNodes();
		for(int i=0; i < userlist.getLength(); i++){//for every user			
			
			String username="";
			Node feedparent=null;
			
			Node user = userlist.item(i);
			if(user.getNodeType() == 3) continue;//ignoring textnodes				
			
			NodeList userfields = user.getChildNodes();//all the user fields
			for(int j=0; j < userfields.getLength(); j++){//for each field
				Node userfield = userfields.item(j);
				if(userfield.getNodeType() == 3) continue;//ignoring textnodes
				
				if(userfield.getNodeName().equals("username")){
					username = userfield.getTextContent();
					if(!username.equals(currusername)) continue;
				}
				else if(userfield.getNodeName().equals("feed")){
					feedparent = userfield;
					System.out.println("ENTERED");
					
				}
			}
			
			if(username.equals(currusername)){
				Element newEvent = doc.createElement("event");
				
				Element newAction = doc.createElement("action");
				newAction.appendChild(doc.createTextNode(actiontype));
				
				Element newTitle = doc.createElement("title");
				newTitle.appendChild(doc.createTextNode(title));
				
				Element newRating = doc.createElement("rating");
				newRating.appendChild(doc.createTextNode(Double.toString(-1.0)));
				
				
				
				newEvent.appendChild(newAction);
				newEvent.appendChild(newTitle);
				newEvent.appendChild(newRating);
				
				feedparent.appendChild(newEvent);
				System.out.println("Added node!");
			}
		}
		
		try{
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ogfilepath));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
		for(int i=0; i < Userbase.size(); i++){
			User curruser = Userbase.get(i);
			if(curruser.getUsername().equals(currusername)){
				ArrayList<Event> feed = curruser.getFeed();
				feed.add(new Event(actiontype, title, -1));
			}
		}
	}
	
	public void follow(String currusername, String tofollow){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuilder=null;
		try {
			dbuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc=null;
		try {
			doc = dbuilder.parse(new File(ogfilepath));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList usersnodel = doc.getElementsByTagName("users");
		Node usersnode = usersnodel.item(0);//users parent node
		
		NodeList userlist = usersnode.getChildNodes();
		for(int i=0; i < userlist.getLength(); i++){//for every user			
			
			String username="";
			Node followparent=null;
			
			Node user = userlist.item(i);
			if(user.getNodeType() == 3) continue;//ignoring textnodes				
			
			NodeList userfields = user.getChildNodes();//all the user fields
			for(int j=0; j < userfields.getLength(); j++){//for each field
				Node userfield = userfields.item(j);
				if(userfield.getNodeType() == 3) continue;//ignoring textnodes
				
				if(userfield.getNodeName().equals("username")){
					username = userfield.getTextContent();
					if(!username.equals(currusername)) continue;
				}
				else if(userfield.getNodeName().equals("following")){
					followparent = userfield;
				}
			}
			
			if(username.equals(currusername)){
				Element newFollow = doc.createElement("username");
				newFollow.appendChild(doc.createTextNode(tofollow));
				followparent.appendChild(newFollow);
				System.out.println("Added node!");
			}
		}
		
		try{
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ogfilepath));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
		for(int i=0; i < Userbase.size(); i++){
			User curruser = Userbase.get(i);
			if(curruser.getUsername().equals(currusername)){
				ArrayList<String> following = curruser.getFollowing();
				following.add(tofollow);
			}
		}
	}
	
	
	public void unfollow(String currusername, String tounfollow){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuilder=null;
		try {
			dbuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc=null;
		try {
			doc = dbuilder.parse(new File(ogfilepath));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList usersnodel = doc.getElementsByTagName("users");
		Node usersnode = usersnodel.item(0);//users parent node
		
		NodeList userlist = usersnode.getChildNodes();
		for(int i=0; i < userlist.getLength(); i++){//for every user			
			
			String username="";
			NodeList follows=null;
			
			Node user = userlist.item(i);
			if(user.getNodeType() == 3) continue;//ignoring textnodes				
			
			NodeList userfields = user.getChildNodes();//all the user fields
			for(int j=0; j < userfields.getLength(); j++){//for each field
				Node userfield = userfields.item(j);
				if(userfield.getNodeType() == 3) continue;//ignoring textnodes
				
				if(userfield.getNodeName().equals("username")){
					username = userfield.getTextContent();
					if(!username.equals(currusername)) continue;
				}
				else if(userfield.getNodeName().equals("following")){
					follows = userfield.getChildNodes();
				}
			}
			
			if(username.equals(currusername)){
				for(int j=0; j< follows.getLength(); j++){
					Node follownode = follows.item(j);
					if(follownode.getNodeType() == 3) continue;//ignoring textnodes
					System.out.println("Node name is: "+follownode.getNodeName());
					String actualtext = follownode.getTextContent();
					System.out.println(actualtext);
					if(actualtext.equals(tounfollow)){
						follownode.getParentNode().removeChild(follownode);
						System.out.println("Removed node!");
						break;
					}
				}
			}
		}
		
		try{
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ogfilepath));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
		for(int i=0; i < Userbase.size(); i++){
			User curruser = Userbase.get(i);
			if(curruser.getUsername().equals(currusername)){
				ArrayList<String> following = curruser.getFollowing();
				int foundindex = -1;
				for(int j=0; j< following.size(); j++){
					if(following.get(j).equals(tounfollow)){
						foundindex = j;
					}
				}
				following.remove(foundindex);
			}
		}
	}
	
	public String getOgfilepath() {
		return ogfilepath;
	}

	public ArrayList<User> getUserbase() {
		return Userbase;
	}
	
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
		ogfilepath = filepath;
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
