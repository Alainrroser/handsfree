cmake_minimum_required(VERSION 3.19)
project(robot_jni)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS 1)

include_directories(
        $ENV{JAVA_HOME}/include
        $ENV{JAVA_HOME}/include/win32
)

add_library(robot_jni SHARED robot.cpp robot.h)

set_target_properties(
    robot_jni
    PROPERTIES
    RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/../handsfree/src/main/resources/lib/robot
)