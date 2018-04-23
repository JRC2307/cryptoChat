package com.encrypt;

import java.io.FileNotFoundException;
import java.util.concurrent.ForkJoinPool;

public class Caesar {

  public String parallelEncrypt(String message) throws FileNotFoundException {
    char[] charArray = message.toCharArray();
    CaesarThread t = new CaesarThread(0, charArray.length, charArray);
    ForkJoinPool pool = new ForkJoinPool();
    char[] result = pool.invoke(t);
    pool.shutdown();

    String encryptedData = String.copyValueOf(result);

    return encryptedData;
  }

  public String parallelDecrypt(String message) throws FileNotFoundException {
    char[] charArray = message.toCharArray();
    DecryptThread t = new DecryptThread(0, charArray.length, charArray);
    ForkJoinPool pool = new ForkJoinPool();
    char[] result = pool.invoke(t);
    pool.shutdown();

    String encryptedData = String.copyValueOf(result);

    return encryptedData;
  }
}
