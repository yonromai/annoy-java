package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.Annoy;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AnnoyStr {

    public static AnnoyStrIndexBuilder newIndex() {
        return new AnnoyStrIndexBuilderImpl();
    }

    public static AnnoyStrIndex loadIndex(String filename) throws FileNotFoundException {
        return AnnoyStrIndexBuilderImpl.loadIndex(filename);
    }

    public static void installAnnoy() throws IOException, InterruptedException {
        Annoy.install();
    }
}
