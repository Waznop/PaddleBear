package com.waznop.paddlebear;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.waznop.gameobjects.Bear;
import com.waznop.gameworld.GameState;
import com.waznop.gameworld.GameWorld;

/**
 * Created by Waznop on 2016-08-22.
 */
public class InputHandler implements InputProcessor {

    private Bear bear;
    private GameWorld world;
    public static TouchScreenEnum touchSection;

    public InputHandler(GameWorld world) {
        this.world = world;
        this.bear = world.getBear();
        touchSection = TouchScreenEnum.NONE;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (world.getCurrentState() != GameState.PLAYING) {
            world.reset();
            return true;
        }

        if (keycode == Keys.I) {
            bear.onPaddleFrontLeft();
        } else if (keycode == Keys.O) {
            bear.onPaddleFrontRight();
        } else if (keycode == Keys.K) {
            bear.onPaddleBackLeft();
        } else if (keycode == Keys.L) {
            bear.onPaddleBackRight();
        } else if (keycode == Keys.NUM_0) {
            world.toggleInvincibility();
        } else if (keycode == Keys.NUM_1) {
            world.toggleDebug();
        } else if (keycode == Keys.NUM_2) {
            world.resetData();
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (world.getCurrentState() != GameState.PLAYING) {
            world.reset();
            return true;
        }

        screenX /= Constants.GAME_SCALE;
        screenY /= Constants.GAME_SCALE;

        if (screenX < Constants.GAME_MID_X) {
            if (screenY < Constants.GAME_MID_Y) {
                touchSection = TouchScreenEnum.TOPLEFT;
                bear.onPaddleFrontLeft();
            } else {
                touchSection = TouchScreenEnum.BOTLEFT;
                bear.onPaddleBackLeft();
            }
        } else {
            if (screenY < Constants.GAME_MID_Y) {
                touchSection = TouchScreenEnum.TOPRIGHT;
                bear.onPaddleFrontRight();
            } else {
                touchSection = TouchScreenEnum.BOTRIGHT;
                bear.onPaddleBackRight();
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchSection = TouchScreenEnum.NONE;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
