package com.spotify.annoy;

/**
 * Annoy interface
 * Modeled after: https://github.com/spotify/annoy/blob/master/annoy/__init__.py
 */
public interface AnnoyIndex extends Cloneable {

  AnnoyIndex addItem(int i, float[] vector);

  int[] getNearestByVector(float[] distances, int n);
  int[] getNearestByVectorK(float[] distances, int n, int searchK);

  int[] getNearestByItem(float[] distances, int item, int n);
  int[] getNearestByItemK(float[] distances, int item, int n, int searchK);

  AnnoyIndex build(int nTrees);

  AnnoyIndex save(String filename);

  AnnoyIndex load(String filename);

  float[] getItemVector(int i);

  float getDistance(int i, int j);

  int size();
}
