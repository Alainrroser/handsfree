# Testkonzept Sprint 2
Abschnitt | Inhalt
--- | ---
ID | ST-01
Getestete User Story | #8
Vorbedingungen | \-
Ablauf | Klick auf "On-Screen Keyboard: OFF"<br>Klick auf "On-Screen Keyboard: ON"
Erwartetes Resultat | Das On-Screen Keyboard wird ein- und wieder ausgeblendet und beinhaltet die gleichen Tasten wie die Tastatur ausser dem Numpad

Abschnitt | Inhalt
--- | ---
ID | ST-02
Getestete User Story | #8
Vorbedingungen | ST-01
Ablauf | Auf den Bildschirm klicken<br>Einige Buchstaben und Zahlen anklicken
Erwartetes Resultat | Die geklickten Tasten werden geschrieben

Abschnitt | Inhalt
--- | ---
ID | ST-03
Getestete User Story | #8
Vorbedingungen | ST-02
Ablauf | Klick auf Shift<br>Einen Buchstaben anklicken<br>Eine Zahl anklicken
Erwartetes Resultat | Die Spezialtasten haben den gleichen Effekt wie auf einer echten Tastatur

Abschnitt | Inhalt
--- | ---
ID | ST-04
Vorbedingungen | ST-03
Ablauf | Rechtsklick auf Control<br>Rechtsklick auf Shift<br>Klick auf "Esc"
Erwartetes Resultat | Der Task-Manager öffnet sich und Control und Shift sind rot

Abschnitt | Inhalt
--- | ---
ID | ST-05
Getestete User Story | #8
Vorbedingungen | ST-03
Ablauf | Applikation öffnen<br>Klick auf "Eye Tracking: OFF"<br>Einige Tasten anstarren<br>Applikation beenden
Erwartetes Resultat | Die Tasten werden gedrückt, wenn sie lange genug angestarrt werden

Abschnitt | Inhalt
--- | ---
ID | ST-06
Getestete User Story | #5
Vorbedingungen | ST-02
Ablauf | Applikation starten<br>Klick auf "On-Screen Keyboard: OFF"<br>Klick auf "Eye Tracking: OFF"<br>Klick auf "Shortcuts"<br>Klick auf "Recording: OFF"<br>(Ab jetzt mit Augen steuern)<br>Klick auf Google Chrome Icon<br>Auf Suchleiste klicken<br>"Wikipedia" eingeben<br>Enter drücken<br>Erstes Suchresultat anklicken<br>Applikation öffnen<br>Klick auf "Recording: ON"<br>"Test" eingeben<br>Klick auf "Signed!"<br>Klick auf "Got it!"
Erwartetes Resultat | Es werden keine Fehler-Dialoge angezeigt und der Shortcut erscheint mit dem Namen "W" in der Liste

Abschnitt | Inhalt
--- | ---
ID | ST-07
Getestete User Stories | #6 #7
Vorbedingungen | ST-06
Ablauf | Applikation schliessen<br>Applikation starten<br>Klick auf "Shortcuts"
Erwartetes Resultat | In der Liste befindet sich "W" immer noch

Abschnitt | Inhalt
--- | ---
ID | ST-08
Getestete User Story | #9
Vorbedingungen | ST-07
Ablauf | Klick auf den Pfeil<br>Klick auf "On-Screen Keyboard: OFF"<br>Klick auf "Shortcuts"<br>Rechtsklick auf "Test"
Erwartetes Resultat | Der Computer führt die aufgenommenen Aktionen in den gleichen Zeitintervallen aus und landet am Ende ebenfalls auf Wikipedia

Abschnitt | Inhalt
--- | ---
ID | ST-09
Getestete User Stories | #10
Vorbedingungen | \-
Ablauf | Klick auf "Autorun"<br>Klick auf "Got it!"<br>PC neu starten
Erwartetes Resultat | Die Applikation sollte geöffnet werden

Abschnitt | Inhalt
--- | ---
ID | ST-11
Getestete User Stories | #11
Vorbedingungen | \-
Ablauf | Klick auf "Speech Control"
Erwartetes Resultat | Es sollte von "OFF" auf "ON" switchen

Abschnitt | Inhalt
--- | ---
ID | ST-12
Getestete User Stories | #12
Vorbedingungen | ST-11
Ablauf | "Test" sagen
Erwartetes Resultat | Das Shortcut Test sollte ausgeführt werden