#pragma once

#include "non_copyable.h"

class TobiiAPI;
struct tobii_device_t;

class TobiiDevice : public NonCopyable
{

    public:
        TobiiDevice(TobiiAPI& api);
        ~TobiiDevice();
        tobii_device_t* get_device();

        float* get_latest_gaze_point();
        float* get_head_rotation();
		bool is_left_eye_present();
		bool is_right_eye_present();

    private:
        tobii_device_t* device;

};
