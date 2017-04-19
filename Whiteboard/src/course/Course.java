package course;

import java.util.Vector;

import content.File;
import content.Message;
import content.Post;
import content.Whiteboard;

public class Course {
	
	private int courseID;
	private String name;
	private String prefix; 
	
	private Vector<String> students;
	private Vector<Post> forum;
	private Vector<Whiteboard> whiteboards;
	private Vector<Message> chat;
	private Vector<File> files;
	
	public Course(int courseID){
		this.courseID = courseID;
	}
	
	public Course(int courseID, String name, String prefix){
		this.courseID = courseID;
		this.name = name;
		this.prefix = prefix;
	}
	
	public int getCourseID() {
		return courseID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
