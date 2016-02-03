package fr.sulivan.telekinesis;

import java.awt.AWTException;
import java.io.IOException;

import fr.sulivan.telekinesis.command.Interpreter;
import fr.sulivan.telekinesis.events.EventsManager;
import fr.sulivan.telekinesis.server.Server;

public class Telekinesis {

	public static final Interpreter interpreter = new Interpreter();
	
	public static void main(String[] args) {
		
		try {
			EventsManager.init();
			Server server = new Server();
			
			interpreter.register("mouse", c -> {
				int x = c.getIntArgument(0);
				int y = c.getIntArgument(1);
				EventsManager.get().mouveMouse(x, y);
			});
			
			interpreter.register("click", c -> {
				EventsManager.get().click();
			});
			
			server.run();
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}

	}

}
