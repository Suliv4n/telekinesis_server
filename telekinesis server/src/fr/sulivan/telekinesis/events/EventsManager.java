package fr.sulivan.telekinesis.events;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class EventsManager {
	
	private Robot robot;
	private static EventsManager instance;
	
	private EventsManager() throws AWTException{
		robot = new Robot();
	}
	
	public static void init() throws AWTException{
		instance = new EventsManager();
	}
	
	public static EventsManager get(){
		return instance;
	}
	
	public void mouveMouse(int x, int y){
		robot.mouseMove(x, y);
	}

	public void click() {
	
		robot.mousePress(InputEvent.BUTTON1_MASK );
		robot.mouseRelease(InputEvent.BUTTON1_MASK );
	}
}
