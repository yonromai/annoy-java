/*
 * -\-\-
 * annoy-java
 * --
 * Copyright (C) 2017 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.annoy.jni.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Annoy {

  public enum Metric {
    ANGULAR,
    EUCLIDEAN
  }

  public static class Builder {

    private final AnnoyIndexImpl ann;

    private Builder(int dim, Metric angular) {
      ann = new AnnoyIndexImpl(dim, angular);
    }

    public Builder addItem(int item, List<Float> vector) {
      ann.addItem(item, vector);
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

    public AnnoyIndex build(int nbTrees) {
      return ann.build(nbTrees);
    }
  }

  public static Builder newIndex(int dim, Metric metric) {
    return new Builder(dim, metric);
  }

  public static Builder newIndex(int dim) {
    return new Builder(dim, Metric.ANGULAR);
  }

  public static AnnoyIndex loadIndex(String filename, int dim) throws FileNotFoundException {
    return loadIndex(filename, dim, Metric.ANGULAR, 42);
  }

  public static AnnoyIndex loadIndex(String filename, int dim, Metric metric)
      throws FileNotFoundException {
    return loadIndex(filename, dim, metric, 42);
  }

  public static AnnoyIndex loadIndex(String filename, int dim, Metric metric, int rngSeed)
      throws FileNotFoundException {
    return new AnnoyIndexImpl(dim, metric)
        .setSeed(rngSeed)
        .load(filename);
  }

  static final String ANNOY_LIB_PATH = extractAnnoyBinaries();

  private static String extractAnnoyBinaries() {
    final String libname = System.mapLibraryName("annoy");
    InputStream annoy = AnnoyIndexImpl.class
        .getResourceAsStream("/jni/" + libname);
    try {
      Path tempAnnoy = Files.createTempDirectory("").resolve(libname);
      Files.copy(annoy, tempAnnoy);
      tempAnnoy.toFile().deleteOnExit();
      return tempAnnoy.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
