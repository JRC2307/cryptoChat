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
  private String message, name;

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
      sendMessage(ANSI_CYAN+ name + " contected"+ ANSI_RESET);
    }catch (IOException e) {
      System.out.println("Error server thread" +ANSI_RESET);
    }
    while(!exit){
      try {
        message = in.readUTF();
        if (message.equalsIgnoreCase("exit")) {
          exit = true;
          sendMessage(ANSI_RED + "  -" + name + " has been removed" + ANSI_RESET);
          removeClient();
        }else{
          Caesar caesar = new Caesar();
          message = caesar.parallelDecrypt(message);
          sendMessage(ANSI_CYAN+"   -" + name + ANSI_RESET + " wrote:" + message);
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
