* Application
    * Launcher: The launcher which starts the application
    * HandsFreeApplication: The application itself which manages the scene or for example the close- or minimize requests
    * HandsFreeContext: Provides common control elements to other classes
    * Const: All the common constants
    * Tobii
        * Tobii: Gets the eye- and head tracking data from the tracker
    * Config
        * Autorun: Writes a script into the autorun folder, so that the app gets started on the start of windows
        * Configuration: Saves the toggle state of the buttons, so that they get saved when you close the app and
          reloaded when you reopen it
    * Control
        * HandsFreeKeyCodes: Constants for all the key codes to read the keyboard layout
        * HandsFreeRobot: To control the mouse and click buttons
        * ThreadedSystem: To split parts of the program from other parts, so that they both can continue running
        * EyeTracker
            * EyeTracker: The eye tracker which controls the tracking of your eyes
            * EyeMouseController: The controller which controls the mouse to where you look on the screen
            * GazeEvent: One event that the listener "records" where you can get the position of your eyes and which eyes are open or closed
            * GazeListener: The listener which tracks your eye movement 
            * RegionGazeListener: The listener which tracks if and how long you look at a specific region of the screen
        * HeadTracker
            * HeadTracker: The head tracker which tracks your head rotation
            * HeadGestureController: The controller which controls what to do when you nod or shake your head
            * HeadGestureListener: The listener which listens to if you nod or shake your head
        * Shortcuts
            * Shortcut: A list of clicks which can be executed again
            * Click: A click which can be recorded to make a shortcut
            * ShortcutManager: The manager of all the shortcuts
            * ShortcutReader: The reader which reads the shortcuts from the files
            * ShortcutRecorder: The recorder which records the clicks on the screen you make
            * ShortcutWriter: The writer which writes the shortcuts to the files
        * SpeechControl
            * SpeechControl: The speech controller which controls where all the commands get created
            * Command: A command which can be executed
            * GrammarFileWriter: The writer which writes the commands, and the shortcuts to the grammar file
            * SpeechListener: The listener which listens if you say something
            * SpeechRecognizer: The speech recognizer which recognizes what you say
    * Error
        * ErrorMessages: All the error messages
        * HandsFreeRobotException: An exception to throw when the robot doesn't work
        * KeyboardLoadingException: An exception to throw when the loading of the keyboard doesn't work
        * NativeException: An exception to throw when the loading of a native library doesn't work
        * SpeechRecognizerException: An exception to throw when the speech recognition doesn't work
    * GUI
        * HandsFreeIconifiedWidget: The iconified widget when you minimize the app
        * OnScreenKeyboard
            * HandsFreeOnScreenKeyBoard: The gui of the on-screen keyboard
            * HandsFreeOnScreenKey: The gui of a key of the on-screen keyboard
            * HandsFreeWordSuggestionPanel: The gui of the suggestion panel above the on-screen keyboard
            * VirtualKey: The logic behind a key of the on-screen keyboard
            * VirtualKeyboardLayout: The keyboard layout of the on-screen keyboard
            * VirtualKeyboardLayoutLoader: The loader which loads the keyboard layout from the file
            * VirtualKeyRow: A row of virtual keys from a keyboard-layout
    * Scenes
        * ApplicationScene: The parent scene with all the common content
        * SceneType: The list of the scenes
        * MainMenu: The main menu scene
        * CommandsList: The commands list scene
        * ShortcutMenu: The shortcut menu scene
    * Utils
        * NativeLibraryLoader: A util, so that native libraries can be loaded easier
        * TemporaryFile: A util, so that things can get written into temporary files
* Common
    * Error
        * Error: To report errors and amongst other things, write them in log files
    * GUI
        * WindowDragController: The controller which makes it possible to drag the window around
        * Colors: Constants for all the colors
        * HandsFreeFont: The font which is used in the app
        * HandsFreeCheckBox: A checkbox in the HandsFree style
        * HandsFreeLabel: A label in the HandsFreeStyle
        * HandsFreeListCell: A list cell in the HandsFreeStyle
        * HandsFreeListView: A list in the HandsFreeStyle
        * HandsFreeScene: A scene in the HandsFreeStyle
        * HandsFreeSceneConfiguration: The configuration, for example the title of a scene
        * HandsFreeScrollBar: A scrollbar in the HandsFreeStyle
        * HandsFreeScrollListener: The listener which tracks how much you scroll, so that the scroll pane knows when to move the content
        * HandsFreeScrollPane: A pane with a scrollbar and the content which should be scrollable
        * HandsFreeStageDecoration: The decoration for the stage, for example the minimize- or the close button
        * HandsFreeTextField: A text field in the HandsFreeStyle
        * Button
            * HandsFreeButton: The parent button with the common things of all the buttons
            * HandsFreeButtonPalette: The different color configurations for a button
            * HandsFreeIconButton: A button with an icon on it, for example a back button with an arrow icon on it
            * HandsFreeTextButton: A normal button in the HandsFreeStyle
            * HandsFreeToggleButton: A button which you are able to toggle on or off
        * Dialog
            * HandsFreeDialog: The parent dialog with the common things of all the dialogs
            * HandsFreeConfirmDialog: A dialog with two buttons, one to confirm and one to deny
            * HandsFreeInputDialog: A dialog with an input field
            * HandsFreeMessageDialog: A dialog with only one button, to close it, to display messages
    * Scenes
        * ISceneType: The parent scene type of all the scene types
        * Navigator: The navigator to be able to switch from one scene to another
* Installer
    * Launcher: The launcher which starts the application
    * InstallerApplication: The application itself which manages the scene or for example the close- or minimize requests
    * Const: All the common constants
    * Error
        * ErrorMessages: All the error messages
    * Scenes
        * InstallerScene: The parent scene with all the common content
        * SceneType: The list of the scenes
        * Start: The start scene
        * Shortcut: The scene where you choose if you want shortcuts
        * DirectoryChooser: The scene where you choose the directory
        * End: The end scene
* Uninstaller
    * Launcher: The launcher which starts the application
    * UninstallerApplication: The application itself which manages the scene or for example the close- or minimize requests
    * Const: All the common constants
    * Error
        * ErrorMessages: All the error messages
    * Scenes
        * UninstallerScene: The parent scene with all the common content
        * SceneType: The list of the scenes
        * Start: The start scene
        * End: The end scene