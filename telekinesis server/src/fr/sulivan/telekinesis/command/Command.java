package fr.sulivan.telekinesis.command;

public class Command {
	
	private String command;
	private String[] arguments;
	
	public Command(String command , String[] args){
		this.command = command;
		this.arguments = args;
	}
	
	public String getCommandName(){
		return command;
	}
	
	public String getArgument(int index){
		try{
			return arguments[index];
		}catch(IndexOutOfBoundsException exception){
			return null;
		}
	}
	
	public int getIntArgument(int index){
		return getArgument(index) == null ? 0 : Integer.parseInt(getArgument(0));
	}
}
