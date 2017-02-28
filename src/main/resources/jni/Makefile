CC=g++
CFLAGS=-I. -lc -shared -I. -I../annoy/src -I/System/Library/Frameworks/JavaVM.framework/Headers
SOBJ=libannoy.jnilib
SOURCE=com_spotify_annoy_AnnoyIndexImpl.cpp

all: annoy

annoy:
	javah -cp ../ -jni com.spotify.annoy.AnnoyIndexImpl
	$(CC) $(CFLAGS) -o $(SOBJ) $(SOURCE)

