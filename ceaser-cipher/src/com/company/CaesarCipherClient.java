package com.company;

import java.io.*;
import java.net.*;
public class EchoClient{
	public static void main(String[] args) throws IOException{
		Socket link = null;
		PrintWriter output = null;
		BufferedReader input = null;

		try{
			link = new Socket("127.0.0.1", 50000);
			output = new PrintWriter(link.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(link.getInputStream()));
		}
		catch(UnknownHostException e)
		{
			System.out.println("Unknown Host");
			System.exit(1);
		}
		catch (IOException e){
			System.out.println("Cannot connect to host");
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String usrInput;

		int key = 0; // set initial value of key to zero
		while ((usrInput = stdIn.readLine())!=null){
			if(key > 0){

				StringBuffer result= new StringBuffer(); // temporary variable for encryption

				// algorithm for encrypting input by key
				for (int i=0; i <usrInput.length(); i++) {
					if (Character.isUpperCase(usrInput.charAt(i))) {
						char ch = (char)(((int)usrInput.charAt(i) + key - 65) % 26 + 65);
						result.append(ch);
					}  else if (Character.isLowerCase(usrInput.charAt(i))) {
						char ch = (char)(((int)usrInput.charAt(i) + key - 97) % 26 + 97);
						result.append(ch);
					} else {
						result.append(usrInput.charAt(i));
					}
				}

				usrInput = String.valueOf(result); // the new string with encrypted result stored in usrInput
			}

			output.println(usrInput); // send encrypted or non-encrypted usrInput to server

			String message = input.readLine(); // message is equal to the output received from server

			if(key == 0 && !message.equals("Bye") ) {
				System.out.println("Echo from the server: " + message);
			}

			if(usrInput.equals("Please send the key")){
				key = Integer.valueOf(message.substring(13 - 2)); // retrieve the key (last two chars of string)
			}
		}

		output.close();
		input.close();
		stdIn.close();
		link.close();
	}
}
