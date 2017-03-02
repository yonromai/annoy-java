package com.spotify.annoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Called by scripts/benchmark_java_vs_python.py
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
    AnnoyIndex index = Annoy.newIndex(dim)
        .load(annPath);

    List<Integer> queries = Files.readAllLines(Paths.get(queryFile))
        .stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());

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
    queryTime.forEach(s::addValue);

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
