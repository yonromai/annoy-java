package com.spotify.annoy;

class AnnoyIndexImpl implements AnnoyIndex {

  private native void cppCtor(int f);

  public AnnoyIndexImpl() {
  }

  AnnoyIndex init(int f) {
    System.out.println(">>> " +  System.getProperty("java.library.path"));
    final String dir = System.getProperty("java.library.path");
    //set explicit path for our custom library
    System.load(dir + "/libannoy.jnilib"); //TODO: linux name is different.
    System.out.println("annoy loaded!");

    cppCtor(f);
    return this;
  }

  private native void cppAddItem(int i, float[] vector);

  public AnnoyIndex addItem(int i, float[] vector) {
    cppAddItem(i, vector);
    return this;
  }

  private native int[] cppGetNearestByVector(float[] distances, int n);

  public int[] getNearestByVector(float[] distances, int n) {
    return cppGetNearestByVector(distances, n);
  }

  private native int[] cppGetNearestByVectorK(float[] distances, int n, int searchK);

  public int[] getNearestByVectorK(float[] distances, int n, int searchK) {
    return cppGetNearestByVectorK(distances, n, searchK);
  }

  private native int[] cppGetNearestByItem(float[] distances, int item, int n);

  public int[] getNearestByItem(float[] distances, int item, int n) {
    return cppGetNearestByItem(distances, item, n);
  }

  private native int[] cppGetNearestByItemK(float[] distances, int item, int n, int searchK);

  public int[] getNearestByItemK(float[] distances, int item, int n, int searchK) {
    return cppGetNearestByItemK(distances, item, n, searchK);
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


  // Test Driver
  public static void main(String[] args) {
    AnnoyIndexImpl annoyIndex = new AnnoyIndexImpl();
    System.out.println("calling init");
    annoyIndex.init(100);
    System.out.println("init called!");
  }
}
