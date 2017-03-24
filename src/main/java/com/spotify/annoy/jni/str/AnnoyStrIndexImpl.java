package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.AnnoyIndex;
import com.spotify.sparkey.SparkeyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AnnoyStrIndexImpl implements AnnoyStrIndex {

    private final AnnoyIndex annoyIndex;
    private final SparkeyReader idToStrIndex;
    private final SparkeyReader strToIdIndex;

    AnnoyStrIndexImpl(final AnnoyIndex annoyIndex,
                      final SparkeyReader idToStrIndex,
                      final SparkeyReader strToIdIndex) {
        this.annoyIndex = annoyIndex;
        this.idToStrIndex = idToStrIndex;
        this.strToIdIndex = strToIdIndex;
    }

    @Override
    public List<String> getNearestByVector(List<Float> vector, int n) {
        return idToStr(annoyIndex.getNearestByVector(vector, n));
    }

    @Override
    public List<String> getNearestByVector(List<Float> vector, int n, int searchK) {
        return idToStr(annoyIndex.getNearestByVector(vector, n, searchK));
    }

    @Override
    public List<String> getNearestByItem(String item, int n) {
        return idToStr(annoyIndex.getNearestByItem(strToId(item), n));
    }

    @Override
    public List<String> getNearestByItem(String item, int n, int searchK) {
        return idToStr(annoyIndex.getNearestByItem(strToId(item), n, searchK));
    }

    @Override
    public List<Float> getItemVector(String item) {
        return annoyIndex.getItemVector(strToId(item));
    }

    @Override
    public float getDistance(String i, String j) {
        return annoyIndex.getDistance(strToId(i), strToId(j));
    }

    @Override
    public int size() {
        return annoyIndex.size();
    }

    // Building logic

    AnnoyStrIndexImpl addItem(List<Float> vector) {
        return null;
    }

    AnnoyStrIndexImpl addAllItems(Iterable<List<Float>> vectors) {
        return null;
    }

    AnnoyStrIndexImpl setSeed(int seed) {
        return null;
    }

    AnnoyStrIndexImpl build(int nTrees) {
        return null;
    }

    AnnoyStrIndexImpl load(String filename) {
        return null;
    }

    // utils

    private List<String> idToStr(List<Integer> ids) {
        ArrayList<String> strs = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            try {
                strs.add(idToStrIndex.getAsString(id.toString()));
            } catch (IOException e) {
                throw new RuntimeException("Shit, ", e);
            }
        }
        return strs;
    }

    private Integer strToId(String str) {
        try {
            return Integer.valueOf(strToIdIndex.getAsString(str));
        } catch (IOException e) {
            throw new RuntimeException("Shit, ", e);
        }
    }
}
