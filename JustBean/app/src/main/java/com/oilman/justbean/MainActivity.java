package com.oilman.justbean;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Random random = new Random();

    Timer timer;
    Button button;
    TextView scoreTextView;
    final float jumpHeight = -1.1F;
    final int baseWetTime = 1000;
    final int wetTimeRange = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
FileSaver.saveFile();

        button = findViewById(R.id.button);
        scoreTextView = findViewById(R.id.scoreTextView);

        final AnimationSet[] jump = {getAnimation()};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Score.addScore();
                scoreTextView.setText(Score.getString());
                button.startAnimation(jump[0]);
                jump[0]=getAnimation();
            }
        });


    }

    private boolean isWet() {
        return button.getText().equals(getString(R.string.wet_bean));
    }

    private void dryUp() {
        button.setText(getString(R.string.dry_bean));
    }

    private void getWet() {
        button.setText(getString(R.string.wet_bean));
    }

    public AnimationSet getAnimation() {
        AnimationSet jump = new AnimationSet(false);

        TranslateAnimation translateAnimationUp = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                0F,//to x
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                jumpHeight
        );
        translateAnimationUp.setDuration(700);
        translateAnimationUp.setInterpolator(new PathInterpolator(0.01F, 1F));
        translateAnimationUp.setFillAfter(true);


        TranslateAnimation translateAnimationDown = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                0F,//to x
                Animation.RELATIVE_TO_PARENT,
                0F,
                Animation.RELATIVE_TO_PARENT,
                -jumpHeight
        );
        translateAnimationDown.setDuration(1000);
        translateAnimationDown.setInterpolator(new android.view.animation.BounceInterpolator());

        TranslateAnimation emptyAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0F,
                Animation.RELATIVE_TO_SELF,
                0F,//to x
                Animation.RELATIVE_TO_SELF,
                0F,
                Animation.RELATIVE_TO_SELF,
                0F
        );
        emptyAnimation.setDuration(baseWetTime + random.nextInt(wetTimeRange));

        jump.addAnimation(translateAnimationUp);
        jump.addAnimation(translateAnimationDown);
        jump.addAnimation(emptyAnimation);


        jump.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                button.setClickable(false);
                dryUp();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setClickable(true);
                getWet();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        return jump;
    }
}
