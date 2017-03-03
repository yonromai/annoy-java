# annoy-java

## Run python vs. java benchmark
```
./src/test/python/benchmark_java_vs_python.py
```

## Fetch new version of annoy
```
git remote add -f annoy https://github.com/spotify/annoy.git
git fetch annoy master
git subtree pull --prefix src/main/resources/annoy annoy master --squash
```
(Then compile and push to origin if builds.)

## TODO
* Add usage instructions
* Once tested in prod, move to spotify/annoy-java
* Give a try to [faiss](https://github.com/facebookresearch/faiss)
* Find better annoy packaging than git subtree
* Play with mvn native plugins (like [this](http://maven-nar.github.io/) and [that](http://www.mojohaus.org/maven-native/native-maven-plugin/))
