package com.spotify.annoy.jni.str;

import java.io.IOException;
import java.util.List;

public interface AnnoyStrIndexBuilder {

    AnnoyStrIndexBuilder addItem(String key, List<Float> vector) throws IOException;

    AnnoyStrIndexBuilder setSeed(int seed); // FIXME: should be part of the Index interface?

    AnnoyStrIndex build(int nTrees) throws IOException;
}
