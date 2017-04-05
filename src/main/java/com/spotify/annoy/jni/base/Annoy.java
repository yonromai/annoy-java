package com.spotify.annoy.jni.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

  public static AnnoyIndex loadIndex(String filename, int dim, int rngSeed)
      throws FileNotFoundException {
    return new AnnoyIndexImpl(dim)
        .setSeed(rngSeed)
        .load(filename);
  }

  static final String ANNOY_LIB_PATH = extractAnnoyBinaries();

  private static String extractAnnoyBinaries() {
    InputStream annoy = AnnoyIndexImpl.class.
        getResourceAsStream("/jni/" + System.mapLibraryName("annoy"));
    try {
      Path tempAnnoy = Files.createTempDirectory("").resolve(System.mapLibraryName("annoy"));
      Files.copy(annoy, tempAnnoy);
      tempAnnoy.toFile().deleteOnExit();
      return tempAnnoy.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
