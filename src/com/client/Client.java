package com.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int port = 1234;
    public static DataOutputStream out;
    public static BufferedReader in;
    //conection
    public static Socket s;
    //strings
    public static String message, name;
    //client thread
    public static ClientThread clientT;
    //Boolean
    public static boolean end;

    public static void main(String[] args){
            init();
            try {
                System.out.println("Welcome to the chat.");

                System.out.println("Input your name: ");
                name = in.readLine();
                clientT = new ClientThread(s, name);
                clientT.start();
                out.writeUTF(name);
            }catch (IOException e){
                System.out.println("There was an error with your name input");
            }
            while (!end){
                try {
                    message=in.readLine();
                    out.writeUTF(message);
                    if (message.equalsIgnoreCase("exit")) {
                        end = true;
                    }
                }catch (IOException e){
                    System.out.println("Error in message loop");
                }

            }

    }
    private static void init(){
        try {
            end = false;
            s = new Socket("Localhost", port);
            out = new DataOutputStream(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(System.in));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
