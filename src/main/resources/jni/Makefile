## TODO: make build more cross platform
## take all look at: https://github.com/xerial/snappy-java/blob/master/Makefile.common
## and http://stackoverflow.com/a/12099167


UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Linux)
    JAVA_HOME :=/usr/lib/jvm/default-java
    JNIFLAGS  :=-I $(JAVA_HOME)/include -I ${JAVA_HOME}/include/linux
    SOBJ      :=libannoy.so
endif
ifeq ($(UNAME_S),Darwin)
    JAVA_HOME :=$(shell /usr/libexec/java_home)
    JNIFLAGS  :=-I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/darwin
    SOBJ      :=libannoy.dylib
endif

JAVAH     :=$(JAVA_HOME)/bin/javah

CC        :=g++
CFLAGS    :=-I. -I../annoy/src -lc -shared
FASTFLAGS :=-O3 -march=native -ffast-math -fPIC
SOURCE    :=com_spotify_annoy_jni_base_AnnoyIndexImpl.cpp
HEADER    :=com_spotify_annoy_jni_base_AnnoyIndexImpl.h

all: annoy

annoy:
	javah -cp .. -o ./$(HEADER) -jni com.spotify.annoy.jni.base.AnnoyIndexImpl
	$(CC) $(CFLAGS) $(JNIFLAGS) $(FASTFLAGS) -o $(SOBJ) $(SOURCE)

