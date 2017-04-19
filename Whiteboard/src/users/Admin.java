package users;

import course.Course;

public class Admin extends Moderator {

	public Admin() {
		// TODO Auto-generated constructor stub
	}
	
	public Moderator createMod(String userID) {
		return new Moderator(); // Skeleton stub
	}
	
	public Course createCourse(int courseID) {
		return new Course(courseID);
	}
	
	public void deleteCourse(String classID) {
		
	}
}
