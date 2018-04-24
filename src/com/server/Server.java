package com.server;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;

public class Server{
  public static final int port = 1818;
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
