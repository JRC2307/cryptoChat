package com.client;

import com.encrypt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
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
      System.out.println(ANSI_GREEN + "Welcome to cryptoChat a minimal and distraction free chat.\n");
      System.out.println("We really care about your privacy, therefore we don't log your conversations\n" + ANSI_RESET);

      System.out.println(ANSI_PURPLE + "Here are some premade rooms for you, or you can create your own by typing '?roomname'" + ANSI_RESET);
      System.out.println(ANSI_PURPLE + "\n?main\n?crypto\n?music\n?sports\n" + ANSI_RESET);


      System.out.println("You are currently in the '?main' room. \n");

      System.out.println("Please input a nickname: ");

      name = in.readLine();

      clientT = new ClientThread(s, name);

      clientT.start();

      out.writeUTF(name);
    }catch (IOException e){
      System.out.println("There was an error with your nickname input");
    }
    
    while (!end){
      try {
        message=in.readLine();

        Caesar caesar = new Caesar();
        message = caesar.parallelEncrypt(message);

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
      s = new Socket("localhost", port);
      out = new DataOutputStream(s.getOutputStream());
      in = new BufferedReader(new InputStreamReader(System.in));
    }catch (IOException e){
      e.printStackTrace();
    }

  }

}
