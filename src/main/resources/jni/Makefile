CC=g++
CFLAGS=-I. -lc -shared -I. -I../resources/annoy/src -I/System/Library/Frameworks/JavaVM.framework/Headers
SOBJ=libannoy.jnilib


all: annoy

annoy: com_spotify_annoy_AnnoyIndexImpl.cpp
	javah -cp ../../java -jni com.spotify.annoy.AnnoyIndexImpl
	$(CC) $(CFLAGS) -o $(SOBJ)


