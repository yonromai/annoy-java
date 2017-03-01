package com.spotify.annoy;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class AnnoyTest {

  @Test
  public void install() throws Exception {
    Annoy.install();

    List<Float> v0 = Arrays.asList(0f,1f,2f,3f);
    List<Float> v1 = Arrays.asList(4f,5f,6f,7f);

    AnnoyIndex annoyIndex = Annoy.newAnnoyIndex(4)
        .addAllItems(Arrays.asList(v0, v1))
        .build(2);

    assertThat(annoyIndex.size(), is(2));
    assertThat(toBoxed(annoyIndex.getItemVector(0)), equalTo(v0));
  }

  private static List<Float> toBoxed(float[] v) {
    return Arrays.asList(ArrayUtils.toObject(v));
  }
}