# Recherche
## Blinzeln
Zu Beginn war der Plan, Blinzeln des Benutzers durch eine Webcam zu erkennen. Wir wollten dafür eine Library wie OpenCV
verwenden. Nach einer kurzen Recherche fanden wir allerdings heraus, dass man mithilfe der Tobii Stream Engine API
erkennen kann, ob ein Auge sichtbar ist. Allerdings konnte der Wrapper, den wir für die
[Stream API](https://github.com/GazePlay/TobiiStreamEngineForJava) verwendeten, nur die Blickposition auslesen. Wir
mussten den Wrapper deshalb erweitern. Dazu verwendeten wir die Dokumentation der
[Stream Engine](https://tobiitech.github.io/stream-engine-docs/) und ein
[Tutorial für das Java Native Interface](https://www.baeldung.com/jni). Verwendet wurde der Visual Studio C++
Compiler ([Download](https://visualstudio.microsoft.com/de/downloads/))
## Sprach-Erkennung
Eine kurze Google-Suche nach „Java speech recognition“ führte uns zu der
[Java Speech Api](https://www.oracle.com/java/technologies/speech-api-frequently-asked-questions.html). Diese wurde
scheinbar durch mehrere Projekte implementiert, doch wir entschieden uns für [FreeTTS](https://freetts.sourceforge.io/),
da es eine der wenigen Implementationen ist, die noch existiert. Nach der Installation wurde uns allerdings schnell
klar, dass FreeTTs zwar Sprachsynthese (Text-to-Speech) unterstützt, aber keine Spracherkennung. Im zweiten Anlauf sind
wir dann auf die Library
[Sphinx4](https://cmusphinx.github.io/wiki/tutorialsphinx4/) gestossen, die nach dem Code im Tutorial zu urteilen sehr
einfach zu bedienen ist. Zuerst mussten wir der Library noch eine Datei
im [JSpeech Grammar Format](https://www.w3.org/TR/2000/NOTE-jsgf-20000605/) erstellen, das alle Wörter beinhaltet, die
erkannt werden können.
## Mausklick-Erkennung
Nachdem wir eingeführt hatten, dass man Shortcuts aufnehmen kann, merkten wir, dass es nur Möglich war, Klicks zu
registrieren, welche mithilfe der Augen ausgeführt wurden. Nach einiger Zeit in welcher wir im Internet nach Wegen
gesucht haben, umzusetzen, dass es auch Möglich ist, normale Mausklicks zu registrieren, sind wir auf die Library
[JNativeHook](https://github.com/kwhat/jnativehook) gestossen. So konnten wir dann mithilfe eines NativeMouseListeners
festlegen, dass auch ein normaler Mausklick registriert wird.
## GUI Testing
Von unserem Coach haben wir von [TestFX](https://github.com/TestFX/TestFX) erfahren. Dies ist eine Library, mit welcher
man GUI Tests durchführen kann.
## Installer
Wir hatten von Anfang an die Idee, einen Installer zu machen. Dies taten wir dann auch mithilfe von JavaFX. Damit man
aber die Applikation an Pfaden installieren kann, für die man Admin-Rechte braucht, haben wir nach einiger Recherche die
Library [Launch4J](https://github.com/TheBoegl/gradle-launch4j) gefunden, welche eine .exe Datei macht, welche
Administrator-Rechte verlangt.
## Shortcuts
Im Installer wollten wir zusätzlich die Funktion einbauen, ein Desktop-, sowie ein Start Menu Shortcut zu erstellen.
Nach kurzer Recherche fanden wir die Library [MSLinks](https://github.com/vatbub/mslinks), welche genau dies ermöglicht.
## Wörterliste
Als zusätzliches Feature wollten wir noch einbauen, dass bei unserer Bildschirmtastatur je nach Eingabe, Vorschläge
gemacht werden, was man vielleicht schreiben will. Dies haben wir dann mithilfe dieser
[Wörterliste](https://github.com/first20hours/google-10000-english/blob/master/google-10000-english.txt) erreicht. Die
Wörter sind nach Häufigkeit sortiert, sodass das immer die wahrscheinlichsten Wörter angezeigt werden.