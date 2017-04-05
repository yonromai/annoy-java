/*
 * -\-\-
 * annoy-java
 * --
 * Copyright (C) 2016 Spotify AB
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

class AnnoyIndexImpl implements AnnoyIndex {

  private final int dim;

  public List<Integer> getNearestByVector(List<Float> vector, int nbNeighbors) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVector(boxedToPrimitive(vector), nbNeighbors));
  }

  public List<Integer> getNearestByVector(List<Float> vector, int nbNeighbors, int searchK) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVectorK(boxedToPrimitive(vector), nbNeighbors, searchK));
  }

  public List<Integer> getNearestByItem(int item, int nbNeighbors) {
    return primitiveToBoxed(cppGetNearestByItem(item, nbNeighbors));
  }

  public List<Integer> getNearestByItem(int item, int nbNeighbors, int searchK) {
    return primitiveToBoxed(cppGetNearestByItemK(item, nbNeighbors, searchK));
  }

  public AnnoyIndex save(String filename) {
    cppSave(filename);
    return this;
  }

  public List<Float> getItemVector(int item) {
    return primitiveToBoxed(cppGetItemVector(item));
  }

  public float getDistance(int itemA, int itemB) {
    return cppGetDistance(itemA, itemB);
  }

  public int size() {
    return cppSize();
  }

  // Construction

  AnnoyIndexImpl(int dim) {
    this.dim = dim;
    System.load(Annoy.ANNOY_LIB_PATH);
    cppCtor(dim);
  }

  AnnoyIndexImpl addItem(int item, List<Float> vector) {
    validateVecSize(vector);
    cppAddItem(item, boxedToPrimitive(vector));
    return this;
  }

  AnnoyIndexImpl addAllItems(Iterable<List<Float>> vectors) {
    int nb = size();
    for (List<Float> vector : vectors) {
      addItem(nb++, vector);
    }
    return this;
  }

  AnnoyIndexImpl build(int nbTrees) {
    cppBuild(nbTrees);
    return this;
  }

  AnnoyIndexImpl load(String filename) throws FileNotFoundException {
    if (Files.notExists(Paths.get(filename))) {
      throw new FileNotFoundException("Cannot find annoy index: " + filename);
    }
    cppLoad(filename);
    return this;
  }

  AnnoyIndexImpl setSeed(int seed) {
    cppSetSeed(seed);
    return this;
  }

  // Helpers

  private static List<Float> primitiveToBoxed(float[] vector) {
    return Arrays.asList(ArrayUtils.toObject(vector));
  }

  private static List<Integer> primitiveToBoxed(int[] vector) {
    return Arrays.asList(ArrayUtils.toObject(vector));
  }

  private static float[] boxedToPrimitive(List<Float> vector) {
    return ArrayUtils.toPrimitive(vector.toArray(new Float[0]));
  }

  private void validateVecSize(List<Float> vector) {
    if (vector.size() != dim) {
      throw new RuntimeException("Item's vector should match the dimension of the tree");
    }
  }

  // Native cpp  methods

  private native void cppCtor(int dim);

  private native void cppAddItem(int item, float[] vector);

  private native int[] cppGetNearestByVector(float[] vector, int nbNeighbors);

  private native int[] cppGetNearestByVectorK(float[] vector, int nbNeighbors, int searchK);

  private native int[] cppGetNearestByItem(int item, int nbNeighbors);

  private native int[] cppGetNearestByItemK(int item, int nbNeighbors, int searchK);

  private native void cppBuild(int nbTrees);

  private native void cppSave(String filename);

  private native void cppLoad(String filename);

  private native float[] cppGetItemVector(int item);

  private native float cppGetDistance(int itemA, int itemB);

  private native int cppSize();

  private native void cppSetSeed(int seed);
}
