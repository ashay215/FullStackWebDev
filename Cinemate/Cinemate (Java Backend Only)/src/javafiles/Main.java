package javafiles;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {
	public static void main (String [] args){

		Parser parser = new Parser(args[0]);

		@SuppressWarnings("unused")
		ArrayList<String> genrebase = parser.getGenres();
		@SuppressWarnings("unused")
		ArrayList<String> actionbase = parser.getActions();
		ArrayList<User> Userbase = parser.getUsers();
		ArrayList<Movie> Moviebase = parser.getMovies();

		Scanner sc = new Scanner(System.in);
		login(Userbase, Moviebase, sc);//login calls startmenu(), and so effectively handles all the later commands
		//if exit is requested, login() will end, and thus so will Main

		sc.close();
	}

	private static void login(ArrayList<User> userbase, ArrayList<Movie> moviebase, Scanner scanner){

		boolean running = true;//while the program(function, really) is running
		while(running){

			System.out.print("1. Login \n2. Exit\n");
			try{
				int input = scanner.nextInt();
				if(input != 1 && input != 2){
					System.out.println("You have entered an invalid command, please try again.");
					continue;
				}
				else if(input == 2){
					return;
				}
			}
			catch(InputMismatchException e){
				System.out.println("You have entered an invalid command, please try again.");
				scanner.next();
				continue;
			}

			int userindex = -1;//to keep track of requested/ current user

			int nametries = 3;
			while(nametries != 0){
				System.out.println("Please enter your username \n->");
				String uname = scanner.next();
				userindex = searchuser(userbase,uname);
				if(userindex != -1) { //verifying username
					break;
				}
				else{
					nametries--;
					if (nametries == 0) break;//to avoid saying "0 tries left"
					System.out.println("Invalid username. You have " + nametries + " more chances to enter a valid username.");
				}
			}
			if(nametries == 0){
				userindex = -1; //reset to "null"
				continue;//go back to main menu
			}

			int passwordtries = 3;
			while(passwordtries != 0){
				System.out.println("Please enter your password \n->");
				String passw = scanner.next();
				if(verifypassword(userbase, passw, userindex) ){ //verifying password
					break;
				}
				else{
					passwordtries--;
					if (passwordtries == 0) break;//to avoid saying "0 tries left"
					System.out.println("Incorrect password. You have " + passwordtries + " more chances to enter the correct password.");
				}
			}
			if(passwordtries == 0){
				userindex = -1; //reset to "null"
				continue;//go back to main menu
			}

			int action = startmenu(userbase, moviebase, userindex, scanner);//running actual functions here!
			if(action == 1){//if user wants to log out
				userindex = -1;
			}
			else if(action == 0){ //if user wants to exit
				running = false;
			}
		}
	}

	private static int startmenu(ArrayList<User> userbase, ArrayList<Movie> moviebase, int userindex, Scanner scanner){

		boolean loggedin = true;
		int input=0;
		while(loggedin){
			System.out.println("1. Search Users \n2. Search Movies \n3. View Feed \n4. View Profile \n5. Logout \n6. Exit");
			try{
				input = scanner.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Invalid command.");
				scanner.next();
				continue;
			}

			switch (input){
			case 1 : //usersearch
				usersearch(userbase, scanner);
				break;
			case 2 : //moviesearch
				moviesearch(moviebase, scanner);
				break;
			case 3 : //feed
				printFeed(userbase, userbase.get(userindex), scanner);
				break;
			case 4 : //profile
				printUser(userbase, userbase.get(userindex), scanner);
				break;
			case 5 : //logout
				loggedin = false;
				break;
			case 6 : //exit
				loggedin = false;
				break;
			default : //invalid commands
				System.out.println("You have entered an invalid command, please try again.");
				break;
			}
		}
		if(input == 5) return 1;//if user wants to logout
		else return 0;//if user wants to exit

	}
	private static int searchuser(ArrayList<User> userbase, String s){//returns -1 if not found, otherwise returns index
		for(int i=0; i < userbase.size(); i++){
			if(userbase.get(i).getUsername().equals(s)) return i;
		}
		return -1;
	}

	private static boolean verifypassword(ArrayList<User> userbase, String p, int userindex){//returns true if password matches
		if(userbase.get(userindex).getPassword().equals(p)) return true;
		return false;
	}

	private static void printUser(ArrayList<User> userbase, User u, Scanner scanner){

		String pcopy = u.getPassword();
		int len = pcopy.length();

		String starword = "" + pcopy.charAt(0);
		if(len > 2){//if len <= 2, the entire password is printed without stars
			for(int i=0; i < len -2 ; i++){//adding len-2 stars to account for first and last char
				starword += "*";
			}
		}
		if(len >1) starword += pcopy.charAt(len-1);//adding last char

		ArrayList<String> following = u.getFollowing();
		String printout = (u.getFname() + " " + u.getLname() + "\nUsername: " + u.getUsername() + "\nPassword: " + starword + "\nFollowing:\n");
		for(int i=0; i < following.size(); i++){
			printout += "  " + following.get(i) + "\n";
		}
		printout += "Followers: \n";
		for(int i=0; i < userbase.size(); i++){

			if(userbase.get(i).isFollowing(u.getUsername())){
				printout += "  " + userbase.get(i).getUsername() + "\n";
			}
		}
		System.out.println(printout);

		int input2 = -1;
		System.out.println("To go back to the login menu, please type �0� \n->");
		while(input2 != 0 ){
			try{
				input2 = scanner.nextInt();
				if(input2 != 0){
					System.out.println("Invalid command. To go back to the login menu, please type �0� \n->");
					continue;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Invalid command. To go back to the login menu, please type �0� \n->");
				scanner.next();
				continue;
			}
		}
	}

	private static void printFeed(ArrayList<User> userbase, User u, Scanner scanner){

		String printout = "";
		ArrayList<Event> userfeed = u.getFeed();
		for(int i=0; i< userfeed.size(); i++){//printing user's feed
			printout += u.getUsername() + " " + userfeed.get(i).action +  " the movie " + userfeed.get(i).movie;
			if(userfeed.get(i).action.equals("Rated") && userfeed.get(i).rating != -1 ){
				printout += " (Rating: " + userfeed.get(i).rating + ")";
			}
			printout +=  "\n";
		}

		for(int i=0; i< u.getFollowing().size(); i++){//printing followed users' feeds
			String tempuname = u.getFollowing().get(i);//username of current followed user
			int tempindex = searchuser(userbase, tempuname );
			if(tempindex == -1){
				System.out.println("Error- followed user does not exist!");
				continue;
			}
			ArrayList<Event> usertempfeed = userbase.get(tempindex).getFeed();
			for(int j=0; j< usertempfeed.size(); j++){//printing followed user's feed
				printout += tempuname + " " + usertempfeed.get(j).action +  " the movie " + usertempfeed.get(j).movie;
				if(usertempfeed.get(j).action.equals("Rated") && usertempfeed.get(j).rating != -1){
					printout += " (Rating: " + usertempfeed.get(j).rating + ")";
				}
				printout +=  "\n";
			}
		}

		System.out.print(printout);

		int input2 = -1;
		System.out.println(" \nTo go back to the login menu, please type �0� \n->");
		while(input2 != 0 ){
			try{
				input2 = scanner.nextInt();
				if(input2 != 0){
					System.out.println("Invalid command. To go back to the login menu, please type �0� \n->");
					continue;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Invalid command. To go back to the login menu, please type �0� \n->");
				scanner.next();
				continue;
			}
		}
	}

	private static void usersearch(ArrayList<User> userbase, Scanner scanner){

		boolean searching = true;
		while(searching){
			System.out.print("Please enter the username you are searching for. \n->");
			String tosearch = scanner.next();
			ArrayList<String> foundusers = new ArrayList<String>();

			for(int i=0; i< userbase.size(); i++){
				String tempusername = userbase.get(i).getUsername();
				if(tosearch.equalsIgnoreCase(tempusername)){
					foundusers.add(tempusername);
				}
			}

			String toprint = foundusers.size() + " result(s): \n";
			for(int i=0; i < foundusers.size(); i++){
				toprint += foundusers.get(i) + "\n";
			}
			System.out.print(toprint);

			int input2 = -1;
			while(input2 != 1 && input2 != 2 ){
				System.out.print(" \nPlease choose from the following options: \n1. Back to Login Menu \n2. Search Again \n->");
				try{
					input2 = scanner.nextInt();
					if(input2 != 1 && input2 != 2){
						System.out.println("Invalid command.");
						continue;
					}
					if(input2 == 1){
						searching = false;
					}
				}
				catch(InputMismatchException e){
					System.out.println("Invalid command.");
					scanner.next();
					continue;
				}
			}
		}


	}

	private static void moviesearch(ArrayList<Movie> moviebase, Scanner scanner){

		boolean searching = true;
		while(searching){
			System.out.print(" \n1. Search by Actor \n2. Search by Title \n3. Search by Genre \n4. Back to Login Menu \n->");
			int input = -1;
			try{
				input = scanner.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Invalid command.");
				scanner.next();
				continue;
			}
			if(input < 1 || input > 4){
				System.out.println("Invalid command.");
				continue;
			}

			ArrayList<String> foundmovies = new ArrayList<String>();

			switch(input){

			case 1://actorsearch
				System.out.print("Please enter the name of the actor you wish to search by. \n->");
				scanner.nextLine();
				String tosearch = scanner.nextLine();
				for(int i=0; i< moviebase.size(); i++){//for all movies
					ArrayList<String> tempactors = moviebase.get(i).actors;
					System.out.println(moviebase.get(i).title);
					for(int j = 0; j < tempactors.size(); j++){//for all actors in the movie
						System.out.println(tempactors.get(j));
						if(tosearch.equalsIgnoreCase(tempactors.get(j))){
							foundmovies.add(moviebase.get(i).title);

						}
					}
				}

				break;
			case 2://titlesearch
				System.out.print("Please enter the title you wish to search by. \n->");
				scanner.nextLine();
				String tosearch2 = scanner.nextLine();
				for(int i=0; i< moviebase.size(); i++){
					String temptitle = moviebase.get(i).title;
					if(tosearch2.equalsIgnoreCase(temptitle)){
						System.out.println("found");
						foundmovies.add(temptitle);
					}
				}
				break;
			case 3://genresearch
				System.out.print("Please enter the genre you wish to search by. \n->");
				scanner.nextLine();
				String tosearch3 =scanner.nextLine();
				for(int i=0; i< moviebase.size(); i++){
					String tempgenre = moviebase.get(i).genre;
					if(tosearch3.equalsIgnoreCase(tempgenre)){
						foundmovies.add(moviebase.get(i).title);
					}
				}
				break;
			case 4://login menu
				searching = false;
				break;
			}
			if(input == 4) break;

			String toprint = foundmovies.size() + " result(s): \n";
			for(int i=0; i < foundmovies.size(); i++){
				toprint += foundmovies.get(i) + "\n";
			}
			System.out.print(toprint);

			int input2 = -1;
			while(input2 < 1 || input2 > 2 ){
				System.out.print(" \nPlease choose from the following options: \n1. Back to Login Menu \n2. Back to Search Movies Menu \n->");
				try{
					input2 = scanner.nextInt();
					if(input2 < 1 || input2 > 2 ){
						input2 = -1;
						System.out.println("Invalid command.");
						continue;
					}
					if(input2 == 1){
						searching = false;
					}
				}
				catch(InputMismatchException e){
					System.out.println("Invalid command.");
					scanner.next();
					continue;
				}
			}
		}
	}

}
