package com.oilman.justbean;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;
import java.util.Timer;

import static com.oilman.justbean.ValueHolder.baseJumpHeight;
import static com.oilman.justbean.ValueHolder.clickNumber;
import static com.oilman.justbean.ValueHolder.clickTimerStarted;
import static com.oilman.justbean.ValueHolder.dataName;
import static com.oilman.justbean.ValueHolder.downTime;
import static com.oilman.justbean.ValueHolder.easterEggClicks;
import static com.oilman.justbean.ValueHolder.easterEggTime;
import static com.oilman.justbean.ValueHolder.jumpHeightRange;
import static com.oilman.justbean.ValueHolder.logTagD;
import static com.oilman.justbean.ValueHolder.logTagV;
import static com.oilman.justbean.ValueHolder.scoreDataKey;
import static com.oilman.justbean.ValueHolder.uppingTime;

/**
 * @author Oilman
 * @since 0.0.1
 */
public class MainActivity extends AppCompatActivity {
    Random random = new Random();

    Timer timer;
    Button emojiButton, easterEggButton;
    TextView scoreTextView;
    ConstraintLayout buttonConstrainLayout;

    /**
     * Basically everything
     *
     * @param savedInstanceState the Android stuff
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Getting the score from local file
         */
        if (Score.getInt() == 0 || Score.getInt() == -1) {
            int scoreInData = getScoreFromData();
            Log.v(logTagV, scoreInData + "is score in data");
            Score.setScore(scoreInData);
        }

        timer = new Timer();

