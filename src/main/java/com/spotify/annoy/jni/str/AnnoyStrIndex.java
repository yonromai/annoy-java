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

import java.util.List;

/**
 * FIXME: to we want to make it more generic and support Bytes?.
 */
public interface AnnoyStrIndex {

  List<String> getNearestByVector(List<Float> vector, int nbNeighbors);

  List<String> getNearestByVector(List<Float> vector, int nbNeighbors, int searchK);

  List<String> getNearestByItem(String item, int nbNeighbors);

  List<String> getNearestByItem(String item, int nbNeighbors, int searchK);

  List<Float> getItemVector(String item);

  float getDistance(String itemA, String itemB);

  int size();
}
