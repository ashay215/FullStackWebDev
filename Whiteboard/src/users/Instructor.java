package users;

import java.util.Vector;

public class Instructor extends TeachersAssistant {
	
	private Vector<String> instructorClasses;
	
	public Instructor() {
		instructorClasses = new Vector<String>();
	}
	
	public TeachersAssistant createTA(String classID, String userID) {
		return new TeachersAssistant(); // Skeleton stub
	}
}
