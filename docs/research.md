# Recherche

## Blinzeln
Zu Beginn war der Plan, Blinzeln des Benutzers durch eine Webcam zu erkennen.
Wir wollten dafür eine Library wie OpenCV verwenden. Nach einer kurzen Recherche
fanden wir allerdings heraus, dass man mithilfe der Tobii Stream Engine API
erkennen kann, ob ein Auge sichtbar ist. Allerdings konnte der Wrapper, den wir
für die Stream API verwendeten (<https://github.com/GazePlay/TobiiStreamEngineForJava>),
nur die Blickposition auslesen. Wir mussten den Wrapper deshalb erweitern.
Dazu verwendeten wir die Dokumentation der Stream Engine
(<https://tobiitech.github.io/stream-engine-docs/>) und ein Tutorial für das
Java Native Interface (<https://www.baeldung.com/jni>). Verwendet wurde der Visual
Studio C++ Compiler (Download: <https://visualstudio.microsoft.com/de/downloads/>)