package com.spotify.annoy.jni.str;

import java.io.FileNotFoundException;
import java.util.List;

public class AnnoyStr {

    public static class Builder {

        private final AnnoyStrIndexImpl ann;

        private Builder() {
            ann = new AnnoyStrIndexImpl();
        }

        public AnnoyStr.Builder addItem(List<Float> vector) {
            ann.addItem(vector);
            return this;
        }

        public AnnoyStr.Builder addAllItems(Iterable<List<Float>> vectors) {
            ann.addAllItems(vectors);
            return this;
        }

        public AnnoyStr.Builder setSeed(int seed) {
            ann.setSeed(seed);
            return this;
        }

        public AnnoyStrIndex build(int nTrees) {
            return ann.build(nTrees);
        }
    }

    public static AnnoyStr.Builder newIndex() {
        return new AnnoyStr.Builder();
    }

    public static AnnoyStrIndex loadIndex(String filename) throws FileNotFoundException {
        return loadIndex(filename, 42);
    }

    public static AnnoyStrIndex loadIndex(String filename, int rngSeed) throws FileNotFoundException {
        return new AnnoyStrIndexImpl()
                .setSeed(rngSeed)
                .load(filename);
    }
}
