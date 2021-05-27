* Application
    * Tobii
        * Tobii: To get the head- and eye tracking data from the tracker. Uses the JNI to interface with the Tobii Stream Engine API
    * HandsFree
        * Launcher: To start the application
        * HandsFreeApplication: To manage the scene, for example the close- or minimize requests
        * HandsFreeContext: To provide common control elements to other classes
        * ApplicationConstants: All the common constants from the application
        * Config
            * Autorun: To make the app start on the start of Windows
            * Configuration: To save and read the state of the buttons to and from files
        * Control
            * HandsFreeKeyCodes: Constants for all the key codes that the robot and the virtual keyboard use
            * HandsFreeRobot: To control the mouse and keyboard and get low-level information about the keyboard
            * ThreadedSystem: To move systems into separate threads that would block the main thread
            * EyeTracker
                * EyeTracker: To convert the eye tracking data from the Tobii class into a more useful form
                * EyeMouseController: To control the mouse when you move your eyes or blink
                * GazeEvent: Contains the position of where your eyes look at and if they are closed or not
                * GazeListener: To get data from an EyeTracker
                * RegionGazeListener: To fire an event when you look on a specific region for long enough
            * HeadTracker
                * HeadTracker: To convert the head tracking data from the Tobii class into a more useful form
                * HeadGestureController: To fire events when you nod with your head or shake it
                * HeadGestureListener: To get notified when the user nods or shakes its head
            * Shortcuts
                * Shortcut: A list of timed clicks that can be executed again using a robot
                * Click: A click that contains information about its position, button and time offset
                * ShortcutManager: To record, run, write and read shortcuts
                * ShortcutReader: To read shortcuts from files
                * ShortcutRecorder: To record a shortcut
                * ShortcutWriter: To write shortcuts to files
            * SpeechControl
                * SpeechControl: To manage the Sphinx API to get live speech results
                * Command: A command with a name and description that can be executed using a robot
                * GrammarFileWriter: To write a command, or a shortcut to a grammar file that the Sphinx API then uses
                * SpeechListener: To get notified when the user says a certain text
                * SpeechRecognizer: To run shortcuts or commands when the user says their name
        * Error
            * ApplicationErrorMessages: Constants for all the error messages
            * HandsFreeRobotException: An exception to throw when the robot doesn't work
            * KeyboardLoadingException: An exception to throw when the loading of the keyboard doesn't work
            * NativeException: An exception to throw when the loading of a native library doesn't work
            * SpeechRecognizerException: An exception to throw when the speech recognition doesn't work
        * GUI
            * HandsFreeIconifiedWidget: The widget that appears when you minimize the application. Can be clicked on to repoen the app.
            * OnScreenKeyboard
                * HandsFreeOnScreenKeyBoard: The GUI of the on-screen keyboard
                * HandsFreeOnScreenKey: The GUI of a key of the on-screen keyboard
                * HandsFreeWordSuggestionPanel: The GUI of the word suggestion panel above the on-screen keyboard
                * VirtualKey: Contains all the logic about a virtual key like the key code, the size or the display text
                * VirtualKeyboardLayout: Contains the name of a virtual keyboard layout and all the key rows
                * VirtualKeyboardLayoutLoader: To load the keyboard layout from a file
                * VirtualKeyRow: A row of virtual keys
                * WordSuggestions: To get the best word suggestions for the word the user is currently typing
        * Scenes
            * ApplicationScene: The parent of all other scenes in the application
            * SceneType: An enum containing all the scene types
            * MainMenu: The main menu scene
            * CommandsList: The commands list scene
            * ShortcutMenu: The shortcut menu scene
        * Utils
            * NativeLibraryLoader: Loads DLLs for native libraries (used by the Tobii JNI and Robot JNI)
            * TemporaryFile: To create temporary files
* Common
    * Constants: All the common constants for the subprojects
    * Error
        * Error: To report errors and write them into log files
		* ErrorMessages: All the common error messages for the subprojects
	* GUI
        * WindowDragController: Can be attached to a window to make it draggable
        * HandsFreeColors: Constants for all the colors
        * HandsFreeFont: Generates fonts of a certain size and with the HandsFree font family
        * HandsFreeCheckBox: A checkbox in the HandsFree style
        * HandsFreeLabel: A label in the HandsFree style
        * HandsFreeListCell: A list cell in the HandsFree style
        * HandsFreeListView: A list in the HandsFree style
        * HandsFreeViewClickListener: To track a click on a cell in a list
        * HandsFreeScene: A scene in the HandsFree style
        * HandsFreeSceneConfiguration: The style configuration for a scene (e.g. does it have a minimize button?)
        * HandsFreeScrollBar: A scrollbar in the HandsFree style
        * HandsFreeScrollListener: To track when and how much the user scrolls
        * HandsFreeScrollPane: A pane with a scrollbar. The content is moved by the scrollbar
        * HandsFreeStageDecoration: The decoration for the stage, for example the minimize- or the close button
        * HandsFreeTextField: A text field in the HandsFree style
        * Button
            * HandsFreeButton: The parent of all buttons in the HandsFree style
            * HandsFreeButtonPalette: The color configuration for a button
            * HandsFreeIconButton: A button with an icon on it (e.g. a back button with an arrow icon)
            * HandsFreeTextButton: A button with text on it
            * HandsFreeToggleButton: A button which you are able to toggle on or off
        * Dialog
            * HandsFreeDialog: The parent of all dialogs in the HandsFree style
            * HandsFreeConfirmDialog: A dialog with a message, two buttons, one to confirm and one to deny
            * HandsFreeInputDialog: A dialog with a message, an input field, a confirm button and a cancel button
            * HandsFreeInputListener: To track if the user has finished typing in an input dialog
            * HandsFreeMessageDialog: A dialog with a messsge and a button to close it
    * Scenes
        * ISceneType: The interface that all scene types need to implement
        * Navigator: To navigate between scenes
* Installer
    * Launcher: To start the installer application
    * InstallerApplication: To setup the scenes and run the installation configuration
    * InstallerConstants: All the common constants for the installer
    * Error
        * InstallerErrorMessages: All the error messages
    * Scenes
        * InstallerScene: The parent of all other scenes in the installer
        * SceneType: An enum containing all the scene types
        * Start: The start scene
        * Shortcut: The scene where you choose if you want shortcuts
        * DirectoryChooser: The scene where you choose the directory
        * End: The end scene
* Uninstaller
    * Launcher: To start the uninstaller application
    * UninstallerApplication: To setup the scenes and uninstall the HandsFree application
    * UninstallerConstants: All the common constants for the uninstaller
    * Error
        * UninstallerErrorMessages: All the error messages
    * Scenes
        * UninstallerScene: The parent of all other scenes in the uninstaller
        * SceneType: An enum containing all the scene types
        * Start: The start scene
        * End: The end scene