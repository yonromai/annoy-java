# annoy-java

## Fetch new version of annoy
```
git remote add -f annoy https://github.com/spotify/annoy.git
git fetch annoy master
git subtree pull --prefix src/main/resources/annoy annoy master --squash
```
(Then compile and push to origin if builds.)

## TODO
* Find better annoy packaging than git subtree