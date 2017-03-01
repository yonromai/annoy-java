package com.spotify.annoy;

import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

class AnnoyIndexImpl implements AnnoyIndex {

  private native void cppCtor(int f);

  AnnoyIndexImpl() {
  }

  AnnoyIndex init(int f) {
    final String dir = System.getProperty("java.library.path");
    //set explicit path for our custom library
    System.load(dir + "/libannoy.jnilib"); //TODO: linux name is different.
    cppCtor(f);
    return this;
  }

  private native void cppAddItem(int i, float[] vector);

  public AnnoyIndex addItem(int i, float[] vector) {
    cppAddItem(i, vector);
    return this;
  }

  public AnnoyIndex addAllItems(Collection<List<Float>> vectors) {
    int i = size();
    for (List<Float> vector: vectors){
      float[] primitiveVector = ArrayUtils.toPrimitive(vector.toArray(new Float[0]));
      addItem(i++, primitiveVector);
    }
    return this;
  }

  private native int[] cppGetNearestByVector(float[] vector, int n);

  public int[] getNearestByVector(float[] vector, int n) {
    return cppGetNearestByVector(vector, n);
  }

  private native int[] cppGetNearestByVectorK(float[] vector, int n, int searchK);

  public int[] getNearestByVectorK(float[] vector, int n, int searchK) {
    return cppGetNearestByVectorK(vector, n, searchK);
  }

  private native int[] cppGetNearestByItem(int item, int n);

  public int[] getNearestByItem(int item, int n) {
    return cppGetNearestByItem(item, n);
  }

  private native int[] cppGetNearestByItemK(int item, int n, int searchK);

  public int[] getNearestByItemK(int item, int n, int searchK) {
    return cppGetNearestByItemK(item, n, searchK);
  }

  private native void cppBuild(int nTrees);

  public AnnoyIndex build(int nTrees) {
    cppBuild(nTrees);
    return this;
  }

  private native void cppSave(String filename);

  public AnnoyIndex save(String filename) {
    cppSave(filename);
    return this;
  }

  private native void cppLoad(String filename);

  public AnnoyIndex load(String filename) {
    cppLoad(filename);
    return this;
  }

  private native float[] cppGetItemVector(int i);

  public float[] getItemVector(int i) {
    return cppGetItemVector(i);
  }

  private native float cppGetDistance(int i, int j);

  public float getDistance(int i, int j) {
    return cppGetDistance(i, j);
  }

  private native int cppSize();

  public int size() {
    return cppSize();
  }
}
