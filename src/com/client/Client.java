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
  public static final int port = 1818;
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
      System.out.println("This program is distributed in the hope that it will be useful\n"+
      " but WITHOUT ANY WARRANTY; without even the implied warranty of" +
      " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n" +
      "See the GNU General Public License for more details.\n\n");
      System.out.println(ANSI_GREEN + "Welcome to cryptoChat a minimal and distraction free chat.\n");
      System.out.println("We really care about your privacy, therefore we don't log your conversations\n" + ANSI_RESET);

      System.out.println("Please input a nickname: ");

      name = in.readLine();

      clientT = new ClientThread(s, name);

      clientT.start();

      out.writeUTF(name);
    }catch (IOException e){
      System.out.println(ANSI_RED +"There was an error with your nickname input" + ANSI_RESET);
    }
    while (!end){
      try {
        message=in.readLine();
        //encrypt message
        Caesar caesar = new Caesar();
        message = caesar.parallelEncrypt(message);
        out.writeUTF(message);
        if (message.equalsIgnoreCase("exit")) {
          end = true;
        }
      }catch (IOException e){
        System.out.println(ANSI_RED + "Error in message loop"+ ANSI_RESET);
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
/*Copyright (C) <2018>  <Javier Rodríguez>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>. */
