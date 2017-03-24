package com.spotify.annoy.jni.str;

import java.util.List;

class AnnoyStrIndexImpl implements AnnoyStrIndex {
    AnnoyStrIndexImpl() {

    }

    @Override
    public List<String> getNearestByVector(List<Float> vector, int n) {
        return null;
    }

    @Override
    public List<String> getNearestByVector(List<Float> vector, int n, int searchK) {
        return null;
    }

    @Override
    public List<String> getNearestByItem(String item, int n) {
        return null;
    }

    @Override
    public List<String> getNearestByItem(String item, int n, int searchK) {
        return null;
    }

    @Override
    public List<Float> getItemVector(String item) {
        return null;
    }

    @Override
    public float getDistance(String i, String j) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public AnnoyStrIndex save(String filename) {
        return null;
    }

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
}
