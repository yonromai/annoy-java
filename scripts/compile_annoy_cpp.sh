#! /bin/bash

if [ ! -f annoylib.* ]; then
    echo "[INFO] Building Annoy from sources"
    curl -sSO https://raw.githubusercontent.com/spotify/annoy/master/src/annoylib.h
    curl -sSO https://raw.githubusercontent.com/spotify/annoy/master/src/kissrandom.h
    make > /dev/null
fi


