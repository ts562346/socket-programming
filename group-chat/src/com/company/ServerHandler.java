package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread {
    private Socket server;
    private BufferedReader in;
    public ServerHandler(Socket socket){
        server = socket;

        try{
            //  reads input from the client handler
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run () {

        try{
            String serverMessage = null; // initialise string message from server
                do {

                    serverMessage = in.readLine();  // read input from the

                    if (!serverMessage.equals("")) {
                        System.out.println("\n" + serverMessage); // print message to the client
                        System.out.print("Enter message (BYE to quit): ");  // print message to the client
                    }


                } while (!serverMessage.equals("BYE")); // keep printing messages until user quits

            } catch (IOException e) { // catch exceptions
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



