package com.spotify.annoy;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class AnnoyTest {

  private static final List<Float> v0 = Arrays.asList(0f,1f,2f);
  private static final List<Float> v1 = Arrays.asList(3f,4f,5f);
  private static final List<Float> v2 = Arrays.asList(6f,7f,8f);
  private static final List<List<Float>> allVecs = Arrays.asList(v0, v1, v2);

  @Test
  public void basicTest() throws Exception {
    Annoy.install();



    AnnoyIndex annoyIndex = Annoy.newAnnoyIndex(3)
        .addAllItems(allVecs)
        .build(2);

    assertThat(annoyIndex.size(), is(3));
    assertThat(annoyIndex.getItemVector(1), equalTo(v1));
    assertThat(annoyIndex.getItemVector(0), not(v1));
  }

  @Test
  public void fileTest() throws Exception {
    Annoy.install();

    String tmpDir = System.getProperty("java.io.tmpdir");
    String filename = String.format("%stmp-%d.annoy", tmpDir, System.currentTimeMillis());

    Annoy.newAnnoyIndex(3)
        .addAllItems(allVecs)
        .build(2)
        .save(filename);

    AnnoyIndex annoyIndex = Annoy.newAnnoyIndex(3)
        .load(filename);

    assertThat(annoyIndex.size(), is(3));
    assertThat(annoyIndex.getItemVector(0), equalTo(v0));
    assertThat(annoyIndex.getItemVector(0), not(v1));
  }
}