# Testkonzept Sprint 2
Abschnitt | Inhalt
--- | ---
ID | ST-01
Getestete User Story | #8
Vorbedingungen | \-
Ablauf | Jar starten<br>Klick auf "On-Screen Keyboard: OFF"
Erwartetes Resultat | Das On-Screen Keyboard wird eingeblendet und beinhaltet die gleichen Tasten wie die Tastatur ausser dem Numpad und statt der Applikation wird ein Floating Widget in der unteren rechten Ecke angezeigt

Abschnitt | Inhalt
--- | ---
ID | ST-02
Getestete User Story | #8
Vorbedingungen | ST-01
Ablauf | An einen beschreibbaren Ort auf dem Bildschirm klicken<br>Einige Buchstaben und Zahlen anklicken
Erwartetes Resultat | Die geklickten Tasten werden geschrieben

Abschnitt | Inhalt
--- | ---
ID | ST-03
Getestete User Story | #8
Vorbedingungen | ST-02
Ablauf | Klick auf Shift<br>Einen Buchstaben anklicken<br> Klick auf Shift<br>Eine Zahl anklicken
Erwartetes Resultat | Die Spezialtasten haben denselben Effekt wie auf einer echten Tastatur

Abschnitt | Inhalt
--- | ---
ID | ST-04
Getestete User Story | #8
Vorbedingungen | ST-03
Ablauf | Rechtsklick auf Control<br>Rechtsklick auf Shift<br>Klick auf "Esc"<br>Klick auf Control<br>Klick auf Shift<br>Taskmanager schliessen<br>Klick auf das Floating Widget
Erwartetes Resultat | Der Task-Manager öffnet sich und Control und Shift werden rot nachdem sie mit einem Rechtsklick angeklickt wurden und zudem wird das Fenster wieder angezeigt

Abschnitt | Inhalt
--- | ---
ID | ST-05
Getestete User Story | #8
Vorbedingungen | ST-03
Ablauf | Bildschirmtastatur verschieben<br>Klick auf "Eye Tracking: OFF"<br>An einen beschreibbaren Ort auf dem Bildschirm klicken<br>Einige Tasten anstarren
Erwartetes Resultat | Die Tasten werden gedrückt, wenn sie lange genug angestarrt werden

Abschnitt | Inhalt
--- | ---
ID | ST-06
Getestete User Story | #5
Vorbedingungen | ST-02
Ablauf | Bildschirmtastatur verschieben<br>Klick auf "Shortcuts"<br>Bildschirmtastatur verschieben<br>Klick auf "Recording: OFF"<br>(Ab jetzt mit Augen steuern)<br>Klick auf Google Chrome Icon<br>Auf Suchleiste klicken<br>"Wikipedia" eingeben<br>Enter drücken<br>Erstes Suchresultat anklicken<br>Applikation öffnen<br>Bildschirmtastatur verschieben<br>Klick auf "Recording: ON"<br>"Test" eingeben<br>Bildschirmtastatur verschieben<br>Klick auf "Ship it!"<br>Bildschirmtastatur verschieben<br>Klick auf "Got it!"
Erwartetes Resultat | Es werden keine Fehler-Dialoge angezeigt und der Shortcut erscheint mit dem Namen "Test" in der Liste

Abschnitt | Inhalt
--- | ---
ID | ST-07
Getestete User Stories | #6 #7
Vorbedingungen | ST-06
Ablauf | Jar schliessen<br>Jar starten<br>Chrome schliessen<br>Klick auf "Shortcuts"
Erwartetes Resultat | In der Liste befindet sich "Test" immer noch

Abschnitt | Inhalt
--- | ---
ID | ST-08
Getestete User Story | #9
Vorbedingungen | ST-07
Ablauf | Klick auf den Pfeil<br>Klick auf "On-Screen Keyboard: OFF"<br>Bildschirmtastatur verschieben<br>Klick auf "Shortcuts"<br>Bildschirmtastatur verschieben<br>Rechtsklick auf "Test"
Erwartetes Resultat | Der Computer führt die aufgenommenen Aktionen in den gleichen Zeitintervallen aus und landet am Ende ebenfalls auf Wikipedia

Abschnitt | Inhalt
--- | ---
ID | ST-09
Getestete User Stories | #10
Vorbedingungen | \-
Ablauf | Bildschirmtastatur verschieben<br>Klick auf den Pfeil<br>Bildschirmtastatur verschieben<br>Klick auf "Autorun"<br>Klick auf "Got it!"<br>Klick auf Windows Taste<br>Klick auf On-/Off-Symbol<br>Klick auf "Neu Starten"<br>Klick auf "Trotzdem herunterfahren"
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