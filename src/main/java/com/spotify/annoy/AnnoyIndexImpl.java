package com.spotify.annoy;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

class AnnoyIndexImpl implements AnnoyIndex {

  private final int f;

  private native void cppCtor(int f);

  AnnoyIndexImpl(int f) {
    this.f = f;
    final String dir = System.getProperty("java.library.path");
    //set explicit path for our custom library
    System.load(dir + "/libannoy.jnilib"); //TODO: linux name is different.
    cppCtor(f);
  }

  private native void cppSetSeed(int seed);
  AnnoyIndex setSeed(int seed) {
    cppSetSeed(seed);
    return this;
  }

  private native void cppAddItem(int i, float[] vector);

  AnnoyIndex addItem(int i, List<Float> vector) {
    validateVecSize(vector);
    cppAddItem(i, boxedToPrimitive(vector));
    return this;
  }

  AnnoyIndex addAllItems(Iterable<List<Float>> vectors) {
    int i = size();
    for (List<Float> vector : vectors) {
      addItem(i++, vector);
    }
    return this;
  }

  private native int[] cppGetNearestByVector(float[] vector, int n);

  public List<Integer> getNearestByVector(List<Float> vector, int n) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVector(boxedToPrimitive(vector), n));
  }

  private native int[] cppGetNearestByVectorK(float[] vector, int n, int searchK);

  public List<Integer> getNearestByVectorK(List<Float> vector, int n, int searchK) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVectorK(boxedToPrimitive(vector), n, searchK));
  }

  private native int[] cppGetNearestByItem(int item, int n);

  public List<Integer> getNearestByItem(int item, int n) {
    return primitiveToBoxed(cppGetNearestByItem(item, n));
  }

  private native int[] cppGetNearestByItemK(int item, int n, int searchK);

  public List<Integer> getNearestByItemK(int item, int n, int searchK) {
    return primitiveToBoxed(cppGetNearestByItemK(item, n, searchK));
  }

  private native void cppBuild(int nTrees);

  AnnoyIndex build(int nTrees) {
    cppBuild(nTrees);
    return this;
  }

  private native void cppSave(String filename);

  public AnnoyIndex save(String filename) {
    cppSave(filename);
    return this;
  }

  private native void cppLoad(String filename);

  AnnoyIndex load(String filename) {
    cppLoad(filename);
    return this;
  }

  private native float[] cppGetItemVector(int i);

  public List<Float> getItemVector(int i) {
    return primitiveToBoxed(cppGetItemVector(i));
  }

  private native float cppGetDistance(int i, int j);

  public float getDistance(int i, int j) {
    return cppGetDistance(i, j);
  }

  private native int cppSize();

  public int size() {
    return cppSize();
  }

  // helpers

  private static List<Float> primitiveToBoxed(float[] v) {
    return Arrays.asList(ArrayUtils.toObject(v));
  }

  private static List<Integer> primitiveToBoxed(int[] v) {
    return Arrays.asList(ArrayUtils.toObject(v));
  }

  private static float[] boxedToPrimitive(List<Float> v) {
    return ArrayUtils.toPrimitive(v.toArray(new Float[0]));
  }

  private void validateVecSize(List<Float> vector) {
    if (vector.size() != f) {
      throw new RuntimeException("Item's vector should match the dimension of the tree");
    }
  }
}
