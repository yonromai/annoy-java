package com.spotify.annoy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Annoy {

  private static void verifyInstall() {}

  public static void install() throws IOException, InterruptedException {
    Runtime rt = Runtime.getRuntime();
    File jniDir = new File(ClassLoader.getSystemResource("jni").getFile());
    Process pr = rt.exec("make", null , jniDir);
    int retCode = pr.waitFor();
    if (retCode != 0) {
      System.out.println("ret code: " + retCode);
      printInputStream(pr.getErrorStream());
    }
  }

  // FIXME: add proper logging
  private static void printInputStream(InputStream inputStream) throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferReader = new BufferedReader(inputStreamReader);
    while (true) {
      String line = bufferReader.readLine();
      if (line != null) {
        System.out.println(line);
      } else {
        break;
      }
    }
  }

}
