package server;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1654202436656698275L;
	
	private String user;
    private String content;

	
    public Message() {
    	setUser("username");
        setContent("");
    }
    
    public Message(String user, String content) {
        setUser(user);
        setContent(content);
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}