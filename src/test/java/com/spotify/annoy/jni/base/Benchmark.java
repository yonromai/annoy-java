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

package com.spotify.annoy.jni.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Called by `python /src/test/python/benchmark_java_vs_python.py`
 */
public class Benchmark {

  public static void main(String[] args) throws IOException, InterruptedException {
    String annPath = args[0];
    Integer dim = Integer.parseInt(args[1]);
    String queryFile = args[2];
    Integer nnsCount = Integer.parseInt(args[3]);
    runBenchmark(annPath, dim, queryFile, nnsCount);
  }

  private static void runBenchmark(String annPath, Integer dim, String queryFile, Integer nnsCount)
      throws IOException, InterruptedException {
    AnnoyIndex index = Annoy.loadIndex(annPath, dim);

    List<Integer> queries = AnnoyTest.parseFileLinesAsInts(queryFile);

    List<Long> queryTime = new ArrayList<>(queries.size());

    for (Integer query : queries) {
      long t = System.nanoTime();
      index.getNearestByItem(query, nnsCount);
      queryTime.add(System.nanoTime() - t);
    }

    dispStats(queryTime);
  }

  private static void dispStats(List<Long> queryTime) {
    DescriptiveStatistics s = new DescriptiveStatistics();

    for (Long t : queryTime) {
      s.addValue(t);
    }

    System.out.print(new StringBuilder()
        .append(String.format("Total time:  %.5fs\n", s.getSum() / 1.e9))
        .append(String.format("Mean time:   %.5fs\n", s.getMean() / 1.e9))
        .append(String.format("Median time: %.5fs\n", s.getPercentile(.5) / 1.e9))
        .append(String.format("Stddev time: %.5fs\n", s.getStandardDeviation() / 1.e9))
        .append(String.format("Min time:    %.5fs\n", s.getMin() / 1.e9))
        .append(String.format("Max time:    %.5fs\n", s.getMax() / 1.e9))
        .toString());
  }
}
