#include <iostream>
#include <com_spotify_annoy_AnnoyIndexImpl.h>
#include <jni.h>
#include <annoylib.h>




JNIEXPORT void JNICALL Java_com_spotify_annoy_AnnoyIndexImpl_cppAddItem
  (JNIEnv *env, jobject jniObj, jint inJNIInt, jfloatArray inJNIFloatArray)
{

   jfloat *inCArray = env->GetFloatArrayElements(inJNIFloatArray, NULL);
   if (NULL == inCArray) return;
   jsize length = env->GetArrayLength(inJNIFloatArray);

   jint inCint = env->GetIntField(inJNIInt, NULL);


   for(int i=0; i<length; ++i) {
       float d = inCArray[i];
       printf("in C++, value of the array element####:%f\n", d);
   }

   

   AnnoyIndexInterface<int, float>* ai = new AnnoyIndex<int, float, Angular, RandRandom>(10);

   ai->build(12342);








   env->ReleaseFloatArrayElements(inJNIArray, inCArray, 0); // release resources
   return;
}
