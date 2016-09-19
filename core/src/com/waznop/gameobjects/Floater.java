package com.waznop.gameobjects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameworld.FloaterEnum;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.HelperFunctions;

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
    private ParticleEffectPool.PooledEffect trail;
    private Vector2 trailPosition;

    public Floater(FloaterEnum type, float x, float y, int width, int height, float speedY, float rotation) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        double rotateRad = Math.toRadians(rotation);
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        velocity = new Vector2(0, speedY);
        this.rotation = rotation;
        collider = new Polygon(new float[] {0, 0, width, 0, width, height, 0, height});
        collider.setOrigin(halfWidth, halfHeight);
        collider.setRotation(rotation);
        this.type = type;
        trailPosition = HelperFunctions.rotateAtPoint(x, y + halfHeight,
                x + halfWidth, y + halfHeight, (float) rotateRad);
        trail = AssetLoader.particlePool.obtain();
        trail.reset();
        ParticleEmitter emitter = trail.getEmitters().first();
        emitter.getSpawnWidth().setHigh(width * (float) Math.cos(rotateRad));
        emitter.getSpawnHeight().setHigh(width * (float) Math.sin(rotateRad));
        emitter.getRotation().setHigh(rotation);
        if (type == FloaterEnum.BABYCUB) {
            emitter.setMaxParticleCount(10);
            emitter.getLife().setHigh(1000);
        }

    }

    public void update(float delta, float extraSpeedY) {
        position.mulAdd(velocity, delta);
        position.add(0, extraSpeedY * delta);
        trailPosition.mulAdd(velocity, delta);
        trailPosition.add(0, extraSpeedY * delta);
        collider.setPosition(position.x, position.y);
        trail.setPosition(trailPosition.x, trailPosition.y);
        trail.update(delta);
    }

    public void die() {
        trail.allowCompletion();
        AssetLoader.particlePool.free(trail);
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

    public ParticleEffect getTrail() {
        return trail;
    }

    public float getTrailX() {
        return trailPosition.x;
    }

    public float getTrailY() {
        return trailPosition.y;
    }

}
