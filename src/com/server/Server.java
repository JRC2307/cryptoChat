package com.server;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;

public class Server{
  public static final int port = 1234;
  private static ServerSocket ss;
  private static Boolean exit;
  private static int cont;
  private static Users list;

  public static void main(String[] args) {
    try {
      cont = 0;
      list = new Users();
      ss = new ServerSocket(port);
      exit = false;
      System.out.println("Server started on port "+ port);
      while(!exit){
        Socket s=ss.accept();
        list.addUser(cont, s);
        ServerThread st = new ServerThread(s, list, cont);
        st.start();
        cont++;
      }
    }catch (IOException e) {
        System.out.println("Server error");
        e.printStackTrace();
    }
  }

}
