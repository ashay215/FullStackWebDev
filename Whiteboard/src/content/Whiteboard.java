package content;

import java.awt.Image;
import java.sql.Date;
import java.util.ArrayList;

import content.Pin;
public class Whiteboard extends Content{
	private Image image;
	private String name;
	private ArrayList<Pin> pins;
	
	public Whiteboard(String contentID, String userID, String classID, Date time, String name, Image image) {
		super(contentID, userID, classID, time);
		this.setName(name);
		this.setImage(image);
		this.setPins(new ArrayList<Pin>());
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Pin> getPins() {
		return pins;
	}
	public void setPins(ArrayList<Pin> pins) {
		this.pins = pins;
	}
	public void addPin(Pin p)
	{
		pins.add(p);
	}
}
