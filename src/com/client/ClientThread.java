package com.client;

import com.encrypt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket s;
    private DataInputStream in;
    private String name, message, dec;
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
                 Caesar caesar = new Caesar();
                 message = caesar.parallelDecrypt(message);
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
