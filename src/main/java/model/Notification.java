package model;

import java.io.Serializable;
import java.util.Date;

import javafx.scene.paint.Color;

public class Notification implements Serializable {

	private static final long serialVersionUID = 5350513499526902081L;
	private Date date;
	private String content;
	private SerializableColor color;
	
	
	public Notification() {
		setDate(new Date());
		setNotification("");
		setColor(new SerializableColor(Color.GRAY));
	}
	
	public Notification(String notification) {
		setDate(new Date());
		setNotification(notification);
		setColor(new SerializableColor(Color.GRAY));
	}
	
	public Notification(String notification, SerializableColor color) {
		setDate(new Date());
		setNotification(notification);
		setColor(color);
	}
	
	public Date getDate() {	return date; }

	public void setDate(Date date) { this.date = date;	}

	public String getNotification() { return content; }
	
	public void setNotification(String content) { this.content = content; }

	public SerializableColor getColor() { return color;	}

	public void setColor(SerializableColor color) { this.color = color;	}

}