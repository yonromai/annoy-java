package com.spotify.annoy;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
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

//    Assert.assertArrayEquals(v2, v2, 0.001f);
  }


}