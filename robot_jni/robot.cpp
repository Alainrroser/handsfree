#include "robot.h"

#include <windows.h>

#include <iostream>

JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_control_HandsFreeRobot_jniKeyPress(JNIEnv *, jobject, jshort keyCode) {
    INPUT inputs[1] = {};
    ZeroMemory(inputs, sizeof(inputs));
    inputs[0].type = INPUT_KEYBOARD;
    inputs[0].ki.wVk = keyCode;
    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_ch_bbcag_handsfree_control_HandsFreeRobot_jniKeyRelease(JNIEnv *, jobject, jshort keyCode) {
    INPUT inputs[1] = {};
    ZeroMemory(inputs, sizeof(inputs));
    inputs[0].type = INPUT_KEYBOARD;
    inputs[0].ki.dwFlags = KEYEVENTF_KEYUP;
    inputs[0].ki.wVk = keyCode;
    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT jchar JNICALL Java_ch_bbcag_handsfree_control_HandsFreeRobot_jniKeyCodeToChar(JNIEnv *, jobject, jshort keyCode) {
    // Allocate memory for the keyboard state
    PBYTE keyboardState = (PBYTE) malloc(sizeof(BYTE) * 256);

    // Get the keyboard state from the operating system
    GetKeyboardState(keyboardState);

    // Allocate a buffer for the result
    LPWSTR buffer = (LPWSTR) malloc(sizeof(WCHAR) * 10);

    // Convert the key code and the keyboard state to a unicode character and store it in the buffer
    ToUnicode(keyCode, NULL, keyboardState, buffer, 10, 0);

    // Get the first character of the buffer and store it in a Java character
    jchar result = buffer[0];

    // Free the previously allocated memory
    free(buffer);
    free(keyboardState);

    return result;
}