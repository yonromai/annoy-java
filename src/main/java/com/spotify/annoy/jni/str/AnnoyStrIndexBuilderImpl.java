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

package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.Annoy;
import com.spotify.annoy.jni.base.AnnoyIndex;
import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyReader;
import com.spotify.sparkey.SparkeyWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class AnnoyStrIndexBuilderImpl implements AnnoyStrIndexBuilder {

  // TODO: move to conf
  private static final String annoyIndexFilename = "annoy.tree";
  private static final String idToStrIndexFilename = "idToStrIndex";
  private static final String strToIdIndexFilename = "strToIdIndex";

  // Building logic
  private final String dirName;
  private final Annoy.Builder annoyBuilder;
  private final SparkeyWriter idToStrIndexWriter;
  private final SparkeyWriter strToIdIndexWriter;
  private int itemCount = 0;

  private File getIdToStrIndexPath() {
    return Paths.get(dirName, idToStrIndexFilename).toFile();
  }

  private File getStrToIdIndexPath() {
    return Paths.get(dirName, strToIdIndexFilename).toFile();
  }

  AnnoyStrIndexBuilderImpl(String dirName, int dim, Annoy.Metric metric) throws IOException {
    this.dirName = dirName;
    annoyBuilder = Annoy.newIndex(dim, metric);
    idToStrIndexWriter = Sparkey.createNew(getIdToStrIndexPath());
    strToIdIndexWriter = Sparkey.createNew(getStrToIdIndexPath());
  }

  @Override
  public AnnoyStrIndexBuilder addItem(String key, List<Float> vector) throws IOException {
    Integer id = itemCount++;
    idToStrIndexWriter.put(id.toString(), key);
    strToIdIndexWriter.put(key, id.toString());
    annoyBuilder.addItem(id, vector);
    return this;
  }

  @Override
  public AnnoyStrIndexBuilder setSeed(int seed) {
    annoyBuilder.setSeed(seed);
    return this;
  }

  @Override
  public AnnoyStrIndex build(int nbTrees) throws IOException {
    idToStrIndexWriter.writeHash();
    idToStrIndexWriter.close();
    strToIdIndexWriter.writeHash();
    strToIdIndexWriter.close();

    Path annoyPath = Paths.get(dirName, annoyIndexFilename);
    AnnoyIndex annoyIndex = annoyBuilder.build(nbTrees).save(annoyPath.toString());
    
    return new AnnoyStrIndexImpl(annoyIndex,
        Sparkey.open(getIdToStrIndexPath()),
        Sparkey.open(getStrToIdIndexPath()));
  }

  // Loading logic

  static AnnoyStrIndex loadIndex(String dirName, int dim) throws IOException {
    return loadIndex(dirName, dim, Annoy.Metric.ANGULAR);
  }

  static AnnoyStrIndex loadIndex(String dirName, int dim, Annoy.Metric metric) throws IOException {
    Path annoyPath = Paths.get(dirName, annoyIndexFilename);
    AnnoyIndex annoyIndex = Annoy.loadIndex(annoyPath.toString(), dim, metric);

    Path idToStrIndexPath = Paths.get(dirName, idToStrIndexFilename);
    SparkeyReader idToStrIndex = Sparkey.open(idToStrIndexPath.toFile());

    Path strToIdIndexPath = Paths.get(dirName, strToIdIndexFilename);
    SparkeyReader strToIdIndex = Sparkey.open(strToIdIndexPath.toFile());

    return new AnnoyStrIndexImpl(annoyIndex, idToStrIndex, strToIdIndex);
  }
}
