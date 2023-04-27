package com.company;

import java.io.*;
import java.net.*;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;

public class MultiEchoClient{
	private static final int PORT = 1234;
	private static Socket link;
	private static PrintWriter out;
	private static BufferedReader kbd;



	public static void main(String[] args) throws Exception{
		try{
			link = new Socket("127.0.0.1", PORT);

			ServerHandler serverHandler = new ServerHandler(link);
			new Thread(serverHandler).start();

			out = new PrintWriter(link.getOutputStream(), true);
			kbd = new BufferedReader(new InputStreamReader(System.in));
			String message = null, response;

			// prompting client to enter name upon connection
			System.out.print("Enter name: ");
			out.println(kbd.readLine());

			/* This loop is for taking continuous user messages */
			do {

					System.out.print("Enter message (BYE to quit): ");
					message = kbd.readLine();
					out.println(message);


			} while (!message.equals("BYE")); // the loop quits when user writes 'BYE'
		}
		catch(UnknownHostException e){System.exit(1);}
		catch(IOException e){System.exit(1);}
		finally{
			try{
				if (link!=null){
					System.out.println("Connection shut down");
					link.close();
				}
			}
			catch(IOException e){System.exit(1);}
		}
	}
}
	
