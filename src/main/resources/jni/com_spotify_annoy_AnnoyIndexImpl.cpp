#include <iostream>
#include <com_spotify_annoy_AnnoyIndexImpl.h>
#include <jni.h>
#include <annoylib.h>


namespace
{
    static AnnoyIndexInterface<int, float>* annoy_index;
}

/*
 * Class:     com_spotify_annoy_AnnoyIndexImpl
 * Method:    cppCtor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spotify_annoy_AnnoyIndexImpl_cppCtor
  (JNIEnv *env, jobject obj, jint jni_int)
{
    annoy_index = new AnnoyIndex<int, float, Angular, RandRandom>(jni_int);
}

/*
 * Class:     com_spotify_annoy_AnnoyIndexImpl
 * Method:    cppAddItem
 * Signature: (I[F)V
 */
JNIEXPORT void JNICALL Java_com_spotify_annoy_AnnoyIndexImpl_cppAddItem
  (JNIEnv *env, jobject obj, jint jni_int, jfloatArray jni_floats)
{

    return;
}


/*
//TODO
JNIEXPORT void JNICALL Java_com_spotify_annoy_AnnoyIndexImpl_cppAddItem
  (JNIEnv *env, jobject jniObj, jint inJNIInt, jfloatArray inJNIFloatArray)
{

   jfloat *inCArray = env->GetFloatArrayElements(inJNIFloatArray, NULL);
   if (NULL == inCArray) return;
   jsize length = env->GetArrayLength(inJNIFloatArray);

   for(int i=0; i<length; ++i) {
       float d = inCArray[i];
       printf("in C++, value of the array element####:%f\n", d);
   }
   env->ReleaseFloatArrayElements(inJNIFloatArray, inCArray, 0); // release resources
   return;
}
*/



JNIEXPORT void JNICALL Java_com_spotify_annoy_AnnoyIndexImpl_cppBuild
(JNIEnv *env, jobject jni_obj, jint jni_int)
{
    return annoy_index->build(jni_int);
}


