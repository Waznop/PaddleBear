package com.waznop.paddlebear;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.SimpleButton;
import com.waznop.gameworld.GameStateEnum;
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

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.GAMEOVER) {
            world.showPostMenu();
            return true;
        }

        if (state == GameStateEnum.MENU || state == GameStateEnum.POSTMENU) {
            world.startGame();
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
        screenX /= Constants.GAME_SCALE;
        screenY /= Constants.GAME_SCALE;

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.GAMEOVER) {
            world.showPostMenu();
            return true;
        }

        if (state == GameStateEnum.MENU || state == GameStateEnum.POSTMENU) {
            boolean touchDown = false;
            for (SimpleButton simpleButton : world.getActiveButtons()) {
                touchDown = touchDown || simpleButton.isTouchDown(screenX, screenY);
            }
            return touchDown;
        }

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
        screenX /= Constants.GAME_SCALE;
        screenY /= Constants.GAME_SCALE;

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.MENU || state == GameStateEnum.POSTMENU) {
            for (SimpleButton simpleButton : world.getActiveButtons()) {
                 if (simpleButton.isTouchUp(screenX, screenY)) {
                     switch(simpleButton.getType()) {
                         case PLAY:
                             world.startGame();
                             break;
                     }
                     return true;
                 }
            }
            return false;
        }

        touchSection = TouchScreenEnum.NONE;
        return true;
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
