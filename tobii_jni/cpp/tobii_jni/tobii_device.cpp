#include "tobii_device.h"
#include "tobii_api.h"
#include "tobii_headers.h"
#include <thread>
#include <chrono>
#include <iostream>

using namespace std;
using namespace std::chrono;

namespace {

    float latest_gaze_point[2] = {0.5f, 0.5f};
    float head_rotation[3] = {0.0f, 0.0f, 0.0f};
	bool left_eye_present = false;
	bool right_eye_present = false;

    string device_url = "";

    void url_receiver(const char* url, void*) {
        device_url = string(url);
    }

    void gaze_callback(const tobii_gaze_point_t* gaze_point, void*) {
        if(gaze_point->validity == TOBII_VALIDITY_VALID) {
            latest_gaze_point[0] = gaze_point->position_xy[0];
            latest_gaze_point[1] = gaze_point->position_xy[1];
        }
    }
	
	void eye_position_normalized_callback(tobii_eye_position_normalized_t const* eye_position, void*) {
		left_eye_present = eye_position->left_validity == TOBII_VALIDITY_VALID;
		right_eye_present = eye_position->right_validity == TOBII_VALIDITY_VALID;
	}

	void head_pose_callback(tobii_head_pose_t const* head_pose, void*) {
        head_rotation[0] = head_pose->rotation_xyz[0];
        head_rotation[1] = head_pose->rotation_xyz[1];
        head_rotation[2] = head_pose->rotation_xyz[2];
    }

}

TobiiDevice::TobiiDevice(TobiiAPI& api) : device(nullptr)
{
    int error = tobii_enumerate_local_device_urls(api.get_api(), url_receiver, NULL);
    if(error != TOBII_ERROR_NO_ERROR) {
        throw -2;
    }

    error = tobii_device_create(api.get_api(), device_url.c_str(), &device);
    if(error != TOBII_ERROR_NO_ERROR) {
        device = nullptr;
        throw -2;
    }

    error = tobii_gaze_point_subscribe(device, gaze_callback, NULL);
    if(error != TOBII_ERROR_NO_ERROR) {
        throw -2;
    }
	
	error = tobii_eye_position_normalized_subscribe(device, eye_position_normalized_callback, NULL);
	if(error != TOBII_ERROR_NO_ERROR) {
        throw -2;
    }

    error = tobii_head_pose_subscribe(device, head_pose_callback, NULL);
    if(error != TOBII_ERROR_NO_ERROR) {
        throw -2;
    }

    thread([this] {
        while(true) {
            tobii_wait_for_callbacks(NULL, 1, &device);
            tobii_device_process_callbacks(device);
            this_thread::sleep_for(milliseconds(16));
        }
    }).detach();
}

TobiiDevice::~TobiiDevice() {
    if(device != nullptr) {
        tobii_device_destroy(device);
    }
}

tobii_device_t* TobiiDevice::get_device() {
    return device;
}

float* TobiiDevice::get_latest_gaze_point() {
    return latest_gaze_point;
}

float* TobiiDevice::get_head_rotation() {
    return head_rotation;
}

bool TobiiDevice::is_left_eye_present() {
	return left_eye_present;
}

bool TobiiDevice::is_right_eye_present() {
	return right_eye_present;
}