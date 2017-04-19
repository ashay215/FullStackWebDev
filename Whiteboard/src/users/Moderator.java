package users;

public class Moderator extends TeachersAssistant {
	
	public Moderator() {
		// TODO Auto-generated constructor stub
	}
	
	public void suspend(String userID, double time) {
		
	}
	
	public TeachersAssistant createTA(String classID, String userID) {
		return new TeachersAssistant(); // Skeleton stub
	}
	
	public Instructor createInstructor(String classID, String userID) {
		return new Instructor(); // Skeleton stub
	}
}
