package com.spotify.annoy.jni.str;

import java.util.List;

/**
 * FIXME: to we want to make it more generic and support Bytes?
 */
public interface AnnoyStrIndex {

    List<String> getNearestByVector(List<Float> vector, int n);

    List<String> getNearestByVector(List<Float> vector, int n, int searchK);

    List<String> getNearestByItem(String item, int n);

    List<String> getNearestByItem(String item, int n, int searchK);

    List<Float> getItemVector(String item);

    float getDistance(String i, String j);

    int size();

    AnnoyStrIndex save(String filename);
}
