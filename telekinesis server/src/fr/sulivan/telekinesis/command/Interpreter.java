package fr.sulivan.telekinesis.command;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Interpreter {
	
	private HashMap<String, Consumer<Command>> commands;
	
	public Interpreter(){
		commands = new HashMap<String, Consumer<Command>>();
	}
	
	public void register(String command, Consumer<Command> handler){
		commands.put(command, handler);
	}
	
	public void execute(String line){
		try {
			
			Command command = parse(line);
			Consumer<Command> handler = commands.get(command.getCommandName());
			if(handler == null){
				throw new CommandParseException(String.format("Command %s not found", command.getCommandName()));
			}
			handler.accept(command);
		} catch (CommandParseException e) {
			e.printStackTrace();
		}
	}
	
	private Command parse(String line) throws CommandParseException{
		
		String res[] = line.trim().split(" ");
		String quote = "";
		boolean findQuote = false;
		ArrayList<String> parse = new ArrayList<String>();
		if(res.length == 0){
			throw new CommandParseException("Empty command");
		}
		for(String s : res){
			if(!s.contains("\"")){ //pas de quote
				if(findQuote){
					quote += s + " ";
				}
				else{
					parse.add(s);
				}
			}
			else if(s.matches("$\"(.*)\"^")){ //quotes sans espace
				parse.add(s);
			}
			else if(s.matches("^\".*") && !findQuote){ // Une quote detecté
				quote += s.replace("\"","") + " ";
				findQuote = true;
			}
			else if(s.matches(".*\"$") && findQuote){ //Fin de quote
				quote += s.replace("\"","");
				findQuote = false;
				parse.add(quote);
				quote="";
			}
		}
		
		String commandLine = parse.get(0);
		String[] args = new String[parse.size() - 1];
		
		for(int i=1; i<parse.size(); i++){
			args[i-1] = parse.get(i);
		}
		
		return new Command(commandLine, args);
	}
}
