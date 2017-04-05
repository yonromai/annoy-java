#! /bin/bash

if [ ! -f annoylib.* ]; then
    echo "[INFO] Building annoy from sources"
    curl -sS https://raw.githubusercontent.com/spotify/annoy/master/src/annoylib.h > annoylib.h
    curl -sS https://raw.githubusercontent.com/spotify/annoy/master/src/kissrandom.h > kissrandom.h
    make > /dev/null
fi


