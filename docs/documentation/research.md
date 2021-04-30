# Recherche
## Blinzeln
Zu Beginn war der Plan, Blinzeln des Benutzers durch eine Webcam zu erkennen. Wir wollten dafür eine Library wie OpenCV
verwenden. Nach einer kurzen Recherche fanden wir allerdings heraus, dass man mithilfe der Tobii Stream Engine API
erkennen kann, ob ein Auge sichtbar ist. Allerdings konnte
[der Wrapper, den wir für die Stream API verwendeten](https://github.com/GazePlay/TobiiStreamEngineForJava), nur die
Blickposition auslesen. Wir mussten den Wrapper deshalb erweitern. Dazu verwendeten wir
[die Dokumentation der Stream Engine](https://tobiitech.github.io/stream-engine-docs/)
und
[ein Tutorial für das Java Native Interface](https://www.baeldung.com/jni). Verwendet wurde der Visual Studio C++
Compiler ([Download](https://visualstudio.microsoft.com/de/downloads/))
## Spracherkennung
Eine kurze Google-Suche nach "Java speech recognition" führte uns zu der
[Java Speech Api](https://www.oracle.com/java/technologies/speech-api-frequently-asked-questions.html). Diese wurde
scheinbar durch mehrere Projekte implementiert, doch wir entschieden uns für [FreeTTS](https://freetts.sourceforge.io/),
da es eine der wenigen Implementationen ist, die noch existiert. Nach der Installation wurde uns allerdings schnell
klar, dass FreeTTs zwar Sprachsynthese (Text-to-Speech) unterstützt, aber keine Spracherkennung. Im zweiten Anlauf
sind wir dann auf die Library 
[Sphinx4](https://cmusphinx.github.io/wiki/tutorialsphinx4/) gestossen, die nach dem Code im Tutorial zu urteilen sehr 
einfach zu bedienen ist. Zuerst mussten wir der Library noch eine Datei
im [JSpeech Grammar Format](https://www.w3.org/TR/2000/NOTE-jsgf-20000605/) erstellen, das alle Wörter beinhaltet, die
erkannt werden können