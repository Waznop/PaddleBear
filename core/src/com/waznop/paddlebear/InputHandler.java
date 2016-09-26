package com.waznop.paddlebear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.ShopItem;
import com.waznop.gameobjects.SimpleButton;
import com.waznop.gameworld.GameStateEnum;
import com.waznop.gameworld.GameWorld;

/**
 * Created by Waznop on 2016-08-22.
 */
public class InputHandler implements InputProcessor {

    private Bear bear;
    private GameWorld world;
    private OrthographicCamera cam;
    public static TouchScreenEnum touchSection;
    private Vector3 touch;

    public InputHandler(GameWorld world, OrthographicCamera cam) {
        this.world = world;
        this.bear = world.getBear();
        this.cam = cam;
        touchSection = TouchScreenEnum.NONE;
        touch = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.GAMEOVER && keycode == Keys.SPACE) {
            world.showPostMenu();
            return true;
        }

        if (state == GameStateEnum.PLAYING) {
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
            } else if (keycode == Keys.NUM_3) {
                world.addToKarma(100);
                world.saveKarma();
            } else {
                return false;
            }
            return true;
        }

        if (keycode == Keys.SPACE) {
            if (state == GameStateEnum.MENU || state == GameStateEnum.POSTMENU) {
                world.startGame();
            } else if (state == GameStateEnum.POSTMENUSHOP) {
                world.showPostMenu();
            } else {
                world.goToMenu();
            }
            return true;
        }

        return false;
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
        cam.unproject(touch.set(screenX, screenY, 0));
        screenX = (int) touch.x;
        screenY = (int) touch.y;

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.GAMEOVER) {
            world.showPostMenu();
            return true;
        }

        if (state == GameStateEnum.PLAYING) {
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

        boolean touchDown = false;

        if ((state == GameStateEnum.MENUSHOP || state == GameStateEnum.POSTMENUSHOP)
                && ! world.isShowingItem()) {
            for (ShopItem item : world.getShopItems()) {
                touchDown = touchDown || item.isTouchDown(screenX, screenY);
                if (touchDown) {
                    return true;
                }
            }
        }

        for (SimpleButton simpleButton : world.getActiveButtons()) {
            touchDown = touchDown || simpleButton.isTouchDown(screenX, screenY);
            if (touchDown) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        cam.unproject(touch.set(screenX, screenY, 0));
        screenX = (int) touch.x;
        screenY = (int) touch.y;

        GameStateEnum state = world.getCurrentState();

        if (state == GameStateEnum.PLAYING || state == GameStateEnum.GAMEOVER) {
            touchSection = TouchScreenEnum.NONE;
            return true;
        }

        if ((state == GameStateEnum.MENUSHOP || state == GameStateEnum.POSTMENUSHOP)
                && ! world.isShowingItem()) {
            for (ShopItem item : world.getShopItems()) {
                if (item.isTouchUp(screenX, screenY)) {
                    switch(item.getState()) {
                        case OWNED:
                            world.switchBear(item.getType());
                            break;
                        case AFFORDABLE:
                        case EQUIPPED:
                            world.showItem(item);
                            break;
                    }
                    return true;
                }
            }
        }

        for (SimpleButton simpleButton : world.getActiveButtons()) {
             if (simpleButton.isTouchUp(screenX, screenY)) {
                 switch(simpleButton.getType()) {
                     case PLAY:
                         world.startGame();
                         break;
                     case BACK:
                         if (world.isShowingItem()) {
                             world.openShop();
                         } else if (state == GameStateEnum.POSTMENUSHOP) {
                             world.showPostMenu();
                         } else {
                             world.goToMenu();
                         }
                         break;
                     case BUY:
                         world.buyItem(world.getItemOnDisplay());
                         break;
                     case MUTE:
                         world.setIsMuted(true);
                         break;
                     case UNMUTE:
                         world.setIsMuted(false);
                         break;
                     case HELP:
                         world.openHelp();
                         break;
                     case CREDITS:
                         world.openCredits();
                         break;
                     case SHOP:
                         world.openShop();
                         break;
                 }
                 return true;
             }
        }
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
