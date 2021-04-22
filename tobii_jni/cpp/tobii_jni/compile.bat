cd C:/DEV/Java/handsfree/tobii_jni/cpp/tobii_jni/
cl.exe /LD /EHsc /I "%JAVA_HOME%\include" /I "%JAVA_HOME%\include\win32" /I "." /I ".\tobii" tobii_api.cpp tobii_device.cpp tobii_jni.cpp "..\..\..\src\main\resources\lib\tobii\x64\tobii_stream_engine.lib" /o tobii_jni_stream_engine.dll
move tobii_jni_stream_engine.dll C:/DEV/Java/handsfree/src/main/resources/lib/tobii/x64/
del *.obj
del *.lib
del *.exp