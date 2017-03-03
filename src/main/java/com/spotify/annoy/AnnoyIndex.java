package com.spotify.annoy;

import java.util.List;

/**
 * Annoy interface
 * Modeled after: https://github.com/spotify/annoy/blob/master/annoy/__init__.py
 */
public interface AnnoyIndex extends Cloneable {

  List<Integer> getNearestByVector(List<Float> vector, int n);

  List<Integer> getNearestByVectorK(List<Float> vector, int n, int searchK);

  List<Integer> getNearestByItem(int item, int n);

  List<Integer> getNearestByItemK(int item, int n, int searchK);

  List<Float> getItemVector(int i);

  float getDistance(int i, int j);

  int size();

  AnnoyIndex save(String filename);
}
