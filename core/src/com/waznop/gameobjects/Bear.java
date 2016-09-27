package com.waznop.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameworld.GameWorld;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.paddlebear.HelperFunctions;

/**
 * Created by Waznop on 2016-08-17.
 */
public class Bear {

    private Vector2 position;
    private float rotation;
    private int width;
    private int height;

    private float lv;
    private float av;
    private float la;
    private float aa;

    private float paddleTimer;
    private boolean paddlingLeft;
    private boolean paddlingFront;

    private int radius;
    private Polygon collider;

    private boolean isAlive;
    private float dyingTimer;
    private boolean isInvincible;

    private Vector2 trailPosition;
    private ParticleEffect trail;

    private Sound paddling1Sound;
    private Sound paddling2Sound;
    private Sound prevPaddlingSound;
    private Sound nextPaddlingSound;

    private GameWorld world;

    public Bear(GameWorld world, float x, float y, int width, int height) {

        position = new Vector2(x, y);
        rotation = 0;
        this.width = width;
        this.height = height;

        this.world = world;

        lv = 0;
        av = 0;
        la = 0;
        aa = 0;

        paddleTimer = 0;
        paddlingLeft = true;
        paddlingFront = true;

        radius = Constants.BOAT_RADIUS;

        float halfWidth = width / 2;
        float halfHeight = height / 2;
        collider = new Polygon(new float[] {halfWidth, 0, width, halfHeight, halfWidth, height, 0, halfHeight});
        collider.setOrigin(halfWidth, halfHeight);

        isAlive = true;
        dyingTimer = 0;
        isInvincible = Constants.IS_INVINCIBLE;

        trailPosition = new Vector2(x + halfWidth, y);
        trail = AssetLoader.bearTrail;
        trail.start();

        paddling1Sound = AssetLoader.paddling1Sound;
        paddling2Sound = AssetLoader.paddling2Sound;
        prevPaddlingSound = paddling2Sound;
        nextPaddlingSound = paddling1Sound;
    }

    public void reset(float x, float y) {
        rotation = 0;
        position.set(x, y);
        trailPosition.set(x + width / 2, y);
        lv = 0;
        av = 0;
        la = 0;
        aa = 0;
        paddleTimer = 0;
        paddlingLeft = true;
        paddlingFront = true;
        isAlive = true;
        dyingTimer = 0;
        trail.reset();
        getEmitter().getLife().setHigh(1000);
        getEmitter().setAttached(false);
    }

    public void updateGameover(float delta) {
        float scrollSpeedY = Constants.BOAT_SCROLL_Y - Constants.LAND_SCROLL_Y;
        position.y += scrollSpeedY * delta;
        trailPosition.y += scrollSpeedY * delta;
        dyingTimer += delta;
        trail.update(delta);
    }

    public void updatePlaying(float delta) {
        updatePlaying(delta, 0);
    }

    public void updatePlaying(float delta, float extraSpeedY) {

        if (paddleTimer > 0) {
            paddleTimer -= delta;

            if (paddleTimer <= 0) {
                paddleTimer = 0;
                la = 0;
                aa = 0;
            } else if (paddleTimer <= Constants.PADDLE_TIMER_C && la != Constants.LA_FORCE_C) {
                la = paddlingFront ? Constants.LA_FORCE_C : Constants.LA_BACK_FORCE_C;
                aa = Math.signum(aa) * Constants.AA_FORCE_C;
            } else if (paddleTimer <= Constants.PADDLE_TIMER_B && la != Constants.LA_FORCE_B) {
                la = paddlingFront ? Constants.LA_FORCE_B : Constants.LA_BACK_FORCE_B;
                aa = Math.signum(aa) * Constants.AA_FORCE_B;
            }

        }

        lv += la * delta;
        lv *= Constants.RESISTANCE;
        av += aa * delta;
        av *= Constants.RESISTANCE;
        rotation += av * delta;
        float rotationRad = (float) Math.toRadians(rotation);

        float ds = lv * delta;
        float dx = ds * (float) Math.sin(rotationRad);
        float dy = ds * (float) Math.cos(rotationRad);

        position.x += dx;
        position.y += (Constants.BOAT_SCROLL_Y + extraSpeedY) * delta - dy;

        collider.setPosition(position.x, position.y);
        collider.setRotation(rotation);

        float centerX = getCenterX();
        float centerY = getCenterY();
        trailPosition.set(centerX, position.y);
        HelperFunctions.rotateAtPoint(trailPosition, centerX, centerY, rotationRad);

        if (ds > 0) {
            trail.setPosition(trailPosition.x, trailPosition.y);
        } else {
            trail.setPosition(centerX, centerY);
        }

        getEmitter().getRotation().setHigh(rotation);

        trail.update(delta);

    }

    public void onPaddleFrontLeft() {
        la = Constants.LA_FORCE_A;
        aa = Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = true;
        paddlingFront = true;
        playPaddlingSound();
    }

    public void onPaddleFrontRight() {
        la = Constants.LA_FORCE_A;
        aa = - Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = false;
        paddlingFront = true;
        playPaddlingSound();
    }

    public void onPaddleBackLeft() {
        la = Constants.LA_BACK_FORCE_A;
        aa = - Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = true;
        paddlingFront = false;
        playPaddlingSound();
    }

    public void onPaddleBackRight() {
        la = Constants.LA_BACK_FORCE_A;
        aa = Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = false;
        paddlingFront = false;
        playPaddlingSound();
    }

    private void playPaddlingSound() {
        if (world.getIsMuted()) {
            return;
        }

        prevPaddlingSound.stop();
        nextPaddlingSound.play();
        prevPaddlingSound = nextPaddlingSound;
        nextPaddlingSound = nextPaddlingSound == paddling1Sound ? paddling2Sound : paddling1Sound;
    }

    public void die() {
        isAlive = false;
        trail.allowCompletion();
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getRotation() {
        return rotation;
    }

    public float getPaddleTimer() {
        return paddleTimer;
    }

    public boolean getPaddlingLeft() {
        return paddlingLeft;
    }

    public boolean getPaddlingFront() {
        return paddlingFront;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getCenterX() {
        return position.x + width / 2;
    }

    public float getCenterY() {
        return position.y + height / 2;
    }

    public int getRadius() {
        return radius;
    }

    public Polygon getCollider() {
        return collider;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public float getDyingTimer() {
        return dyingTimer;
    }

    public boolean getIsInvincible() {
        return isInvincible;
    }

    public void setIsInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public ParticleEffect getTrail() {
        return trail;
    }

    public ParticleEmitter getEmitter() {
        return trail.getEmitters().first();
    }

    public float getTrailX() {
        return trailPosition.x;
    }

    public float getTrailY() {
        return trailPosition.y;
    }
}
