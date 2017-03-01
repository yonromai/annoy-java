package com.spotify.annoy;

import java.util.Collection;
import java.util.List;

/**
 * Annoy interface
 * Modeled after: https://github.com/spotify/annoy/blob/master/annoy/__init__.py
 */
public interface AnnoyIndex extends Cloneable {

  AnnoyIndex addItem(int i, float[] vector);

  AnnoyIndex addAllItems(Collection<List<Float>> vectors);

  int[] getNearestByVector(float[] vector, int n);

  int[] getNearestByVectorK(float[] vector, int n, int searchK);

  int[] getNearestByItem(int item, int n);

  int[] getNearestByItemK(int item, int n, int searchK);

  AnnoyIndex build(int nTrees);

  AnnoyIndex save(String filename);

  AnnoyIndex load(String filename);

  float[] getItemVector(int i);

  float getDistance(int i, int j);

  int size();
}
