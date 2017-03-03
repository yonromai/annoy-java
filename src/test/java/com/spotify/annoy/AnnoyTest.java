package com.spotify.annoy;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnnoyTest {

  private static final List<Float> v0 = Arrays.asList(0f, 1f, 2f);
  private static final List<Float> v1 = Arrays.asList(3f, 4f, 5f);
  private static final List<Float> v2 = Arrays.asList(6f, 7f, 8f);
  private static final List<List<Float>> allVecs = Arrays.asList(v0, v1, v2);
  private static final float EPS = 0.0000001f;

  private static final String tmpDir = System.getProperty("java.io.tmpdir");

  @BeforeClass
  public static void installAnnoy() throws IOException, InterruptedException {
    Annoy.install();
  }

  @Test
  public void basicTest() {
    AnnoyIndex annoyIndex = Annoy.newIndex(3)
        .addAllItems(allVecs)
        .build(2);

    assertThat(annoyIndex.size(), is(3));
    assertThat(annoyIndex.getItemVector(1), equalTo(v1));
    assertThat(annoyIndex.getItemVector(0), not(v1));
  }

  @Test
  public void fileTest() {

    String filename = String.format("%stmp-%d.annoy", tmpDir, System.currentTimeMillis());

    Annoy.newIndex(3)
        .addAllItems(allVecs)
        .build(2)
        .save(filename);

    AnnoyIndex annoyIndex = Annoy.loadIndex(filename, 3);
    assertThat(annoyIndex.size(), is(3));
    assertThat(annoyIndex.getItemVector(0), equalTo(v0));
    assertThat(annoyIndex.getItemVector(0), not(v1));
  }

  @Test
  public void distanceTest() {
    AnnoyIndex annoyIndex = Annoy.newIndex(3)
        .addAllItems(allVecs)
        .build(2);

    // FIXME: CosineDistance[{0, 1, 2}, {3, 4, 5}] = 0.114562f, WTF!
    float expectedCosDistance = 0.47866955f;

    assertEquals(annoyIndex.getDistance(0, 1), expectedCosDistance, EPS);
  }


  @Test
  public void setSeedTest() {
    Annoy.newIndex(3)
        .addAllItems(allVecs)
        .setSeed(42)
        .build(2);
  }

  @Test
//  @Test(expected=IndexOutOfBoundsException.class)
  public void outOfBoundsTest() {
    AnnoyIndex annoyIndex = Annoy.newIndex(3)
        .addItem(0, v0)
        .addItem(1, v1)
        .addItem(3, v2)
        .build(2);

    List<Float> emptyVec = Arrays.asList(0f, 0f, 0f);

    // FIXME: this is terrible behavior :(
    assertThat(annoyIndex.getItemVector(2), is(emptyVec));
  }

  @Test(expected = RuntimeException.class)
  public void dimensionMismatchTest() {
    Annoy.newIndex(4)
        .addAllItems(allVecs);
  }

  @Test
  public void nearestNeighborsCountTest() {
    Iterable<List<Float>> vectors = IntStream.range(0, 100)
        .mapToObj(i -> Arrays.asList(1f * i, 2f * i, 3f * i))
        .collect(Collectors.toList());

    AnnoyIndex annoyIndex = Annoy.newIndex(3)
        .addAllItems(vectors)
        .build(5);

    int nNeighbors = 20;
    assertThat(annoyIndex.getNearestByItem(50, nNeighbors).size(), is(nNeighbors));
    assertThat(annoyIndex.getNearestByItemK(50, nNeighbors, 2 * nNeighbors).size(), is(nNeighbors));

    List<Float> queryVec = Arrays.asList(1f, 2f, 3f);
    assertThat(annoyIndex.getNearestByVector(queryVec, nNeighbors).size(), is(nNeighbors));
    assertThat(annoyIndex.getNearestByVectorK(queryVec, nNeighbors, 2 * nNeighbors).size(),
        is(nNeighbors));
  }

  @Test
  public void compareNnsToPython() throws IOException {
    int dim = 20;
    int nnsCnt = 100;
    int seed = 42;
    String expectedNnsFile = ClassLoader
        .getSystemResource(String.format("%d_nns_of_%d.txt", nnsCnt, seed))
        .getFile();

    List<Integer> expectedNns = Files.readAllLines(Paths.get(expectedNnsFile))
        .stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    String annFile = ClassLoader.getSystemResource(String.format("test_%d.ann", dim)).getFile();
    List<Integer> actualNns = Annoy.loadIndex(annFile, dim)
        .getNearestByItem(seed, nnsCnt);

    assertThat(actualNns, is(expectedNns));
  }

  @Test
  public void compareNnsToPythonWithK() throws IOException {
    int dim = 20;
    int nnsCnt = 100;
    int seed = 42;
    int k = 2;
    String expectedNnsFile = ClassLoader
        .getSystemResource(String.format("%d_nns_of_%d_k%d.txt", nnsCnt, seed, k))
        .getFile();

    List<Integer> expectedNns = Files.readAllLines(Paths.get(expectedNnsFile))
        .stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    String annFile = ClassLoader.getSystemResource(String.format("test_%d.ann", dim)).getFile();
    List<Integer> actualNns = Annoy.loadIndex(annFile, dim)
        .getNearestByItemK(seed, nnsCnt, k);

    assertThat(actualNns, is(expectedNns));
  }

  @AfterClass
  public static void rmTmpDir() {
    new File(tmpDir).delete();
  }
}