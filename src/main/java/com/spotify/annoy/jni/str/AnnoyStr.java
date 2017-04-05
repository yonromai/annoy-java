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

import java.io.IOException;

public class AnnoyStr {

  public static AnnoyStrIndexBuilder newIndex(String dirName, int dim) throws IOException {
    return new AnnoyStrIndexBuilderImpl(dirName, dim);
  }

  public static AnnoyStrIndex loadIndex(String dirName, int dim) throws IOException {
    return AnnoyStrIndexBuilderImpl.loadIndex(dirName, dim);
  }
}
