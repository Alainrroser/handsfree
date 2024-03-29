package ch.bbcag.handsfree.control.headtracker;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeKeyCodes;

public class HeadGestureController {

    public HeadGestureController(HandsFreeContext context) {
        context.getHeadTracker().addHeadGestureListener(new HeadGestureListener() {
            @Override
            public void nod() {
                context.getRobot().keyTypeSpecial(HandsFreeKeyCodes.ENTER);
            }

            @Override
            public void shake() {
                context.getRobot().keyTypeSpecial(HandsFreeKeyCodes.ESCAPE);
            }
        });
    }

}