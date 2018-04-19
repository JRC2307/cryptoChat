package com.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

public class ServerThread extends Thread {
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

  public ServerThread(){

  }

  public void run(){
    try {
      in = new DataInputStream(s.getInputStream());
      out = new DataOutputStream(s.getOutputStream());
      exit = false;
      name = in.readUTF();
      sendMessage(name + " contected");
    }catch (IOException e) {
      System.out.println("Error server thread");
    }
    while(!exit){
      try {
        message = in.readUTF();
        if (message.equalsIgnoreCase("exit")) {
          exit = true;
          sendMessage("  -" + name + " has been removed");
          removeClient();
        }else{
          sendMessage("   -" + name + " wrote:" + message);
        }
      }catch (Exception e){

        System.out.println("Error in server thread while sending message io" );
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
