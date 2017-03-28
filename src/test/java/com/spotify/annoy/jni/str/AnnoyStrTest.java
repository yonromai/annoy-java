package com.spotify.annoy.jni.str;

import com.google.common.io.Files;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AnnoyStrTest {

    private static final String k0 = "k0";
    private static final String k1 = "k1";
    private static final String k2 = "k2";
    private static final List<Float> v0 = Arrays.asList(0f, 1f, 2f);
    private static final List<Float> v1 = Arrays.asList(3f, 4f, 5f);
    private static final List<Float> v2 = Arrays.asList(6f, 7f, 8f);
    private static final float EPS = 0.0000001f;
    private static final String tempDir = Files.createTempDir().toString();


    @BeforeClass
    public static void installAnnoyStr() throws IOException, InterruptedException {
        AnnoyStr.installAnnoy();
    }

    @Test
    public void basicTest() throws IOException {

        AnnoyStrIndex AnnoyStrIndex = getDefaultAnnoyIndex();

        assertThat(AnnoyStrIndex.size(), is(3));
        assertThat(AnnoyStrIndex.getItemVector(k1), equalTo(v1));
        assertThat(AnnoyStrIndex.getItemVector(k0), not(v1));
    }

    @Test
    public void fileTest() throws IOException {
        getDefaultAnnoyIndex();

        AnnoyStrIndex annoyStrIndex = AnnoyStr.loadIndex(tempDir, 3);
        assertThat(annoyStrIndex.size(), is(3));
        assertThat(annoyStrIndex.getItemVector(k0), equalTo(v0));
        assertThat(annoyStrIndex.getItemVector(k0), not(v1));
    }

    @Test
    public void distanceTest() throws IOException {
        AnnoyStrIndex AnnoyStrIndex = getDefaultAnnoyIndex();

        // FIXME: CosineDistance[{0, 1, 2}, {3, 4, 5}] = 0.114562f, WTF!
        float expectedCosDistance = 0.47866955f;

        assertEquals(AnnoyStrIndex.getDistance(k0, k1), expectedCosDistance, EPS);
    }


    @Test
    public void setSeedTest() throws IOException {
        AnnoyStr.newIndex(tempDir, 3)
                .addItem(k0, v0)
                .addItem(k1, v1)
                .addItem(k2, v2)
                .setSeed(42)
                .build(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOfBoundsTest() throws IOException {
        AnnoyStrIndex annoyStrIndex = AnnoyStr.newIndex(tempDir, 3)
                .addItem(k0, v0)
                .addItem(k2, v2)
                .build(2);

        annoyStrIndex.getItemVector(k1);
    }

    @Test(expected = RuntimeException.class)
    public void dimensionMismatchTest() throws IOException {
        AnnoyStr.newIndex(tempDir, 4)
                .addItem(k0, v0);
    }

    @Test(expected = FileNotFoundException.class)
    public void fileDoesNotExistTest() throws IOException {
        AnnoyStr.loadIndex("FILE_DOES_NOT_EXIST", 42);
    }

    private static AnnoyStrIndex getDefaultAnnoyIndex() throws IOException {
        return AnnoyStr.newIndex(tempDir, 3)
                .addItem(k0, v0)
                .addItem(k1, v1)
                .addItem(k2, v2)
                .build(2);
    }

    @AfterClass
    public static void rmTempDir() {
        Paths.get(tempDir).toFile().delete();
    }
}
