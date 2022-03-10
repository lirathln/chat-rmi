package model;

public class Clock {
	
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	
	public Clock() {
		this.day = 0;
		this.hour = 0;
		this.minute = 0;
		this.second = 0;
	}
	
	public String timerString() {
		String timer = getDay() == 0 ? "" : Integer.toString(getDay()) + " : ";
		timer += formatTimer(getHour());
		timer += " : ";
		timer += formatTimer(getMinute());
		timer += " : ";
		timer += formatTimer(getSecond());
		return timer;
	}
	
	private String formatTimer(int number) {
		if (number == 0)
			return "00";
		if (number < 10)
			return "0" + Integer.toString(number);
		return Integer.toString(number);
	}
	
	public void addSecond() {
		if (getSecond() < 59)
			setSecond(getSecond() + 1);
		else {
			setSecond(0);
			addMinute();
		}
	}
	
	private void addMinute() {
		if (getMinute() < 59)
			setMinute(getMinute() + 1);
		else {
			setMinute(0);
			addHour();
		}
	}
	
	private void addHour() {
		if (getHour() < 23)
			setHour(getHour() + 1);
		else {
			setHour(0);
			addDay();
		}
	}
	
	private void addDay() {
		setDay(getDay() + 1);
	}
	
	public int getDay() { return day; }
	
	public void setDay(int day) { this.day = day; }
	
	public int getHour() { return hour;	}
	
	public void setHour(int hour) { this.hour = hour; }
	
	public int getMinute() { return minute; }
	
	public void setMinute(int minute) { this.minute = minute; }
	
	public int getSecond() { return second; }
	
	public void setSecond(int second) { this.second = second; }
	
}