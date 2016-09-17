package com.waznop.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.waznop.paddlebear.Constants;

import java.util.LinkedList;

/**
 * Created by Waznop on 2016-09-16.
 */
public class Scrollable {

    protected Vector2 initialPosition;
    protected LinkedList<Vector2> positions;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected int numTiles;

    public Scrollable(int numTiles, float x, float y, int width, int height, float speedY) {
        this.numTiles = numTiles;
        initialPosition = new Vector2(x, y);
        velocity = new Vector2(0, speedY);
        this.width = width;
        this.height = height;
        positions = new LinkedList<Vector2>();
        for (int i = 0; i < numTiles; ++i) {
            Vector2 position = initialPosition.cpy().sub(0, height * i);
            positions.add(position);
        }
    }

    public void reset() {
        for (int i = 0; i < numTiles; ++i) {
            positions.get(i).set(initialPosition).sub(0, height * i);
        }
    }

    public void update(float delta, float extraSpeedY) {
        for (Vector2 position : positions) {
            position.mulAdd(velocity, delta);
            position.add(0, extraSpeedY * delta);
        }

        if (positions.getFirst().y >= Constants.GAME_START_Y + Constants.GAME_HEIGHT) {
            if (numTiles == 1) {
                positions.getFirst().y = initialPosition.y;
            } else {
                Vector2 outOfBoundsPosition = positions.removeFirst();
                outOfBoundsPosition.set(initialPosition.x, positions.getLast().y - height);
                positions.add(outOfBoundsPosition);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public LinkedList<Vector2> getPositions() {
        return positions;
    }

    public int getNumTiles() {
        return numTiles;
    }

}
