package com.spotify.annoy;

public class AnnoyIndexImpl implements AnnoyIndex {
   static {
      System.loadLibrary("annoy"); // Load native library at runtime
   }

  private native void cppAddItem(int i, float[] vector);


  public AnnoyIndex addItem(int i, float[] vector) {
    cppAddItem(i, vector);
    return null;
  }

  public int[] getNearestByVector(float[] vector, int n) {
    return new int[0];
  }

  public int[] getNearestByVector(float[] vector, int n, int searchK) {
    return new int[0];
  }

  public int[] getNearestByItem(float[] vector, int n) {
    return new int[0];
  }

  public int[] getNearestByItem(float[] vector, int n, int searchK) {
    return new int[0];
  }

  public AnnoyIndex build(int nTrees) {
    return null;
  }

  public AnnoyIndex save(String filename) {
    return null;
  }

  public AnnoyIndex load(String filename) {
    return null;
  }

  public float[] getItemVector(int i) {
    return new float[0];
  }

  public float getDistance(int i, int j) {
    return 0;
  }

  public int size() {
    return 0;
  }
}
