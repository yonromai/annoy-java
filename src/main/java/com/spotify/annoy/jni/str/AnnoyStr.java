package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.Annoy;
import com.spotify.annoy.jni.base.AnnoyIndex;
import com.spotify.sparkey.SparkeyReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AnnoyStr {

    public static AnnoyStrIndexBuilder newIndex() {
        return new AnnoyStrIndexBuilderImpl();
    }

    public static AnnoyStrIndex loadIndex(String filename) throws FileNotFoundException {
        return AnnoyStrIndexBuilderImpl.loadIndex(filename);
    }

    public static AnnoyStrIndex loadIndex(String filename, int rngSeed) throws FileNotFoundException {

        AnnoyIndex annoyIndex = null;
        SparkeyReader idToStr = null;
        SparkeyReader strToId = null;
        return new AnnoyStrIndexImpl(annoyIndex, idToStr, strToId)
                .setSeed(rngSeed)
                .load(filename);
    }

    public static void installAnnoy() throws IOException, InterruptedException {
        Annoy.install();
    }
}
