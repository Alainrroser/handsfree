Abschnitt | Inhalt
--- | ---
ID | ST-01
Getestete User Story | #14
Vorbedingungen | \-
Ablauf | Installer starten<br>Administrator-Berechtigungen erteilen<br>Klick auf "Next"<br>Klick auf "Next"<br>"Create a desktop shortcut" auswählen<br>"Create a start menu shortcut" auswählen<br>Klick auf "Next"<br>"Start the app" auswählen<br>Klick auf "Finish"<br>Im Startmenü nach "HandsFree" suchen<br>In Explorer zu "C:/Program Files/HandsFree" wechseln
Erwartetes Resultat | Es sollte im angegebenen Pfad einen Ordner HandsFree erstellen und darin sollte ein Jar sein, zudem sollte es einen Desktop- und einen Start Menu-Shortcut erstellt haben und die Applikation sollte gestartet werden

Abschnitt | Inhalt
--- | ---
ID | ST-02
Getestete User Story | #13
Vorbedingungen | Die Applikation ist gestartet, auf dem Desktop befindet sich ein Ordner "test" mit einer Datei "test.txt" darin, ein Mikrofon ist angeschlossen
Ablauf | Klick auf "Speech Control: OFF"<br>"desktop" sagen<br>In den Ordner "test" wechseln<br>"select all" sagen<br>"copy" sagen<br>"paste" sagen<br>"rename" sagen<br>"test2.txt" eingeben<br>"delete" sagen<br>"undo" sagen<br>"close explorer" sagen
Erwartetes Resultat | Zuerst wird auf den Desktop gewechselt, dann die Datei "test.txt" im Ordner "test" ausgewählt, dann eine Kopie erstellt, dann zu "test2.txt" umbenannt, dann gelöscht, dann wiederhergestellt und schliesslich wird der Explorer geschlossen

Abschnitt | Inhalt
--- | ---
ID | ST-03
Getestete User Story | #13
Vorbedingungen | ST-02, Google Chrome ist geöffnet
Ablauf | "switch open" sagen<br>Mit "left", "right", "down" und "up" zu Google Chrome navigieren<br>"switch close" sagen<br>"new tab" sagen<br>"Brot" eingeben<br>Enter drücken<br>"refresh" sagen<br>"close tab" sagen
Erwartetes Resultat | Mit dem Switcher wird Google Chrome geöffnet, dann wird ein neuer Tab erstellt, in diesem wird nach "Brot" gesucht, dann wird die Seite neu geladen und der Tab geschlossen

Abschnitt | Inhalt
--- | ---
ID | ST-04
Getestete User Story | #15
Vorbedingungen | Die Applikation ist gestartet, auf dem Desktop befindet sich eine Datei "test.txt"
Ablauf | Klick auf "Head Tracking: OFF"<br>Auf den Desktop wechseln<br>"test.txt" anwählen<br>Shift + Delete drücken<br>Den Kopf langsam schütteln<br>Shift + Delete erneut drücken<br>Mit dem Kopf nicken
Erwartetes Resultat | Beim Schütteln des Kopfes wird der Dialog geschlossen und die Datei nicht gelöscht, beim Nicken wird der Dialog bestätigt und die Datei gelöscht

Abschnitt | Inhalt
--- | ---
ID | ST-05
Getestete User Story | #17
Vorbedingungen | Die Applikation ist gestartet
Ablauf | Klick auf "On-Screen Keyboard: OFF"<br>Windows-Taste drücken<br>Notepad++ eingeben<br>Enter drücken<br>Per Bildschirmtastatur "Wel" eingeben<br>Klick auf "Welcome"
Erwartetes Resultat | Das Wort "Welcome" wird an zweiter Stelle vorgeschlagen und mit einem Leerschlag danach ausgeschrieben, wenn darauf geklickt wird. Nach dem Klick werden die Vorschläge geleert.

Abschnitt | Inhalt
--- | ---
ID | ST-06
Getestete User Story | #16
Vorbedingungen | Das Projekt ist in IntelliJ geöffnet
Ablauf | Rechtsklick auf "handsfree/application/src/test/"<br>Klick auf "Run 'Tests in handsfree..."
Erwartetes Resultat | Es erscheinen keine Fehlermeldungen und alle Tests werden bestanden