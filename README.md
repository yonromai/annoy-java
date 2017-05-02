# annoy-java

[![Build Status](https://img.shields.io/circleci/project/github/yonromai/annoy-java/master.svg)](https://circleci.com/gh/yonromai/annoy-java)
[![GitHub license](https://img.shields.io/github/license/yonromai/annoy-java.svg)](./LICENSE.txt)

Java JNI wrapper around [annoy (C++)](https://github.com/spotify/annoy).

## Adding to pom
```xml
<dependency>
    <groupId>com.spotify</groupId>
    <artifactId>annoy-java</artifactId>
    <version>0.4.0-SNAPSHOT</version>
</dependency>
```
At the moment, the jar is only published on Spotify's internal Artifactory.

## Getting started

```java
List<Float> v0 = Arrays.asList(0f, 1f, 2f);
List<Float> v1 = Arrays.asList(3f, 4f, 5f);
List<List<Float>> allVecs = Arrays.asList(v0, v1);

String filename = String.format("./dummy.annoy");

Annoy.newIndex(3)
    .addAllItems(allVecs)
    .build(2)
    .save(filename);

AnnoyIndex annoyIndex = Annoy.loadIndex(filename, 3);

annoyIndex.getNearestByItem(1, 42);
```
For more examples, take a look at the unit tests.

## Run python vs. java benchmark
```
./src/test/python/benchmark_java_vs_python.py
```

## TODO
* Once tested in prod, move to spotify/annoy-java
* Give a try to [faiss](https://github.com/facebookresearch/faiss)

# License

Copyright 2017 Spotify AB.

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0