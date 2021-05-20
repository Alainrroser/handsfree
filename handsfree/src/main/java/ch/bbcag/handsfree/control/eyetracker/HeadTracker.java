package ch.bbcag.handsfree.control.eyetracker;

import tobii.Tobii;

import java.util.ArrayList;
import java.util.List;

public class HeadTracker {

    enum HeadShakeStage {
        NOT_SHAKING, ONE_SIDE_ACTIVATED, TWO_SIDES_ACTIVATED
    }

    private static final float NOD_DOWN_THRESHOLD_ANGLE = -15;
    private static final float NOD_UP_THRESHOLD_ANGLE = 0;
    private static final float NOD_RESET_THRESHOLD_ANGLE = 5;
    private static final long NOD_EXPIRE_TIME = 1000;

    private static final float SHAKE_THRESHOLD_ANGLE = 15;
    private static final float SHAKE_RESET_THRESHOLD_ANGLE = 5;
    private static final long SHAKE_EXPIRE_TIME = 2000;


    private List<HeadGestureListener> headGestureListeners = new ArrayList<>();

    private boolean nodDown = false;
    private long nodExpireTimestamp = 0;
    private boolean waitingForNodReset = false;

    private HeadShakeStage shakeStage = HeadShakeStage.NOT_SHAKING;
    private float shakeSign = 0;
    private long shakeExpireTimestamp = 0;
    private boolean waitingForShakeReset = false;

    public void addHeadGestureListener(HeadGestureListener listener) {
        headGestureListeners.add(listener);
    }

    public void removeHeadGestureListener(HeadGestureListener listener) {
        headGestureListeners.remove(listener);
    }

    public void update() {
        float pitch = (float) Math.toDegrees(Tobii.getHeadRotation()[0]);
        float yaw = (float) Math.toDegrees(Tobii.getHeadRotation()[1]);

        trackShaking(yaw);
        trackNodding(pitch);
    }

    private void trackShaking(float yaw) {
        boolean sideActivationThresholdPassed = Math.abs(yaw) >= SHAKE_THRESHOLD_ANGLE;
        boolean resetThresholdPassed = Math.abs(yaw) <= SHAKE_RESET_THRESHOLD_ANGLE;

        if(!waitingForShakeReset) {
            if(shakeStage == HeadShakeStage.NOT_SHAKING && sideActivationThresholdPassed) {
                shakeStage = HeadShakeStage.ONE_SIDE_ACTIVATED;
                shakeSign = Math.signum(yaw);
                shakeExpireTimestamp = System.currentTimeMillis() + SHAKE_EXPIRE_TIME;
            } else if(shakeStage == HeadShakeStage.ONE_SIDE_ACTIVATED && sideActivationThresholdPassed) {
                boolean isOnOtherSide = Math.signum(yaw) != shakeSign;

                if(isOnOtherSide) {
                    shakeStage = HeadShakeStage.TWO_SIDES_ACTIVATED;
                }
            } else if(shakeStage == HeadShakeStage.TWO_SIDES_ACTIVATED && resetThresholdPassed) {
                shakeStage = HeadShakeStage.NOT_SHAKING;

                for(HeadGestureListener listener : headGestureListeners) {
                    listener.shake();
                }
            }
        } else {
            if(resetThresholdPassed) {
                waitingForShakeReset = false;
            }
        }

        if(shakeStage != HeadShakeStage.NOT_SHAKING && System.currentTimeMillis() > shakeExpireTimestamp) {
            shakeStage = HeadShakeStage.NOT_SHAKING;
            waitingForShakeReset = true;
        }
    }

    private void trackNodding(float pitch) {
        boolean resetThresholdPassed = Math.abs(pitch) <= NOD_RESET_THRESHOLD_ANGLE;

        if(!waitingForNodReset) {
            if(!nodDown) {
                if(pitch < NOD_DOWN_THRESHOLD_ANGLE) {
                    nodDown = true;
                    nodExpireTimestamp = System.currentTimeMillis() + NOD_EXPIRE_TIME;
                }
            } else {
                if(pitch > NOD_UP_THRESHOLD_ANGLE) {
                    for(HeadGestureListener listener : headGestureListeners) {
                        listener.nod();
                    }

                    nodDown = false;
                }
            }
        } else {
            if(resetThresholdPassed) {
                waitingForNodReset = false;
            }
        }

        if(nodDown && System.currentTimeMillis() > nodExpireTimestamp) {
            nodDown = false;
            waitingForNodReset = true;
        }
    }

}