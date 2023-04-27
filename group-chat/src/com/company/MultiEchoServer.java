package com.company;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MultiEchoServer{
	//declare the ServerSocket variable and the port number for the server(constant)
	private static ServerSocket serverSock;
	private static final int PORT = 1234;

	// Array to store ClientHandler objects
	private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

	//The main method will create the ServerSocket object and listens to inputs
	//from multiple clients

	public static void main(String[] args) throws IOException{
		try{
			serverSock = new ServerSocket(PORT);
		}
		catch(IOException e){
			System.out.println("Can't listen on " + PORT);
			System.exit(1);
		}
		do{
			Socket client = null;
			System.out.println("Listening for connection ...");
			try{
				client = serverSock.accept();
				System.out.println("New client accepted");

				// create client handler objects when a new Client joins
				ClientHandler handler = new ClientHandler(client, clientHandlers);
				clientHandlers.add(handler); // adds client to the arraylist

				handler.start();
			}
			catch(IOException e){
				System.out.println("Accept failed");
				System.exit(1);
			}
			System.out.println("Connection successful");
			System.out.println("Listening for input ...");
		} while (true);
	}
}
