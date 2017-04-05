package com.spotify.annoy.jni.base;

/**
 * Called by `python /src/test/python/test_jarred_install.py`
 */
public class JarredTest {

  public static void main(String[] args) {
    Annoy.newIndex(42);
  }

}