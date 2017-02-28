package com.spotify.annoy;

class AnnoyIndexImpl implements AnnoyIndex {

  static {
    System.loadLibrary("annoy"); // Load native library at runtime
  }

  private native void cppCtor(int f);

  public AnnoyIndexImpl(int f) {
    cppCtor(f);
  }

  private native void cppAddItem(int i, float[] vector);

  public AnnoyIndex addItem(int i, float[] vector) {
    cppAddItem(i, vector);
    return this;
  }

  private native int[] cppGetNearestByVector(float[] vector, int n);

  public int[] getNearestByVector(float[] vector, int n) {
    return cppGetNearestByVector(vector, n);
  }

  private native int[] cppGetNearestByVector(float[] vector, int n, int searchK);

  public int[] getNearestByVector(float[] vector, int n, int searchK) {
    return cppGetNearestByVector(vector, n, searchK);
  }

  private native int[] cppGetNearestByItem(float[] vector, int n);

  public int[] getNearestByItem(float[] vector, int n) {
    return cppGetNearestByItem(vector, n);
  }

  private native int[] cppGetNearestByItem(float[] vector, int n, int searchK);

  public int[] getNearestByItem(float[] vector, int n, int searchK) {
    return cppGetNearestByItem(vector, n, searchK);
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
