package com.spotify.annoy;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnnoyTest {

  @Test
  public void install() throws Exception {
    Annoy.install();

    AnnoyIndex annoyIndex = Annoy.newAnnoyIndex(4);
    annoyIndex.addItem(0, new float[]{1, 2, 3, 4});
  }
}