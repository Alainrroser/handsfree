package ch.bbcag.handsfree.error;

public class HandsFreeRobotException extends RuntimeException {

    public HandsFreeRobotException(Exception exception) {
        super(exception);
    }

}
