#! /bin/bash

cd src/main/resources/jni
curl https://raw.githubusercontent.com/spotify/annoy/master/src/annoylib.h > annoylib.h
curl https://raw.githubusercontent.com/spotify/annoy/master/src/kissrandom.h > kissrandom.h

mkdir -p native/Mac/x86_64
mkdir -p native/Linux/x86_64
make
