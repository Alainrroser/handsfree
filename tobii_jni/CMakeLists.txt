cmake_minimum_required(VERSION 3.19)
project(tobii_jni_stream_engine)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS 1)

include_directories(
    $ENV{JAVA_HOME}/include
    $ENV{JAVA_HOME}/include/win32
    tobii/
)
link_directories(${CMAKE_SOURCE_DIR}/lib/)

add_library(tobii_jni_stream_engine SHARED tobii_api.cpp tobii_device.cpp tobii_jni.cpp)
target_link_libraries(tobii_jni_stream_engine tobii_stream_engine)

set_target_properties(tobii_jni_stream_engine
    PROPERTIES
    RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/../src/main/resources/lib/tobii/x64
)