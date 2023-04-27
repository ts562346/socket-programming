package com.company;

//This is a support class that extends Thread, runs the client thread
//and sends and receives messages

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler extends Thread{
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clientHandlers;

	public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers){
		client = socket;
		this.clientHandlers = clientHandlers;
		try{
			in = new BufferedReader(new InputStreamReader(client.getInputStream())); // read from client
			out = new PrintWriter(client.getOutputStream(), true); // print to server
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}


	public void run(){
		try{
			String received = in.readLine(); // read line received from client
			String name = received; // read name from user
			System.out.println(name + " has joined"); // print user name

			/* This loop is for printing newly joined users */
			for (ClientHandler client: clientHandlers){ // loop through ClientHandler array
				if (!client.getName().equals(this.getName())) { // check reference to array and if its not the current user
					client.out.println("\n" + name + " has joined"); // broadcast the message
				}
			}

			/* This loop is for broadcasting user message */
			do{
				received = in.readLine();
				System.out.println("Message from " + name + ": " + received); // send message to server
				for (ClientHandler client: clientHandlers){
					if (!client.getName().equals(this.getName())) {
						client.out.println("Message from " + name + ": " + received); // broadcast message only when it's
																						// not from the current user
					}
				}

			} while (!received.equals("BYE")); // keep looping until user exits
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(client!=null){
					System.out.println("Closing connection");
					client.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
