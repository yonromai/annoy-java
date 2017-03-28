package com.spotify.annoy.jni.str;

import com.google.common.io.Files;
import com.spotify.annoy.jni.base.Annoy;
import com.spotify.annoy.jni.base.AnnoyIndex;
import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyReader;
import com.spotify.sparkey.SparkeyWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class AnnoyStrIndexBuilderImpl implements AnnoyStrIndexBuilder {

    // TODO: move to conf
    private final static String annoyIndexFilename = "annoy.tree";
    private final static String idToStrIndexFilename = "idToStrIndex";
    private final static String strToIdIndexFilename = "strToIdIndex";

    // Building logic
    private final String dirName;
    private final Annoy.Builder annoyBuilder;
    private final SparkeyWriter idToStrIndexWriter;
    private final SparkeyWriter strToIdIndexWriter;
    private int itemCount = 0;

    private File getIdToStrIndexPath() {
        return Paths.get(dirName, idToStrIndexFilename).toFile();
    }

    private File getStrToIdIndexPath() {
        return Paths.get(dirName, strToIdIndexFilename).toFile();
    }

    AnnoyStrIndexBuilderImpl(String dirName, int dim) throws IOException {
        this.dirName = dirName;
        annoyBuilder = Annoy.newIndex(dim);
        idToStrIndexWriter = Sparkey.createNew(getIdToStrIndexPath());
        strToIdIndexWriter = Sparkey.createNew(getStrToIdIndexPath());
    }

    @Override
    public AnnoyStrIndexBuilder addItem(String key, List<Float> vector) throws IOException {
        Integer id = itemCount++;
        idToStrIndexWriter.put(id.toString(), key);
        strToIdIndexWriter.put(key, id.toString());
        annoyBuilder.addItem(id, vector);
        return this;
    }

    @Override
    public AnnoyStrIndexBuilder setSeed(int seed) {
        annoyBuilder.setSeed(seed);
        return this;
    }

    @Override
    public AnnoyStrIndex build(int nTrees) throws IOException {
        Path annoyPath = Paths.get(dirName, annoyIndexFilename);
        AnnoyIndex annoyIndex = annoyBuilder.build(nTrees).save(annoyPath.toString());

        idToStrIndexWriter.writeHash();
        idToStrIndexWriter.close();
        strToIdIndexWriter.writeHash();
        strToIdIndexWriter.close();

        return new AnnoyStrIndexImpl(annoyIndex,
                Sparkey.open(getIdToStrIndexPath()),
                Sparkey.open(getStrToIdIndexPath()));
    }

    // Loading logic

    static AnnoyStrIndex loadIndex(String dirName, int dim) throws IOException {
        Path annoyPath = Paths.get(dirName, annoyIndexFilename);
        AnnoyIndex annoyIndex = Annoy.loadIndex(annoyPath.toString(), dim);

        Path idToStrIndexPath = Paths.get(dirName, idToStrIndexFilename);
        SparkeyReader idToStrIndex = Sparkey.open(idToStrIndexPath.toFile());

        Path strToIdIndexPath = Paths.get(dirName, strToIdIndexFilename);
        SparkeyReader strToIdIndex = Sparkey.open(strToIdIndexPath.toFile());

        return new AnnoyStrIndexImpl(annoyIndex, idToStrIndex, strToIdIndex);
    }
}
