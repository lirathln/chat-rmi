package model;

import javafx.scene.paint.Color;

public class Message extends Notification {

    private static final long serialVersionUID = 1654202436656698275L;
	private String username;
    private String content;

	
    public Message() {
    	setUsername("");
        setContent("");
        setColor(new SerializableColor(Color.BLACK));
    }
    
    public Message(String username, String content, SerializableColor color) {
        setUsername(username);
        setContent(content);
        setColor(color);
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}