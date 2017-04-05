#include <iostream>
#include <com_spotify_annoy_jni_base_AnnoyIndexImpl.h>
#include <jni.h>
#include <annoylib.h>
#include <kissrandom.h>

namespace
{
    static AnnoyIndexInterface<jint, jfloat>* annoy_index;
    static jint f;

    inline jintArray vec_to_jintArray(JNIEnv *env, const vector<jint> &vec)
    {
	jintArray outJNIArray = env->NewIntArray(vec.size());  // allocate
	if (NULL == outJNIArray) return NULL;
	env->SetIntArrayRegion(outJNIArray, 0 , vec.size(), &vec[0]);  // copy
	return outJNIArray;
    }
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppCtor
 * Signature: (I)V
 */
    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppCtor
(JNIEnv *env, jobject obj, jint jni_int, jint metric)
{
    f = jni_int;
    switch(metric) {
    case 'a':
        annoy_index = new AnnoyIndex<jint, jfloat, Angular, Kiss64Random>(jni_int);
        break;
    case 'e':
        annoy_index = new AnnoyIndex<jint, jfloat, Euclidean, Kiss64Random>(jni_int);
        break;
    }
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppSetSeed
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSetSeed
  (JNIEnv *env, jobject obj, jint seed)
{
    annoy_index->set_seed(seed);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppAddItem
 * Signature: (I[F)V
 */
    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppAddItem
(JNIEnv *env, jobject obj, jint item, jfloatArray jni_floats)
{
    jfloat *inCArray = env->GetFloatArrayElements(jni_floats, NULL);
    if (NULL == inCArray) return;
    annoy_index->add_item(item, inCArray);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetNearestByVector
 * Signature: ([FI)[I
 */
    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByVector
(JNIEnv *env, jobject obj, jfloatArray arr, jint n)
{
    jfloat *inCArray = env->GetFloatArrayElements(arr, NULL);
    if (NULL == inCArray) return NULL;
    size_t search_k = (size_t)-1;
    vector<jint> result;
    annoy_index->get_nns_by_vector(inCArray, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetNearestByVectorK
 * Signature: ([FII)[I
 */
    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByVectorK
(JNIEnv *env, jobject obj, jfloatArray arr, jint n, jint search_k)
{
    jfloat *inCArray = env->GetFloatArrayElements(arr, NULL);
    if (NULL == inCArray) return NULL;
    vector<jint> result;
    annoy_index->get_nns_by_vector(inCArray, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}


/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetNearestByItem
 * Signature: ([FI)[I
 */
    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByItem
(JNIEnv *env, jobject obj, jint item, jint n)
{
    size_t search_k = (size_t)-1;
    vector<jint> result;
    annoy_index->get_nns_by_item(item, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetNearestByItemK
 * Signature: ([FII)[I
 */
    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByItemK
(JNIEnv *env, jobject obj, jint item, jint n, jint search_k)
{
    vector<jint> result;
    annoy_index->get_nns_by_item(item, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppBuild
 * Signature: (I)V
 */
    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppBuild
(JNIEnv *env, jobject obj, jint jni_int)
{
    return annoy_index->build(jni_int);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppSave
 * Signature: (Ljava/lang/String;)V
 */
    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSave
(JNIEnv *env, jobject obj, jstring jni_filename)
{
    const char *filename= env->GetStringUTFChars(jni_filename, NULL);
    if (NULL == filename) return;
    bool b = annoy_index->save(filename);
    env->ReleaseStringUTFChars(jni_filename, filename);  // release resources
    return;
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppLoad
 * Signature: (Ljava/lang/String;)V
 */
    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppLoad
(JNIEnv *env, jobject obj, jstring jni_filename)
{
    const char *filename= env->GetStringUTFChars(jni_filename, NULL);
    if (NULL == filename) return;
    bool b = annoy_index->load(filename);
    env->ReleaseStringUTFChars(jni_filename, filename);  // release resources
    return;
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetItemVector
 * Signature: (I)[F
 */
    JNIEXPORT jfloatArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetItemVector
(JNIEnv *env, jobject obj, jint i)
{
    vector<jfloat> vec(f);
    annoy_index->get_item(i, &vec[0]);
    jfloatArray outJNIArray = env->NewFloatArray(vec.size());
    if (NULL == outJNIArray) return NULL;
    env->SetFloatArrayRegion(outJNIArray, 0 , vec.size(), &vec[0]);  // copy
    return outJNIArray;
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppGetDistance
 * Signature: (II)F
 */
    JNIEXPORT jfloat JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetDistance
(JNIEnv *env, jobject obj, jint jni_i, jint jni_j)
{
    return (jfloat) annoy_index->get_distance(jni_i, jni_j);
}

/*
 * Class:     com_spotify_annoy_jni_base_AnnoyIndexImpl
 * Method:    cppSize
 * Signature: ()I
 */
    JNIEXPORT jint JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSize
(JNIEnv *env, jobject obj)
{
    return (jint) annoy_index->get_n_items();
}