        /*
         * Bundling the GUI
         */
        emojiButton = findViewById(R.id.emojiButton);
        easterEggButton = findViewById(R.id.easterEggButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        buttonConstrainLayout = findViewById(R.id.buttonConstranLayOut);

        scoreTextView.setText(Score.getString());

        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the score can be divide by 10, make a special animation
                if (Score.getInt() % 10 == 9) {
                    Log.d(logTagD, "current Score:" + Score.getInt());
                    Log.d(logTagD, "Congratulation!");
                    scoreTextView.startAnimation(new ZoomAndShrink(1.4F));
                }
                // the normal case
                else {
                    scoreTextView.startAnimation(new ZoomAndShrink(1.1F));
                }
                addScore();// Update "Score.score" and "score_data" file
                scoreTextView.setText(Score.getString());// Update the text
                buttonConstrainLayout.startAnimation(getJumpAnimationSet());// Start the jump animation
            }
        });

        // Easter egg
        easterEggButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                Log.v(logTagV, "Click num: " + clickNumber);
                // If the button has been clicked for enough times in a given time
                if (clickNumber >= easterEggClicks) {
                    Log.d(logTagD, "You got the easter egg!");
                    Toast.makeText(MainActivity.this, getString(R.string.enough_is_enough), Toast.LENGTH_SHORT).show();// Make a toast
                    clickNumber = 0;
                }
                // Else add the click number, and start a timer if there is no timer
                else {
                    clickNumber++;
                    if (!clickTimerStarted) {
                        Log.v(logTagV, "New timer");
                        clickTimerStarted = true;
                        timer.schedule(new ClearClickNumber(), easterEggTime);
                    }
                }
            }
        });
    }

    private boolean isWet() {
        return emojiButton.getText().equals(getString(R.string.wet_bean));
    }

    private void dryUp() {
        emojiButton.setText(getString(R.string.dry_bean));
    }

    private void getWet() {
        emojiButton.setText(getString(R.string.wet_bean));
    }

    /**
     * Get an AnimationSet with three Animation:
     * 1. Moving up
     * 2. Moving down
     * 3. Doing nothing with a random duration
     * (I cannot use timer for this, because the timer is not in the UI thread)
     * The height for each jump is random.
     * <p>
     * When the AnimationSet started:
     * The button will start a RandomRotationAnimation.
     * The button will not be clickable.
     * The emoji will be replaced by the dry_emoji
     * <p>
     * When the AnimationSet ended:
     * The button will be clickable again.
     * The emoji will be replaced by the wet_emoji
     *
     * @return an AnimationSet of jumping.
     * @see RandomRotationAnimation
     * @since 0.1.0
     */
    public AnimationSet getJumpAnimationSet() {
        AnimationSet jump = new AnimationSet(false);

        float thisJumpHeight = baseJumpHeight - random.nextFloat() * jumpHeightRange;
        Log.v(logTagV, "this time the height is: " + thisJumpHeight);
        // The going up animation
        TranslateAnimation translateAnimationUp = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                0F,//to x
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                thisJumpHeight//to y
        );
        translateAnimationUp.setDuration(uppingTime);
        translateAnimationUp.setInterpolator(new PathInterpolator(0.0001F, 1F));
        translateAnimationUp.setFillAfter(true);
        // The going down animation
        TranslateAnimation translateAnimationDown = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                0F,//to x
                Animation.RELATIVE_TO_PARENT,
                0F,//to y
                Animation.RELATIVE_TO_PARENT,
                -thisJumpHeight
        );
        translateAnimationDown.setDuration(downTime);
        translateAnimationDown.setStartOffset(uppingTime);
        translateAnimationDown.setInterpolator(new android.view.animation.BounceInterpolator());
        // The empty animation
        RotateAnimation emptyAnimation = new RotateAnimation(0F, 0F);
        emptyAnimation.setDuration(ValueHolder.baseWetTime + random.nextInt(ValueHolder.wetTimeRange));
        emptyAnimation.setStartOffset(uppingTime + downTime);
        // Add the animation to the animation set
        jump.addAnimation(translateAnimationUp);
        jump.addAnimation(translateAnimationDown);
        jump.addAnimation(emptyAnimation);
        // The animation listener
        jump.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                emojiButton.setClickable(false);
                dryUp();
                emojiButton.startAnimation(new RandomRotationAnimation());
                Log.v(logTagV, "Jumping!");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                emojiButton.setClickable(true);
                getWet();
                Log.v(logTagV, "It is wet");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //Do nothing
            }

        });

        return jump;
    }

    /**
     * Save the score to a local file
     *
     * @param scoreToSave score to save
     * @since 0.1.0
     */
    public void saveScore(int scoreToSave) {
        SharedPreferences scoreData = getSharedPreferences(dataName, MODE_PRIVATE);
        SharedPreferences.Editor scoreDataEditor = scoreData.edit();
        scoreDataEditor.putInt(scoreDataKey, scoreToSave);
        scoreDataEditor.apply();
        Score.setScore(scoreToSave);
        Log.v(logTagV, "current score in data is: " + getScoreFromData());
    }

    public int getScoreFromData() {
        SharedPreferences scoreData = getSharedPreferences(dataName, MODE_PRIVATE);
        Log.v(logTagV, "getting score from data");
        return scoreData.getInt(scoreDataKey, 0);
    }

    /**
     * Add the Score.score by one.
     * And keep the score_data file up-to-date
     */
    public void addScore() {
        Score.addScore();
        Log.v(logTagV, "Added");
        saveScore(Score.getInt());
    }

    /**
     * @param menu the Android stuff
     * @return true, because otherwise the menu won't work.
     * @since 0.3.0
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item idk
     * @return true, because otherwise the menu won't work.
     * @since 0.3.0
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                Log.v(logTagV, "you clicked about");
                View aboutView = View.inflate(MainActivity.this, R.layout.about_view, null);
                TextView aboutTextView = aboutView.findViewById(R.id.aboutTextView);
                aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());
                AlertDialog aboutDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.about_title)
                        .setView(aboutView)
                        .setIcon(R.drawable.ic_emoji)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.v(logTagV, "Clicked the OK button");
                            }
                        })
                        .create();
                aboutDialog.show();
                break;
            case R.id.menu_item_credit:
                Log.v(logTagV, "menu_item_credit");
                View creditView = View.inflate(MainActivity.this, R.layout.credit_view, null);
                TextView creditTextView = creditView.findViewById(R.id.creditTextView);
                creditTextView.setMovementMethod(LinkMovementMethod.getInstance());
                AlertDialog creditDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.credit_title)
                        .setView(creditView)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.v(logTagV, "Clicked the OK button");
                            }
                        })
                        .create();
                creditDialog.show();
                break;
        }
        return true;
    }
}




