#include <iostream>
#include <com_spotify_annoy_jni_base_AnnoyIndexImpl.h>
#include <jni.h>
#include <annoylib.h>
#include <kissrandom.h>

namespace
{

    inline jintArray vec_to_jintArray(JNIEnv *env, const vector<jint> &vec)
    {
	jintArray outJNIArray = env->NewIntArray(vec.size());  // allocate
	if (NULL == outJNIArray) return NULL;
	env->SetIntArrayRegion(outJNIArray, 0 , vec.size(), &vec[0]);  // copy
	return outJNIArray;
    }

    class ANN {
	public:
	    jint f;
	    AnnoyIndexInterface<jint, jfloat>* annoy_index;
	    ANN(int f_, char metric):f(f_) {
		switch(metric) {
		    case 'a':
			annoy_index = new AnnoyIndex<jint, jfloat, Angular, Kiss64Random>(f);
			break;
		    case 'e':
			annoy_index = new AnnoyIndex<jint, jfloat, Euclidean, Kiss64Random>(f);
			break;
		}
	    }
	    ~ANN() {
		delete annoy_index;
	    }
    };
}

    JNIEXPORT jlong JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppCtor
(JNIEnv *env, jobject obj, jint jni_int, jint metric)
{
    return (intptr_t) new ANN(jni_int, metric);
}

    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSetSeed
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint seed)
{
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->set_seed(seed);
}

    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppAddItem
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint item, jfloatArray jni_floats)
{
    jfloat *inCArray = env->GetFloatArrayElements(jni_floats, NULL);
    if (NULL == inCArray) return;
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->add_item(item, inCArray);
}

    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByVector
(JNIEnv *env, jobject obj, jlong cpp_ptr, jfloatArray arr, jint n)
{
    jfloat *inCArray = env->GetFloatArrayElements(arr, NULL);
    if (NULL == inCArray) return NULL;
    size_t search_k = (size_t)-1;
    vector<jint> result;
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->get_nns_by_vector(inCArray, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByVectorK
(JNIEnv *env, jobject obj, jlong cpp_ptr, jfloatArray arr, jint n, jint search_k)
{
    jfloat *inCArray = env->GetFloatArrayElements(arr, NULL);
    if (NULL == inCArray) return NULL;
    vector<jint> result;
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->get_nns_by_vector(inCArray, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}


    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByItem
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint item, jint n)
{
    size_t search_k = (size_t)-1;
    vector<jint> result;
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->get_nns_by_item(item, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

    JNIEXPORT jintArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetNearestByItemK
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint item, jint n, jint search_k)
{
    vector<jint> result;
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->get_nns_by_item(item, n, search_k, &result, NULL);
    return vec_to_jintArray(env, result);
}

    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppBuild
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint jni_int)
{
    ANN *ann = (ANN*) cpp_ptr;
    ann->annoy_index->build(jni_int);
}

    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSave
(JNIEnv *env, jobject obj, jlong cpp_ptr, jstring jni_filename)
{
    const char *filename= env->GetStringUTFChars(jni_filename, NULL);
    if (NULL == filename) return;
    ANN *ann = (ANN*) cpp_ptr;
    bool b = ann->annoy_index->save(filename);
    env->ReleaseStringUTFChars(jni_filename, filename);  // release resources
}

    JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppLoad
(JNIEnv *env, jobject obj, jlong cpp_ptr, jstring jni_filename)
{
    const char *filename= env->GetStringUTFChars(jni_filename, NULL);
    if (NULL == filename) return;
    ANN *ann = (ANN*) cpp_ptr;
    bool b = ann->annoy_index->load(filename);
    env->ReleaseStringUTFChars(jni_filename, filename);  // release resources
}

    JNIEXPORT jfloatArray JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetItemVector
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint i)
{
    ANN *ann = (ANN*) cpp_ptr;
    vector<jfloat> vec(ann->f);
    ann->annoy_index->get_item(i, &vec[0]);
    jfloatArray outJNIArray = env->NewFloatArray(vec.size());
    if (NULL == outJNIArray) return NULL;
    env->SetFloatArrayRegion(outJNIArray, 0 , vec.size(), &vec[0]);  // copy
    return outJNIArray;
}

    JNIEXPORT jfloat JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppGetDistance
(JNIEnv *env, jobject obj, jlong cpp_ptr, jint jni_i, jint jni_j)
{
    ANN *ann = (ANN*) cpp_ptr;
    return (jfloat) ann->annoy_index->get_distance(jni_i, jni_j);
}


    JNIEXPORT jint JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppSize
(JNIEnv *env, jobject obj, jlong cpp_ptr)
{
    ANN *ann = (ANN*) cpp_ptr;
    return (jint) ann->annoy_index->get_n_items();
}

JNIEXPORT void JNICALL Java_com_spotify_annoy_jni_base_AnnoyIndexImpl_cppDtor
  (JNIEnv *env, jobject obj, jlong cpp_ptr)
{
    ANN *ann = (ANN*) cpp_ptr;
    delete ann;
}
