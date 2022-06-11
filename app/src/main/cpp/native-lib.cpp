
#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_thuypham_ptithcm_baseapp_util_ApiHelper_baseMovieV3Url(JNIEnv *env, jclass clazz) {
    std::string baseURL = "https://api.themoviedb.org/3/";
    return env->NewStringUTF(baseURL.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_thuypham_ptithcm_baseapp_util_ApiHelper_baseImageV3Url(JNIEnv *env, jclass clazz) {
    std::string baseImageUrl = "https://image.tmdb.org/t/p/w500%s";
    return env->NewStringUTF(baseImageUrl.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_thuypham_ptithcm_baseapp_util_ApiHelper_movieApiKey(JNIEnv *env, jclass clazz) {
    std::string apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    return env->NewStringUTF(apiKey.c_str());
}