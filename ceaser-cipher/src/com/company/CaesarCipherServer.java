package com.company;

import java.net.*;
import java.io.*;
import java.util.Random;

public class CaesarCipherServer {
	public static void main(String[] args) throws IOException{
		ServerSocket serverSock = null;
		try{
			serverSock = new ServerSocket(50000);
		}
		catch (IOException ie){
			System.out.println("Can't listen on 50000");
			System.exit(1);
		}
		Socket link = null;
		System.out.println("Listening for connection ...");
		try {
			link = serverSock.accept();
		}
		catch (IOException ie){
			System.out.println("Accept failed");
			System.exit(1);
		}
		System.out.println("Connection successful");
		System.out.println("Listening for input ...");
		PrintWriter output = new PrintWriter(link.getOutputStream(), true);
		BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));
		String inputLine;


		int key = 0; // set initial value of key to zero

		while ((inputLine = input.readLine())!=null){

			System.out.println("Message from client: " + inputLine);

			if(key > 0) {
				StringBuffer result= new StringBuffer(); // temporary variable for decryption

				for (int i = 0; i < inputLine.length(); i++) {

					if (Character.isUpperCase(inputLine.charAt(i))) {
						char ch = (char)(((int)inputLine.charAt(i) - key - 90) % 26 + 90);
						result.append(ch);
					}  else if (Character.isLowerCase(inputLine.charAt(i))) {
						char ch = (char)(((int)inputLine.charAt(i) - key - 122) % 26 + 122);
						result.append(ch);
					} else {
						result.append(inputLine.charAt(i));
					}

				}

				inputLine = String.valueOf(result); // the new string with decrypted result stored in inputLine

				if(inputLine.equals("Bye")){
					System.out.println("Closing connection");
					break;
				}

				System.out.println("Decrypted: " + inputLine); // the new string with decrypted result is displayed in
															   // the server side.
			}

			if(inputLine.equals("Please send the key")) {
				Random random = new Random();
				key = random.nextInt(25 - 1) + 1;
				output.println("The key is " + key); // a random key is generated and sent to client side when user
													 // ask for it.
			} else {
				output.println(inputLine); //
			}

			if(inputLine.equals("Bye")){
				System.out.println("Closing connection");
				break;
			}

		}


		output.close();
		input.close();
		link.close();
		serverSock.close();
	}
}
