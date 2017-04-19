package content;

public class File extends Content {
	
	private String filepath;
	private String filename;
	private String timestamp;

	public File(String documentID, String userID, String classID, String timestamp, String filename, String filepath){
		super(documentID, userID, classID);
		this.filename = filename;
		this.filepath = filepath;
		this.timestamp = timestamp;
	}
	
	public String getFilepath() {
		return filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
