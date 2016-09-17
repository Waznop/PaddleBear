package com.waznop.gameobjects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameworld.FloaterEnum;

/**
 * Created by Waznop on 2016-09-16.
 */
public class Floater {

    private Vector2 position;
    private Vector2 velocity;
    private Polygon collider;
    private float rotation;
    private int width;
    private int height;
    private FloaterEnum type;

    public Floater(FloaterEnum type, float x, float y, int width, int height, float speedY, float rotation) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        velocity = new Vector2(0, speedY);
        this.rotation = rotation;
        collider = new Polygon(new float[] {0, 0, width, 0, width, height, 0, height});
        collider.setOrigin(width / 2, height / 2);
        collider.setRotation(rotation);
        this.type = type;
    }

    public void update(float delta, float extraSpeedY) {
        position.mulAdd(velocity, delta);
        position.add(0, extraSpeedY * delta);
        collider.setPosition(position.x, position.y);
    }

    public Polygon getCollider() {
        return collider;
    }

    public FloaterEnum getType() {
        return type;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

}
