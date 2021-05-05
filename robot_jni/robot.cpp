#include "robot.h"

#include <windows.h>

#include <iostream>

JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_HandsFreeRobot_jniKeyPress(JNIEnv *, jobject, jshort keyCode) {
    INPUT inputs[1] = {};
    ZeroMemory(inputs, sizeof(inputs));
    inputs[0].type = INPUT_KEYBOARD;
    inputs[0].ki.wVk = keyCode;
    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_HandsFreeRobot_jniKeyRelease(JNIEnv *, jobject, jshort keyCode) {
    INPUT inputs[1] = {};
    ZeroMemory(inputs, sizeof(inputs));
    inputs[0].type = INPUT_KEYBOARD;
    inputs[0].ki.dwFlags = KEYEVENTF_KEYUP;
    inputs[0].ki.wVk = keyCode;
    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}