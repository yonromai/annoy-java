package com.spotify.annoy.jni.str;

import com.spotify.annoy.jni.base.Annoy;

import java.io.IOException;

public class AnnoyStr {

    public static AnnoyStrIndexBuilder newIndex(String dirName, int dim) throws IOException {
        return new AnnoyStrIndexBuilderImpl(dirName, dim);
    }

    public static AnnoyStrIndex loadIndex(String dirName, int dim) throws IOException {
        return AnnoyStrIndexBuilderImpl.loadIndex(dirName, dim);
    }

    public static void installAnnoy() throws IOException, InterruptedException {
        Annoy.install();
    }
}
