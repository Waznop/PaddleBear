package com.waznop.gameobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.waznop.gameworld.BearEnum;
import com.waznop.gameworld.GameWorld;
import com.waznop.gameworld.ShopItemStateEnum;
import com.waznop.paddlebear.AssetLoader;

/**
 * Created by Waznop on 2016-09-20.
 */
public class ShopItem {

    private BearEnum type;
    private GameWorld world;
    private int price;
    private float x;
    private float y;
    private float width;
    private float height;
    private ShopItemStateEnum state;
    private Rectangle bounds;
    private boolean isPressed;
    private Sound clickSound;
    private Sound noclickSound;
    private String name;
    private String description;

    public ShopItem(BearEnum type, GameWorld world, String name, String description, int price, float x, float y,
                    float width, float height) {
        this.type = type;
        this.world = world;
        this.name = name;
        this.description = description;
        this.price = price;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        state = null;
        bounds = new Rectangle(x, y, width, height);
        isPressed = false;
        clickSound = AssetLoader.clickSound;
        noclickSound = AssetLoader.noclickSound;
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public boolean isTouchDown(int screenX, int screenY) {
        boolean condition = isClicked(screenX, screenY);
        boolean canPress = state != ShopItemStateEnum.UNOWNED;
        boolean isMuted = world.getIsMuted() || world.isShowingItem();
        if (condition) {
            if (canPress) {
                isPressed = true;
                if (! isMuted) {
                    clickSound.play();
                }
            } else if (! isMuted) {
                noclickSound.play();
            }
        }
        return condition && canPress;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        boolean condition = isClicked(screenX, screenY) && isPressed && state != ShopItemStateEnum.UNOWNED;
        isPressed = false;
        return condition;
    }

    public void setState(ShopItemStateEnum state) {
        this.state = state;
    }

    public ShopItemStateEnum getState() {
        return state;
    }

    public BearEnum getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
