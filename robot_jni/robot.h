#include <jni.h>

#ifndef _Included_ch_bbcag_handsfree_HandsFreeRobot
#define _Included_ch_bbcag_handsfree_HandsFreeRobot

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_control_HandsFreeRobot_jniKeyPress(JNIEnv *, jobject, jshort);
JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_control_HandsFreeRobot_jniKeyRelease(JNIEnv *, jobject, jshort);

#ifdef __cplusplus
}

#endif
#endif
