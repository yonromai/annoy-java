/*
 * -\-\-
 * annoy-java
 * --
 * Copyright (C) 2017 Spotify AB
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
  public List<String> getNearestByVector(List<Float> vector, int nbNeighbors) {
    return idToStr(annoyIndex.getNearestByVector(vector, nbNeighbors));
  }

  @Override
  public List<String> getNearestByVector(List<Float> vector, int nbNeighbors, int searchK) {
    return idToStr(annoyIndex.getNearestByVector(vector, nbNeighbors, searchK));
  }

  @Override
  public List<String> getNearestByItem(String item, int nbNeighbors) {
    return idToStr(annoyIndex.getNearestByItem(strToId(item), nbNeighbors));
  }

  @Override
  public List<String> getNearestByItem(String item, int nbNeighbors, int searchK) {
    return idToStr(annoyIndex.getNearestByItem(strToId(item), nbNeighbors, searchK));
  }

  @Override
  public List<Float> getItemVector(String item) {
    return annoyIndex.getItemVector(strToId(item));
  }

  @Override
  public float getDistance(String itemA, String itemB) {
    return annoyIndex.getDistance(strToId(itemA), strToId(itemB));
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
        String str = id.toString();
        if (str == null) {
          throw new IndexOutOfBoundsException("Key missing from Sparkey Index: " + id.toString());
        }
        strs.add(idToStrIndex.getAsString(str));
      } catch (IOException e) {
        throw new RuntimeException("Shit, ", e);
      }
    }
    return strs;
  }

  private Integer strToId(String str) {
    try {
      String idx = strToIdIndex.getAsString(str);
      if (idx == null) {
        throw new IndexOutOfBoundsException("Key missing from Sparkey Index: " + str);
      }
      return Integer.valueOf(idx);
    } catch (IOException e) {
      throw new RuntimeException("Shit, ", e);
    }
  }
}
