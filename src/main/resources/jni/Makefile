## TODO: make build more cross platform
## take all look at: https://github.com/xerial/snappy-java/blob/master/Makefile.common
## and http://stackoverflow.com/a/12099167


UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Linux)
	JAVA_HOME :=/usr/lib/jvm/default-java
	JNIFLAGS  :=-I $(JAVA_HOME)/include -I ${JAVA_HOME}/include/linux
endif
ifeq ($(UNAME_S),Darwin)
    JAVA_HOME :=$(shell /usr/libexec/java_home)
    JNIFLAGS  :=-I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/darwin
endif

JAVAH     :=$(JAVA_HOME)/bin/javah


CC        :=g++
CFLAGS    :=-I. -I../annoy/src -lc -shared
FASTFLAGS :=-O3 -march=native -ffast-math -fPIC -m32
SOBJ      :=libannoy.jnilib
SOURCE    :=com_spotify_annoy_jni_AnnoyIndexImpl.cpp
HEADER    :=com_spotify_annoy_jni_AnnoyIndexImpl.h

all: annoy

annoy:
	javah -cp ../../java -o ./$(HEADER) -jni com.spotify.annoy.jni.AnnoyIndexImpl
	$(CC) $(CFLAGS) $(JNIFLAGS) $(FASTFLAGS) -o $(SOBJ) $(SOURCE)

