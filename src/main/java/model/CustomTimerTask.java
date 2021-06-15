package model;

import java.util.TimerTask;

public abstract class CustomTimerTask extends TimerTask {

    protected int timeCounter = 0;

    /** @return the counter of the timer. */
    public int getTimeCounter() {
        return timeCounter;
    }
}
