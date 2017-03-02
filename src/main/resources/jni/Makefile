CC=g++
CFLAGS=-I. -lc -shared -I. -I../annoy/src -I/System/Library/Frameworks/JavaVM.framework/Headers -O3 -march=native -ffast-math
SOBJ=libannoy.jnilib
SOURCE=com_spotify_annoy_AnnoyIndexImpl.cpp
HEADER=com_spotify_annoy_AnnoyIndexImpl.h

all: annoy

annoy:
	javah -cp ../ -o ./$(HEADER) -jni com.spotify.annoy.AnnoyIndexImpl
	$(CC) $(CFLAGS) -o $(SOBJ) $(SOURCE)

