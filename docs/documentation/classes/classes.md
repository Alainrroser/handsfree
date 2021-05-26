* Application
    * Tobii
        * Tobii: To get the head- and eye tracking data from the tracker
    * HandsFree
        * Launcher: To start the application
        * HandsFreeApplication: To manage the scene, for example the close- or minimize requests
        * HandsFreeContext: To provide common control elements to other classes
        * Const: All the common constants
        * Config
            * Autorun: To make the app start on the start of windows
            * Configuration: To save and read the toggle state of the buttons to and from files
        * Control
            * HandsFreeKeyCodes: Constants for all the key codes to read the keyboard layout
            * HandsFreeRobot: To control the mouse and click buttons
            * ThreadedSystem: To split parts of the program from other parts to get them continue running
            * EyeTracker
                * EyeTracker: To control the tracking of your eyes
                * EyeMouseController: To control the mouse when you move your eyes or blink
                * GazeEvent: To get the position of where your eyes look at or if they are closed or not
                * GazeListener: To track your eye movement
                * RegionGazeListener: To track how long you look at a specific region of the screen
            * HeadTracker
                * HeadTracker: To control the tracking of your head
                * HeadGestureController: To control the gestures you make with your head
                * HeadGestureListener: To track if you nod or shake your head
            * Shortcuts
                * Shortcut: A list of clicks which can be executed again
                * Click: A click which can be recorded to make a shortcut
                * ShortcutManager: To manage the shortcut
                * ShortcutReader: To read shortcuts from files
                * ShortcutRecorder: To record a shortcut
                * ShortcutWriter: To write shortcuts to files
            * SpeechControl
                * SpeechControl: To control the tracking of your voice
                * Command: A command which can be executed
                * GrammarFileWriter: To write a command, or a shortcut to the grammar file
                * SpeechListener: To listen if you say something or not
                * SpeechRecognizer: To recognize what you say
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
                * VirtualKeyboardLayout: The layout of the on-screen keyboard
                * VirtualKeyboardLayoutLoader: To load the keyboard layout from a file
                * VirtualKeyRow: A row of virtual keys from a keyboard-layout
        * Scenes
            * ApplicationScene: The parent scene with all the common content
            * SceneType: The list of the scenes
            * MainMenu: The main menu scene
            * CommandsList: The commands list scene
            * ShortcutMenu: The shortcut menu scene
        * Utils
            * NativeLibraryLoader: To load native libraries
            * TemporaryFile: To write to temporary files
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
        * HandsFreeScrollListener: To track how much you scroll
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
        * Navigator: To navigate between scenes
* Installer
    * Launcher: To start the application
    * InstallerApplication: To manage the scene, for example the close- or minimize requests
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
    * Launcher: To start the application
    * UninstallerApplication: To manage the scene, for example the close- or minimize requests
    * Const: All the common constants
    * Error
        * ErrorMessages: All the error messages
    * Scenes
        * UninstallerScene: The parent scene with all the common content
        * SceneType: The list of the scenes
        * Start: The start scene
        * End: The end scene