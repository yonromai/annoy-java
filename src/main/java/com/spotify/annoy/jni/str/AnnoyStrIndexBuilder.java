package com.spotify.annoy.jni.str;

import java.util.List;

public interface AnnoyStrIndexBuilder {

    AnnoyStrIndexBuilder addItem(List<Float> vector);

    AnnoyStrIndexBuilder addAllItems(Iterable<List<Float>> vectors);

    AnnoyStrIndexBuilder setSeed(int seed);

    AnnoyStrIndex build(String filename);
}
