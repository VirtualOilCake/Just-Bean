package com.oilman.justbean;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import java.util.Random;

import static com.oilman.justbean.ValueHolder.logTagV;
import static com.oilman.justbean.ValueHolder.rotationRange;
import static com.oilman.justbean.ValueHolder.uppingTime;

public class OtherAnimation {
}

/**
 * Get an animation that will randomly rotate the view.
 * <p>
 * This is a sub class of AnimationSet instead of RotationAnimation,
 * because a Random object is required for getting a random RotationAnimation
 *
 * @author Oilman
 * @see AnimationSet
 * @see RotateAnimation
 * @see ValueHolder for random range
 * @since 0.2.0
 */
class RandomRotationAnimation extends AnimationSet {
    public RandomRotationAnimation() {
        super(false);
        Random random = new Random();
        int turn = random.nextInt(3) - 1;//Should be -1, 0, or 1
        int toDegree = 360 * random.nextInt(rotationRange + 1) * turn;
        Log.v(logTagV, "This rotation is " + toDegree + " degree");
        RotateAnimation rotateAnimation = new RotateAnimation(
                0,
                toDegree,
                RELATIVE_TO_SELF,
                0.5F,
                RELATIVE_TO_SELF,
                0.5F
        );
        rotateAnimation.setDuration(uppingTime + 200);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.addAnimation(rotateAnimation);
    }


}

/**
 * An AnimationSet that zoom and then shrink the view back to normal.
 *
 * @author Oilman
 * @since 0.2.0
 */
class ZoomAndShrink extends AnimationSet {
    float zoomSize;
    int zoomTime;

    public ZoomAndShrink() {
        super(false);
        zoomSize = 1.1F;
        zoomTime = 200;
        ScaleAnimation zoom = new ScaleAnimation(1F, zoomSize, 1F, zoomSize,
                RELATIVE_TO_SELF,
                0.5F,
                RELATIVE_TO_SELF,
                0.5F
        );
        zoom.setDuration(zoomTime);
        zoom.setInterpolator(new DecelerateInterpolator());
        zoom.setFillAfter(true);
        ScaleAnimation shrink = new ScaleAnimation(zoomSize, 1F, zoomSize, 1F,
                RELATIVE_TO_SELF,
                0.5F,
                RELATIVE_TO_SELF,
                0.5F
        );
        shrink.setDuration(100);
        shrink.setStartOffset(zoomTime);
        shrink.setInterpolator(new AccelerateInterpolator());

        this.addAnimation(zoom);
        this.addAnimation(shrink);
    }

    /**
     * @param zoomSize how big the view will get
     */
    public ZoomAndShrink(float zoomSize) {
        super(false);
        zoomTime = 200;
        ScaleAnimation zoom = new ScaleAnimation(1F, zoomSize, 1F, zoomSize,
                RELATIVE_TO_SELF,
                0.5F,
                RELATIVE_TO_SELF,
                0.5F
        );
        zoom.setDuration(zoomTime);
        zoom.setInterpolator(new DecelerateInterpolator());
        zoom.setFillAfter(true);
        ScaleAnimation shrink = new ScaleAnimation(zoomSize, 1F, zoomSize, 1F,
                RELATIVE_TO_SELF,
                0.5F,
                RELATIVE_TO_SELF,
                0.5F
        );
        shrink.setDuration(100);
        shrink.setStartOffset(zoomTime);
        shrink.setInterpolator(new AccelerateInterpolator());

        this.addAnimation(zoom);
        this.addAnimation(shrink);
    }
}