package com.oilman.justbean;

import android.util.Log;

import java.util.TimerTask;

import static com.oilman.justbean.ValueHolder.clickNumber;
import static com.oilman.justbean.ValueHolder.clickTimerStarted;
import static com.oilman.justbean.ValueHolder.logTagD;

public class Helper {
}

/**
 * It seems like that I have to use a sub class.
 *
 * @author Oilman
 * @since 0.1.0
 */
class ClearClickNumber extends TimerTask {
    @Override
    public void run() {
        clickNumber = 0;
        clickTimerStarted = false;
        Log.d(logTagD, "Click number cleared!");
    }
}