package model;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class Notification implements Serializable {

	private static final long serialVersionUID = 5350513499526902081L;
	private String notification;
	private SerializableColor color;
	
	
	public Notification() {
		setNotification("");
		setColor(new SerializableColor(Color.GRAY));
	}
	
	public Notification(String notification, SerializableColor color) {
		setNotification(notification);
		setColor(color);
	}
	
	public String getNotification() { return notification; }
	
	public void setNotification(String notification) { this.notification = notification; }

	public SerializableColor getColor() { return color;	}

	public void setColor(SerializableColor color) { this.color = color;	}

}