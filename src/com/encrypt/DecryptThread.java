package com.encrypt;

import java.util.concurrent.RecursiveTask;

public class DecryptThread extends RecursiveTask<char[]> {

  private static final int THRESHOLD = 300;

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
        char c = (char)(messageArray[i] - 7);
        if (c > 'z')
        messageArray[i] = (char)(messageArray[i] + (26 + 7));
        else
        messageArray[i] = (char)(messageArray[i] - 7);
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
