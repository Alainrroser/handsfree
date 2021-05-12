# Technische Dokumentation
* Entwicklungsumgebung
    * Setup
    * Code Style
        * Keine Lücken zwischen Schlüsselwort und Klammer
            * if() statt 
            * if ()
        * Öffnende geschweifte Klammern auf selbe Zeile
            * while(true) {  
              } statt
            * while (true) 
              {
              }
        * Einrückung mit Leerzeichen statt mit Tabs
        * Für die Benennung verwenden wir überall camelCase ausser bei Klassen, wo wir PascalCase verwenden
            * aVariable
            * AClass
        * Alle GUI-Komponente werden mit „HandsFree“ und dem Namen der Komponenten benannt
            * HandsFreeDialog statt nur
            * Dialog
* Architektur
    * Genereller Aufbau der Applikation
        * Man hat ein Haupt-Menu wo man folgende Einstellungen treffen kann
            * Eye Tracking an- und ausschalten
            * Speech Control an- und ausschalten
            * On-Screen Keyboard an- und ausschalten
            * Autorun an- und ausschalten
            * Zum Shortcut Menu wechseln
        * Zudem gibt es ein Shortcut-Menu wo man folgendes tun kann
            * Shortcut-Aufnahme starten
            * Shortcut-Aufnahme beenden
            * Shortcut löschen
            * Shortcuts ansehen
            * Shortcuts starten
        * Man wird den Computer mithilfe von Eye Tracking steuern können
            * Blinzeln mit beiden Augen löst einen Linksklick aus
            * Blinzeln mit dem rechten Auge löst einen Rechtsklick aus
        * Man wird den Computer mithilfe von Sprachsteuerung steuern können
            * Man wird die obengenannten Shortcuts mithilfe von Sprachbefehlen starten können
            * Man wird einzelne Sprachbefehle ausführen können
        * Man wird ein On-Screen Keyboard zur Verfügung haben, welches steuern kann, indem man die Tasten ansieht  
        * Man wird die Applikation automatisch beim Start von Windows starten können
    * Klassendiagramm
* Design
    * Mockups
        ![Design](../design/design.png)
        * Das erste Fenster ist das Haupt-Menu
        * Das Zweite das Shortcut-Menu
        * Das Dritte ein Warnungs-Fenster
        * Das Vierte ein Informations-Fenster
* Verwendete APIs
    * Tobii Stream Engine SDK
        * Link: <https://developer.tobii.com/product-integration/stream-engine/>
        * Die Tobii Stream Engine SDK ermöglicht es, Daten vom Eye Tracker zu bekommen
    * Tobii Stream Engine for Java
        * Link: <https://github.com/GazePlay/TobiiStreamEngineForJava>
        * Die Tobii Stream Engine for Java ermöglicht es, den Blickpunkt im Java zu erhalten
        * Wir haben viele Modifikationen am Source Code vorgenommen, um beispielsweise auch geschlossene Augen erkennen zu können.
    * JNativeHook
        * Link: <https://github.com/kwhat/jnativehook>
        * Mit JNativeHook werden Mausklicks registriert, damit Shortcuts aufgenommen werden können