package com.waznop.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.waznop.gameworld.ButtonTypeEnum;

/**
 * Created by Waznop on 2016-09-18.
 */
public class SimpleButton {

    private float x;
    private float y;
    private float width;
    private float height;
    private ButtonTypeEnum type;
    private TextureRegion buttonUp;
    private TextureRegion buttonDown;
    private Rectangle bounds;
    private boolean isPressed;

    public SimpleButton(ButtonTypeEnum type, float x, float y, float width, float height,
                        TextureRegion buttonUp, TextureRegion buttonDown) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        bounds = new Rectangle(x, y, width, height);
        isPressed = false;
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        batcher.draw(isPressed ? buttonDown : buttonUp, x, y, width, height);
    }

    public boolean isTouchDown(int screenX, int screenY) {
        boolean condition = isClicked(screenX, screenY);
        if (condition) {
            isPressed = true;
        }
        return condition;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        boolean condition = isClicked(screenX, screenY) && isPressed;
        isPressed = false;
        return condition;
    }

    public ButtonTypeEnum getType() {
        return type;
    }

}
