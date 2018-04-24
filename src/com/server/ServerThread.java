package com.server;

import com.encrypt.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

public class ServerThread extends Thread {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  private Socket s;
  private int id;
  private DataInputStream in;
  private DataOutputStream out;
  private Users list;
  private boolean exit;
  private String message, name, welcome, removed;

  public ServerThread(Socket s, Users list, int id){
    this.s=s;
    this.id=id;
    this.list=list;
  }

  public void run(){
    try {
      in = new DataInputStream(s.getInputStream());
      out = new DataOutputStream(s.getOutputStream());
      exit = false;
      name = in.readUTF();
      welcome = ( name + " has connected\n");
      //encrypt name and connected message
      Caesar caesar = new Caesar();
      welcome = caesar.parallelEncrypt(welcome);
      sendMessage(welcome);
    }catch (IOException e) {
      System.out.println(ANSI_RED + "Error server thread" +ANSI_RESET);
    }
    while(!exit){
      try {
        message = in.readUTF();
        Caesar caesar = new Caesar();
        message = caesar.parallelDecrypt(message);
        if (message.equalsIgnoreCase("exit")) {
          exit = true;
          removed = (name + " has been removed"+"\n");
          removed = caesar.parallelEncrypt(removed);
          sendMessage(removed);
          removeClient();
        }else{
          message = ("   -" + name + " wrote:" + message);
          message = caesar.parallelEncrypt(message);
          sendMessage(message+"\n");
        }
      }catch (Exception e){
        System.out.println(ANSI_RED +"Error in server thread while sending message io" + ANSI_RESET);
        e.printStackTrace();
        break;
      }

    }
  }
  private void sendMessage(String message){
    try {
      Iterator it=list.getUsers().entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        out = new DataOutputStream(((Socket) pair.getValue()).getOutputStream());
        out.writeUTF(message);
      }
    }catch (IOException e) {
      System.out.println("Error send message");
    }
  }
  private void removeClient(){
  list.removeUser(id);
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
