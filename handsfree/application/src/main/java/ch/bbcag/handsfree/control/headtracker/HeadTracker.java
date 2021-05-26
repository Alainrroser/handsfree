package ch.bbcag.handsfree.control.headtracker;

import ch.bbcag.handsfree.control.ThreadedSystem;
import tobii.Tobii;

import java.util.ArrayList;
import java.util.List;

public class HeadTracker extends ThreadedSystem {

    enum HeadShakeStage {
        NOT_SHAKING,
        ONE_SIDE_ACTIVATED,
        TWO_SIDES_ACTIVATED
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

    @Override
    public void run() {
        while(isRunning()) {
            update();
        }
    }

    private void update() {
        float pitch = (float) Math.toDegrees(Tobii.getHeadRotation()[0]);
        float yaw = (float) Math.toDegrees(Tobii.getHeadRotation()[1]);

        trackShaking(yaw);
        trackNodding(pitch);
    }

    private void trackShaking(float yaw) {
        if(!waitingForShakeReset) {
            updateShakeStage(yaw);
        } else if(isShakeResetThresholdPassed(yaw)) {
            waitingForShakeReset = false;
        }

        if(shouldShakeBeAborted()) {
            abortCurrentShake();
        }
    }

    private void updateShakeStage(float yaw) {
        if(shakeStage == HeadShakeStage.NOT_SHAKING && isShakeSideActivationThresholdPassed(yaw)) {
            finishFirstShakeSide(yaw);
        } else if(shakeStage == HeadShakeStage.ONE_SIDE_ACTIVATED && isShakeSideActivationThresholdPassed(yaw)) {
            finishSecondShakeSide(yaw);
        } else if(shakeStage == HeadShakeStage.TWO_SIDES_ACTIVATED && isShakeResetThresholdPassed(yaw)) {
            finishShake();
        }
    }

    private void finishFirstShakeSide(float yaw) {
        shakeStage = HeadShakeStage.ONE_SIDE_ACTIVATED;
        shakeSign = Math.signum(yaw);
        shakeExpireTimestamp = System.currentTimeMillis() + SHAKE_EXPIRE_TIME;
    }

    private void finishSecondShakeSide(float yaw) {
        boolean isOnOtherSide = Math.signum(yaw) != shakeSign;

        if(isOnOtherSide) {
            shakeStage = HeadShakeStage.TWO_SIDES_ACTIVATED;
        }
    }

    private void finishShake() {
        shakeStage = HeadShakeStage.NOT_SHAKING;

        for(HeadGestureListener listener : headGestureListeners) {
            listener.shake();
        }
    }

    private boolean shouldShakeBeAborted() {
        return shakeStage != HeadShakeStage.NOT_SHAKING && System.currentTimeMillis() > shakeExpireTimestamp;
    }

    private void abortCurrentShake() {
        shakeStage = HeadShakeStage.NOT_SHAKING;
        waitingForShakeReset = true;
    }

    private boolean isShakeSideActivationThresholdPassed(float yaw) {
        return Math.abs(yaw) >= SHAKE_THRESHOLD_ANGLE;
    }

    private boolean isShakeResetThresholdPassed(float yaw) {
        return Math.abs(yaw) <= SHAKE_RESET_THRESHOLD_ANGLE;
    }

    private void trackNodding(float pitch) {
        boolean resetThresholdPassed = Math.abs(pitch) <= NOD_RESET_THRESHOLD_ANGLE;

        if(!waitingForNodReset) {
            updateNodStage(pitch);
        } else if(resetThresholdPassed) {
            waitingForNodReset = false;
        }

        if(shouldNodBeAborted()) {
            abortCurrentNod();
        }
    }

    private void updateNodStage(float pitch) {
        if(!nodDown && pitch < NOD_DOWN_THRESHOLD_ANGLE) {
            finishNodDown();
        } else if(nodDown && pitch > NOD_UP_THRESHOLD_ANGLE) {
            finishNod();
        }
    }

    private void finishNodDown() {
        nodDown = true;
        nodExpireTimestamp = System.currentTimeMillis() + NOD_EXPIRE_TIME;
    }

    private void finishNod() {
        for(HeadGestureListener listener : headGestureListeners) {
            listener.nod();
        }

        nodDown = false;
    }

    private boolean shouldNodBeAborted() {
        return nodDown && System.currentTimeMillis() > nodExpireTimestamp;
    }

    private void abortCurrentNod() {
        nodDown = false;
        waitingForNodReset = true;
    }

}
