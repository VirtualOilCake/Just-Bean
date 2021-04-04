package com.oilman.justbean;

/**
 * This is the class that holds values
 *
 * @author Oilman
 * @since 0.1.0
 */
public class ValueHolder {
    /**** Constants ****/

    //The height the emoji will jump in percentage
    public final static float baseJumpHeight = -0.3F;

    //The height the emoji will jump in percentage
    public final static float jumpHeightRange = 0.7F;
    //How many times it is possible for the emoji to rotate
    public final static int rotationRange = 2;

    //the emoji will change at least after this time.
    public final static int baseWetTime = 1000;

    //randomly added to base time to get the final waiting time.
    public final static int wetTimeRange = 5000;

    //The time range for user to get the easter egg.
    public final static int easterEggTime = 3000;

    //How many times the user need to click the easter egg button to get the easter egg.
    public final static int easterEggClicks = 5;

    //the value key for score in the file
    public final static String scoreDataKey = "score";

    //the file name to store the score
    public final static String dataName = "score_data";

    //How long the emoji will jump
    public static int uppingTime = 500;

    //How long the emoji needs to drop
    public static int downTime = 1000;

    public final static String logTagD = "<My Log D>";//a log tag
    public final static String logTagV = "<My Log V>";//a log tag

    /**** Variables ****/
    //The click number in current time range.
    public static int clickNumber = 0;
    //Weather the timer is started
    public static boolean clickTimerStarted = false;
}
