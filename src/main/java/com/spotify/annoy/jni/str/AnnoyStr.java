package com.spotify.annoy.jni.str;

import java.io.IOException;

public class AnnoyStr {

  public static AnnoyStrIndexBuilder newIndex(String dirName, int dim) throws IOException {
    return new AnnoyStrIndexBuilderImpl(dirName, dim);
  }

  public static AnnoyStrIndex loadIndex(String dirName, int dim) throws IOException {
    return AnnoyStrIndexBuilderImpl.loadIndex(dirName, dim);
  }
}
