## TODO: make build more cross platform
## take all look at: https://github.com/xerial/snappy-java/blob/master/Makefile.common

JAVA_HOME :=$(shell /usr/libexec/java_home)
JAVAH     :=$(JAVA_HOME)/bin/javah
JNIFLAGS  :=-I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/darwin -Ilib/inc_mac

CC        :=g++
CFLAGS    :=-I. -I../annoy/src -lc -shared
FASTFLAGS :=-O3 -march=native -ffast-math
SOBJ      :=libannoy.jnilib
SOURCE    :=com_spotify_annoy_AnnoyIndexImpl.cpp
HEADER    :=com_spotify_annoy_AnnoyIndexImpl.h

all: annoy

annoy:
	$(JAVAH) -cp ../ -o ./$(HEADER) -jni com.spotify.annoy.AnnoyIndexImpl
	$(CC) $(CFLAGS) $(JNIFLAGS) $(FASTFLAGS) -o $(SOBJ) $(SOURCE)

