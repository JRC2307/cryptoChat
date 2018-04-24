package com.encrypt;

import java.util.concurrent.RecursiveTask;

public class DecryptThread extends RecursiveTask<char[]> {
  
  private static final int THRESHOLD = 300;
  private static final int shift = 7;

  int start;
  int end;

  char[] messageArray;


  public DecryptThread(int start, int end, char[] messageArray){
    this.messageArray = messageArray;
    this.start = start;
    this.end = end;
  }


  @Override
  protected char[] compute() {
    int limit = end - start;
    if (limit < THRESHOLD) {
      for (int i = start; i < end; i++) {
        char c = (char)(messageArray[i] - shift);
        if (c > 'z')
        messageArray[i] = (char)(messageArray[i] + (26 - shift));
        else
        messageArray[i] = (char)(messageArray[i] - shift);
      }
    } else {
        int mid = (start + end) / 2;
        CaesarThread t1, t2;
        t1 = new CaesarThread(start, mid, messageArray);
        t2 = new CaesarThread(mid, end, messageArray);
        t1.fork();
        t1.join();
        t2.compute();
    }
    return messageArray;
  }

}

/*Copyright (C) <2018>  <Javier Rodríguez>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>. */
