package com.waznop.gameobjects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.waznop.paddlebear.Constants;

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

    public Bear(float x, float y, int width, int height) {

        position = new Vector2(x, y);
        rotation = 0;
        this.width = width;
        this.height = height;

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

    }

    public void reset(float x, float y) {
        rotation = 0;
        position.set(x, y);
        lv = 0;
        av = 0;
        la = 0;
        aa = 0;
        paddleTimer = 0;
        paddlingLeft = true;
        paddlingFront = true;
        isAlive = true;
        dyingTimer = 0;
    }

    public void updateGameover(float delta) {
        position.y += (Constants.BOAT_SCROLL_Y - Constants.LAND_SCROLL_Y) * delta;
        dyingTimer += delta;
    }

    public void updatePlaying(float delta) {
        updatePlaying(delta, 0);
    }

    public void updatePlaying(float delta, float extraSpeedY) {

        if (paddleTimer > 0) {
            paddleTimer -= delta;

            if (paddleTimer <= Constants.PADDLE_TIMER_B && la != Constants.LA_FORCE_B) {
                la = paddlingFront ? Constants.LA_FORCE_B : Constants.LA_BACK_FORCE_B;
                aa = Math.signum(aa) * Constants.AA_FORCE_B;
            }

            if (paddleTimer <= Constants.PADDLE_TIMER_C && la != Constants.LA_FORCE_C) {
                la = paddlingFront ? Constants.LA_FORCE_C : Constants.LA_BACK_FORCE_C;
                aa = Math.signum(aa) * Constants.AA_FORCE_C;
            }

            if (paddleTimer <= 0) {
                paddleTimer = 0;
                la = 0;
                aa = 0;
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

    }

    public void onPaddleFrontLeft() {
        la = Constants.LA_FORCE_A;
        aa = - Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = true;
        paddlingFront = true;
    }

    public void onPaddleFrontRight() {
        la = Constants.LA_FORCE_A;
        aa = Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = false;
        paddlingFront = true;
    }

    public void onPaddleBackLeft() {
        la = Constants.LA_BACK_FORCE_A;
        aa = Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = true;
        paddlingFront = false;
    }

    public void onPaddleBackRight() {
        la = Constants.LA_BACK_FORCE_A;
        aa = - Constants.AA_FORCE_A;
        paddleTimer = Constants.PADDLE_TIMER_A;
        paddlingLeft = false;
        paddlingFront = false;
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

    public void die() {
        isAlive = false;
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
}
