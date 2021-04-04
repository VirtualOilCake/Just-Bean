package com.oilman.justbean;

import android.util.Log;

import static com.oilman.justbean.ValueHolder.logTagD;

/**
 * The class to store the score
 *
 * @author Oilman
 * @since 0.0.1
 */
public class Score {
    static int score = -1;

    public static int addScore() {
        Log.d(logTagD, "score is " + score);
        return ++score;
    }

    public static int addScore(int toAdd) {
        score += toAdd;
        return score;
    }

    public static int getInt() {
        return score;
    }

    public static String getString() {
        return String.valueOf(score);
    }

    public static void setScore(int score) {
        Score.score = score;
    }

}
