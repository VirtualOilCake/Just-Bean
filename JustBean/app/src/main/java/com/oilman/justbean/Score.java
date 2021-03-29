package com.oilman.justbean;

public class Score {
    static int score;

    public Score() {
        score = 0;
    }
    public Score(int inScore) {
        score = inScore;
    }
    public static int addScore() {
        return ++score;
    }

    public static int addScore(int toAdd) {
        score += toAdd;
        return score;
    }

    public static int getScore() {
        return score;
    }

    public static String getString() {
        return String.valueOf(score);
    }


}
