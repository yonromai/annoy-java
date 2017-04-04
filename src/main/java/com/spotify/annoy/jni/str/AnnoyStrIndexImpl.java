package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.AnnoyIndex;
import com.spotify.sparkey.SparkeyReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AnnoyStrIndexImpl implements AnnoyStrIndex {

  private final AnnoyIndex annoyIndex;
  private final SparkeyReader idToStrIndex;
  private final SparkeyReader strToIdIndex;

  AnnoyStrIndexImpl(final AnnoyIndex annoyIndex,
                    final SparkeyReader idToStrIndex,
                    final SparkeyReader strToIdIndex) {
    this.annoyIndex = annoyIndex;
    this.idToStrIndex = idToStrIndex;
    this.strToIdIndex = strToIdIndex;
  }

  @Override
  public List<String> getNearestByVector(List<Float> vector, int n) {
    return idToStr(annoyIndex.getNearestByVector(vector, n));
  }

  @Override
  public List<String> getNearestByVector(List<Float> vector, int n, int searchK) {
    return idToStr(annoyIndex.getNearestByVector(vector, n, searchK));
  }

  @Override
  public List<String> getNearestByItem(String item, int n) {
    return idToStr(annoyIndex.getNearestByItem(strToId(item), n));
  }

  @Override
  public List<String> getNearestByItem(String item, int n, int searchK) {
    return idToStr(annoyIndex.getNearestByItem(strToId(item), n, searchK));
  }

  @Override
  public List<Float> getItemVector(String item) {
    return annoyIndex.getItemVector(strToId(item));
  }

  @Override
  public float getDistance(String i, String j) {
    return annoyIndex.getDistance(strToId(i), strToId(j));
  }

  @Override
  public int size() {
    return annoyIndex.size();
  }

  // utils

  private List<String> idToStr(List<Integer> ids) {
    ArrayList<String> strs = new ArrayList<>(ids.size());
    for (Integer id : ids) {
      try {
        String s = id.toString();
        if (s == null) {
          throw new IndexOutOfBoundsException("Key missing from Sparkey Index: " + id.toString());
        }
        strs.add(idToStrIndex.getAsString(s));
      } catch (IOException e) {
        throw new RuntimeException("Shit, ", e);
      }
    }
    return strs;
  }

  private Integer strToId(String str) {
    try {
      String s = strToIdIndex.getAsString(str);
      if (s == null) {
        throw new IndexOutOfBoundsException("Key missing from Sparkey Index: " + str);
      }
      return Integer.valueOf(s);
    } catch (IOException e) {
      throw new RuntimeException("Shit, ", e);
    }
  }
}
