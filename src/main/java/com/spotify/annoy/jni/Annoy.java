package com.spotify.annoy.jni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;



public class Annoy {

  public static class Builder {

    private final AnnoyIndexImpl ann;

    private Builder(int dim) {
      ann = new AnnoyIndexImpl(dim);
    }

    public Builder addItem(int i, List<Float> vector) {
      ann.addItem(i, vector);
      return this;
    }

    public Builder addAllItems(Iterable<List<Float>> vectors) {
      ann.addAllItems(vectors);
      return this;
    }

    public Builder setSeed(int seed) {
      ann.setSeed(seed);
      return this;
    }

    public AnnoyIndex build(int nTrees) {
      return ann.build(nTrees);
    }
  }

  public static Builder newIndex(int dim) {
    return new Builder(dim);
  }

  public static AnnoyIndex loadIndex(String filename, int dim) throws FileNotFoundException {
    return loadIndex(filename, dim, 42);
  }

  public static AnnoyIndex loadIndex(String filename, int dim, int rngSeed) throws FileNotFoundException {
    return new AnnoyIndexImpl(dim)
        .setSeed(rngSeed)
        .load(filename);
  }
  final static String LIB_PATH = "java.library.path";
  static String ANNOY_LIB_NAME = "/native/Mac/x86_64/libannoy.jnilib";
  // Enable to install annoy on the fly, :'(
  public static void install() throws IOException, InterruptedException {
    if (!System.getProperty("os.name").equals("Mac OS X")) {
      ANNOY_LIB_NAME = "/native/Linux/x86_64/libannoy.so";
    }
    String annoyLibPath = System.getProperty(LIB_PATH) + ANNOY_LIB_NAME;
    if (new File(annoyLibPath).exists()) {
      return; // Already installed
    }
    verifyInstall();
    File jniDir = new File(ClassLoader.getSystemResource("jni").getFile());
    System.setProperty(LIB_PATH, jniDir.getAbsolutePath());
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

  private static void verifyInstall() {
    // TODO: verify g++ and make are installed
  }
}
