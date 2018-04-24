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
