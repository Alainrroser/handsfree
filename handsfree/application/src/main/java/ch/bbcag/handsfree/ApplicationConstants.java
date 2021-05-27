package ch.bbcag.handsfree;

import javafx.geometry.Insets;

public class ApplicationConstants extends Constants {
    public static final double WIDTH = 900;
    public static final double HEIGHT = 600;

    public static final int V_BOX_SPACING = 20;

    private static final int V_BOX_PADDING_TOP_BOTTOM = 50;
    private static final int V_BOX_PADDING_RIGHT_LEFT = 100;
    public static final Insets PADDING = new Insets(ApplicationConstants.V_BOX_PADDING_TOP_BOTTOM, ApplicationConstants.V_BOX_PADDING_RIGHT_LEFT,
                                                    ApplicationConstants.V_BOX_PADDING_TOP_BOTTOM,
                                                    ApplicationConstants.V_BOX_PADDING_RIGHT_LEFT);

    public static final String EYE_TRACKING_STATE = "Eye_Tracking_State";
    public static final String HEAD_TRACKING_STATE = "Head_Tracking_State";
    public static final String SPEECH_CONTROL_STATE = "Speech_Control_State";
    public static final String AUTORUN_STATE = "Autorun_State";
}
