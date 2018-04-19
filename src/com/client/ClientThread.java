package com.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket s;
    private DataInputStream in;
    private String name, message;
    private boolean end;


    public ClientThread(Socket s, String name){
            this.s = s;
            this.name = name;
    }

    public void run(){
        try {
            end = false;
            in = new DataInputStream(s.getInputStream());

            while (!end){
                message = in.readUTF();
                System.out.println(message);
                if (message.equalsIgnoreCase("-  " + name + " disconected")){
                    end = true;
                }
            }
            s.close();
        }catch (Exception e){
          System.out.println("Error in Client thread");
          e.printStackTrace();
          end = true;
        }
    }
}
