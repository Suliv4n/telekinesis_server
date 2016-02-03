package fr.sulivan.telekinesis.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import fr.sulivan.telekinesis.Telekinesis;

public class Server {
	
	public static final int DEFAULT_PORT = 4242;
	
	private ServerSocket serverSocket;
	private Socket client;
	
	private Thread listen;
	
	private PrintWriter out;
	
	private boolean verbose = true;
	
	public Server(boolean verbose) throws IOException{
		this.verbose = verbose;
		serverSocket = new ServerSocket(DEFAULT_PORT);
	}
	
	public Server() throws IOException{
		serverSocket = new ServerSocket(DEFAULT_PORT);
	}
	
	public void run(){
		log("Waiting for a client...");
		try {
			client = serverSocket.accept();

			out = new PrintWriter(client.getOutputStream());
			send("OK");
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			
			
			listen = new Thread(
				() -> {
					while(true){
						try {
							System.out.println("Test");
							String line = input.readLine();
							log("Client send : %s", line);
							Telekinesis.interpreter.execute(line);
							
						} catch (Exception e) {
							log("Client disconnected");
							closeClient(input);
							run();
							listen.interrupt();
						}
					}
				}	
			);
			
			listen.start();
			
		} catch (IOException e) {
			log("Client connection fail... Try again.");
			run();
		}
		
		log("Client connected");
	}
	
	public void send(String message) throws IOException{
		log("Send to client : " + message);
		PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println(message);
        out.flush();
	}
	
	private void closeClient(BufferedReader input) {
		try {
			input.close();
		} catch (IOException e) {}
	}

	public void close() throws IOException{
		listen.interrupt();
		serverSocket.close();
	}
	
	
	
	//log
	public void log(String message, Object... args){
		if(verbose){
			System.out.println(String.format(message, args));
		}
	}
	
	public void log(String message){
		if(verbose){
			System.out.println(message);
		}
	}
}
