package model;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class SerializableColor implements Serializable {

	private static final long serialVersionUID = 6381890852265521452L;
	private double red;
	private double green;
	private double blue;
	private double alpha;
	
	
	public SerializableColor(Color color) {
		this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
	}
	
	public SerializableColor(double red, double green, double blue, double alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color getJavaFXScenePaintColor() {
		return Color.color(getRed(), getGreen(), getBlue(), getAlpha());
	}
	
	public String getHexStringColor() {
		int red = ((int) Math.round(getRed() * 255)) << 24;
		int green = ((int) Math.round(getGreen() * 255)) << 16;
		int blue = ((int) Math.round(getBlue() * 255)) << 8;
		int alpha = ((int) Math.round(getAlpha() * 255));
		return String.format("#%08X", (red + green + blue + alpha));
	}
	
	public boolean isBackgroundDark() {
		return (1 - (0.299 * getRed() + 0.587 * getGreen() + 0.114 * getBlue()) < 0.5) ? false : true;
	}

	public double getRed() { return red; }

	public void setRed(double red) { this.red = red; }

	public double getGreen() { return green; }

	public void setGreen(double green) { this.green = green; }

	public double getBlue() { return blue; }

	public void setBlue(double blue) { this.blue = blue; }

	public double getAlpha() { return alpha; }

	public void setAlpha(double alpha) { this.alpha = alpha; }
	
}