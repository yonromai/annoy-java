UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Linux)
    SOBJ      :=libannoy.so
endif
ifeq ($(UNAME_S),Darwin)
    SOBJ      :=libannoy.dylib
endif

CC        :=g++
CFLAGS    :=-I. -lc -shared
JNIFLAGS  :=-I ./include
FASTFLAGS :=-O3 -march=native -ffast-math -fPIC
SOURCE    :=com_spotify_annoy_jni_base_AnnoyIndexImpl.cpp

all: annoy

annoy:
	$(CC) $(CFLAGS) $(JNIFLAGS) $(FASTFLAGS) -o $(SOBJ) $(SOURCE)

