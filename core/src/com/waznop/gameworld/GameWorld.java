package com.waznop.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.waznop.gameobjects.Bear;
import com.waznop.gameobjects.Floater;
import com.waznop.gameobjects.ShopItem;
import com.waznop.paddlebear.*;
import com.waznop.gameobjects.SimpleButton;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameWorld {

    private Bear bear;
    private ScrollHandler scrollHandler;
    private Spawner spawner;
    private boolean fastforwarding;
    private float endGameTimer;
    private GameStateEnum currentState;
    private BearEnum activeBear;

    private Preferences data;
    private int karma;
    private int deltaKarma;
    private int score;
    private int highScore;
    private boolean isMuted;
    private Music currentMusic;

    private ArrayList<SimpleButton> activeButtons;
    private SimpleButton playButton;
    private SimpleButton shopButton;
    private SimpleButton restartButton;
    private SimpleButton backButton;
    private SimpleButton creditsButton;
    private SimpleButton helpButton;
    private SimpleButton cubButton;
    private SimpleButton muteButton;
    private SimpleButton unmuteButton;
    private SimpleButton shopBackButton;
    private SimpleButton shopDetailsBackButton;
    private SimpleButton buyButton;

    private ArrayList<ShopItem> shopItems;
    private ShopItem shopTeddy;
    private ShopItem shopSpaddula;
    private ShopItem shopJacub;
    private ShopItem shopTsunder;
    private ShopItem shopWolf;
    private ShopItem itemOnDisplay;

    public GameWorld() {
        currentState = GameStateEnum.MENU;
        bear = new Bear(this,
                Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2,
                Constants.BEAR_SIZE,
                Constants.BEAR_SIZE);
        scrollHandler = new ScrollHandler(this);
        spawner = new Spawner();
        score = 0;
        endGameTimer = 0;
        fastforwarding = false;

        data = AssetLoader.data;
        karma = data.getInteger("karma");
        deltaKarma = 0;
        highScore = data.getInteger("highScore");
        activeBear = HelperFunctions.getBearEnumFromString(data.getString("activeBear"));

        // buttons
        TextureRegion playButtonUp = AssetLoader.playButtonUp;
        TextureRegion shopButtonUp = AssetLoader.shopButtonUp;
        TextureRegion restartButtonUp = AssetLoader.restartButtonUp;
        TextureRegion backButtonUp = AssetLoader.backButtonUp;
        TextureRegion buyButtonUp = AssetLoader.buyButtonUp;

        playButton = new SimpleButton(ButtonTypeEnum.PLAY, this,
                Constants.GAME_MID_X - playButtonUp.getRegionWidth() * 1.5f,
                Constants.GAME_MID_Y + Constants.PLAY_BUTTON_OFFSET_Y,
                playButtonUp.getRegionWidth() * 3, (playButtonUp.getRegionHeight() - 3) * 3,
                playButtonUp, AssetLoader.playButtonDown,
                playButtonUp.getRegionWidth() * 3, playButtonUp.getRegionHeight() * 3);

        float postMenuButtonX = Constants.GAME_MID_X - shopButtonUp.getRegionWidth() * 1.2f;
        float postMenuButtonWidth = shopButtonUp.getRegionWidth() * 2.4f;
        float postMenuButtonHeight = shopButtonUp.getRegionHeight() * 2;

        shopButton = new SimpleButton(ButtonTypeEnum.SHOP, this,
                postMenuButtonX, Constants.GAME_MID_Y + Constants.SHOP_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, shopButtonUp, AssetLoader.shopButtonDown);
        restartButton = new SimpleButton(ButtonTypeEnum.PLAY, this,
                postMenuButtonX, Constants.GAME_MID_Y + Constants.RESTART_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, restartButtonUp, AssetLoader.restartButtonDown);
        backButton = new SimpleButton(ButtonTypeEnum.BACK, this,
                postMenuButtonX, Constants.GAME_MID_Y + Constants.BACK_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, backButtonUp, AssetLoader.backButtonDown);
        shopBackButton = new SimpleButton(ButtonTypeEnum.BACK, this,
                postMenuButtonX, Constants.GAME_START_Y + Constants.SHOP_BACK_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, backButtonUp, AssetLoader.backButtonDown);
        shopDetailsBackButton = new SimpleButton(ButtonTypeEnum.BACK, this,
                Constants.GAME_MID_X - Constants.SHOP_DETAILS_BUTTON_OFFSET_X - postMenuButtonWidth,
                Constants.GAME_MID_Y + Constants.SHOP_DETAILS_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, backButtonUp, AssetLoader.backButtonDown);
        buyButton = new SimpleButton(ButtonTypeEnum.BUY, this,
                Constants.GAME_MID_X + Constants.SHOP_DETAILS_BUTTON_OFFSET_X,
                Constants.GAME_MID_Y + Constants.SHOP_DETAILS_BUTTON_OFFSET_Y,
                postMenuButtonWidth, postMenuButtonHeight, buyButtonUp, AssetLoader.buyButtonDown);

        helpButton = new SimpleButton(ButtonTypeEnum.HELP, this,
                0, Constants.GAME_START_Y,
                24, 24, AssetLoader.helpButtonUp, AssetLoader.helpButtonDown);
        creditsButton = new SimpleButton(ButtonTypeEnum.CREDITS, this,
                Constants.SCREEN_WIDTH - 24, Constants.GAME_START_Y,
                24, 24, AssetLoader.creditsButtonUp, AssetLoader.creditsButtonDown);
        cubButton = new SimpleButton(ButtonTypeEnum.SHOP, this,
                0, Constants.GAME_START_Y + Constants.GAME_HEIGHT - 24,
                24, 24, AssetLoader.cubButtonUp, AssetLoader.cubButtonDown);
        muteButton = new SimpleButton(ButtonTypeEnum.MUTE, this,
                Constants.SCREEN_WIDTH - 24,
                Constants.GAME_START_Y + Constants.GAME_HEIGHT - 24,
                24, 24, AssetLoader.muteButtonUp, AssetLoader.muteButtonDown);
        unmuteButton = new SimpleButton(ButtonTypeEnum.UNMUTE, this,
                Constants.SCREEN_WIDTH - 24,
                Constants.GAME_START_Y + Constants.GAME_HEIGHT - 24,
                24, 24, AssetLoader.unmuteButtonUp, AssetLoader.unmuteButtonDown);

        activeButtons = new ArrayList<SimpleButton>();
        activeButtons.add(playButton);
        activeButtons.add(helpButton);
        activeButtons.add(creditsButton);
        activeButtons.add(cubButton);

        // shop

        TextureRegion shopItemUp = AssetLoader.shopItemUp;
        float shopItemX = Constants.GAME_MID_X - shopItemUp.getRegionWidth() * 1.5f;
        float shopItemWidth = shopItemUp.getRegionWidth() * 3;
        float shopItemHeight = shopItemUp.getRegionHeight() * 3;
        shopTeddy = new ShopItem(BearEnum.TEDDY, this, Constants.TEDDY_NAME,
                Constants.TEDDY_DESCRIPTION, Constants.SHOP_TEDDY_PRICE,
                shopItemX, Constants.GAME_MID_Y + Constants.SHOP_TEDDY_OFFSET_Y,
                shopItemWidth, shopItemHeight);
        shopSpaddula = new ShopItem(BearEnum.SPADDULA, this, Constants.SPADDULA_NAME,
                Constants.SPADDULA_DESCRIPTION, Constants.SHOP_SPADDULA_PRICE,
                shopItemX, Constants.GAME_MID_Y + Constants.SHOP_SPADDULA_OFFSET_Y,
                shopItemWidth, shopItemHeight);
        shopJacub = new ShopItem(BearEnum.JACUB, this, Constants.JACUB_NAME,
                Constants.JACUB_DESCRIPTION, Constants.SHOP_JACUB_PRICE,
                shopItemX, Constants.GAME_MID_Y + Constants.SHOP_JACUB_OFFSET_Y,
                shopItemWidth, shopItemHeight);
        shopTsunder = new ShopItem(BearEnum.TSUNDER, this, Constants.TSUNDER_NAME,
                Constants.TSUNDER_DESCRIPTION, Constants.SHOP_TSUNDER_PRICE,
                shopItemX, Constants.GAME_MID_Y + Constants.SHOP_TSUNDER_OFFSET_Y,
                shopItemWidth, shopItemHeight);
        shopWolf = new ShopItem(BearEnum.WOLF, this, Constants.WOLF_NAME,
                Constants.WOLF_DESCRIPTION, Constants.SHOP_WOLF_PRICE,
                shopItemX, Constants.GAME_MID_Y + Constants.SHOP_WOLF_OFFSET_Y,
                shopItemWidth, shopItemHeight);
        shopItems = new ArrayList<ShopItem>();
        shopItems.add(shopTeddy);
        shopItems.add(shopSpaddula);
        shopItems.add(shopJacub);
        shopItems.add(shopTsunder);
        shopItems.add(shopWolf);

        itemOnDisplay = null;

        // music
        currentMusic = AssetLoader.menuMusic;
        isMuted = data.getBoolean("isMuted");
        if (isMuted) {
            activeButtons.add(unmuteButton);
        } else {
            activeButtons.add(muteButton);
            currentMusic.play();
        }

        if (data.getBoolean("firstTime")) {
            openHelp();
            data.putBoolean("firstTime", false);
            data.flush();
        }
    }

    public void refreshShopStates() {
        for (ShopItem item : shopItems) {
            BearEnum bearType = item.getType();
            if (bearType == activeBear) {
                item.setState(ShopItemStateEnum.EQUIPPED);
            } else if (data.getBoolean(bearType.name)) {
                item.setState(ShopItemStateEnum.OWNED);
            } else if (karma >= item.getPrice()) {
                item.setState(ShopItemStateEnum.AFFORDABLE);
            } else {
                item.setState(ShopItemStateEnum.UNOWNED);
            }
        }
    }

    public void startGame() {
        currentState = GameStateEnum.PLAYING;
        score = 0;
        deltaKarma = 0;
        bear.reset(Constants.GAME_MID_X - Constants.BEAR_SIZE / 2,
                Constants.GAME_MID_Y - Constants.BEAR_SIZE / 2);
        scrollHandler.reset();
        fastforwarding = false;
        spawner.reset();
        activeButtons.clear();
        currentMusic.stop();
        currentMusic = AssetLoader.gameMusic;
        if (! isMuted) {
            currentMusic.play();
        }
    }

    private void endGame() {
        endGameTimer = Constants.END_GAME_TIME;
        InputHandler.touchSection = TouchScreenEnum.NONE;
        bear.die();
        saveKarma();
        if (score > highScore) {
            setHighScore(score);
        }
        currentState = GameStateEnum.GAMEOVER;
        currentMusic.stop();
        if (! isMuted) {
            AssetLoader.gameOverSound.play(0.5f);
        }
    }

    public void showPostMenu() {
        currentState = GameStateEnum.POSTMENU;
        activeButtons.clear();
        activeButtons.add(shopButton);
        activeButtons.add(restartButton);
        activeButtons.add(backButton);
        currentMusic = AssetLoader.menuMusic;
        if (! isMuted && ! currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    public void goToMenu() {
        currentState = GameStateEnum.MENU;
        scrollHandler.reset();
        activeButtons.clear();
        activeButtons.add(playButton);
        activeButtons.add(helpButton);
        activeButtons.add(creditsButton);
        activeButtons.add(cubButton);
        if (isMuted) {
            activeButtons.add(unmuteButton);
        } else {
            activeButtons.add(muteButton);
        }
    }

    public void openShop() {
        if (currentState == GameStateEnum.MENU) {
            currentState = GameStateEnum.MENUSHOP;
        } else if (currentState == GameStateEnum.POSTMENU) {
            currentState = GameStateEnum.POSTMENUSHOP;
        }

        if (isShowingItem()) {
            itemOnDisplay = null;
        }

        activeButtons.clear();
        activeButtons.add(shopBackButton);
        refreshShopStates();
    }

    public void openCredits() {
        currentState = GameStateEnum.CREDITS;
        activeButtons.clear();
        activeButtons.add(shopBackButton);
    }

    public void openHelp() {
        currentState = GameStateEnum.HELP;
        activeButtons.clear();
        activeButtons.add(shopBackButton);
    }

    public void showItem(ShopItem item) {
        itemOnDisplay = item;
        float buttonWidth = AssetLoader.backButtonUp.getRegionWidth() * 2.4f;
        if (item.getState() == ShopItemStateEnum.EQUIPPED) {
            shopDetailsBackButton.setPosition(Constants.GAME_MID_X - buttonWidth / 2,
                    Constants.GAME_MID_Y + Constants.SHOP_DETAILS_BUTTON_OFFSET_Y);
        } else {
            shopDetailsBackButton.setPosition(
                    Constants.GAME_MID_X - Constants.SHOP_DETAILS_BUTTON_OFFSET_X - buttonWidth,
                    Constants.GAME_MID_Y + Constants.SHOP_DETAILS_BUTTON_OFFSET_Y);
            activeButtons.add(buyButton);
        }
        activeButtons.add(shopDetailsBackButton);
    }

    public ShopItem getItemOnDisplay() {
        return itemOnDisplay;
    }

    public boolean isShowingItem() {
        return itemOnDisplay != null;
    }

    public void buyItem(ShopItem item) {
        if (isShowingItem()) {
            itemOnDisplay = null;
            if (item.getState() == ShopItemStateEnum.AFFORDABLE) {
                activeButtons.remove(buyButton);
            }
            activeButtons.remove(shopDetailsBackButton);
        }

        int price = item.getPrice();
        if (karma < price) {
            return;
        }

        karma -= price;
        BearEnum bearType = item.getType();
        data.putBoolean(bearType.name, true);
        data.putInteger("karma", karma);
        switchBear(bearType);
    }

    public void switchBear(BearEnum bear) {
        if (activeBear == bear) {
            return;
        }

        activeBear = bear;
        data.putString("activeBear", bear.name);
        data.flush();
        AssetLoader.reloadBear(bear);
        refreshShopStates();
    }

    public void update(float delta) {
        switch(currentState) {
            case MENU:
            case MENUSHOP:
            case POSTMENUSHOP:
            case CREDITS:
            case HELP:
                updateIdle(delta);
                break;
            case PLAYING:
                updatePlaying(delta);
                break;
            case GAMEOVER:
                updateGameover(delta);
                break;
            case POSTMENU:
                updateIdle(delta);
                bear.updateGameover(delta);
                break;
        }
    }

    public void updateIdle(float delta) {
        scrollHandler.update(delta, - Constants.LAND_SCROLL_Y);
        spawner.update(delta, - Constants.LAND_SCROLL_Y);
    }

    public void updateGameover(float delta) {
        endGameTimer -= delta;
        if (endGameTimer <= 0) {
            showPostMenu();
        }
        updateIdle(delta);
        bear.updateGameover(delta);
    }

    public void updatePlaying(float delta) {
        float centerX = bear.getCenterX();
        float centerY = bear.getCenterY();

        if (centerY < Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_START
                && ! fastforwarding) {
            fastforwarding = true;
            bear.getEmitter().getLife().setHigh(0);
            bear.getEmitter().setAttached(true);
        } else if (centerY > Constants.GAME_START_Y + Constants.GAME_HEIGHT * Constants.FASTFORWARD_END
                && fastforwarding) {
            fastforwarding = false;
            bear.getEmitter().getLife().setHigh(1000);
            bear.getEmitter().setAttached(false);
        }

        if (fastforwarding) {
            bear.updatePlaying(delta, Constants.FASTFORWARD_SCROLL_Y);
            scrollHandler.update(delta, Constants.FASTFORWARD_SCROLL_Y);
            spawner.update(delta, Constants.FASTFORWARD_SCROLL_Y);
        } else {
            bear.updatePlaying(delta);
            scrollHandler.update(delta);
            spawner.update(delta);
        }

        if (centerX - bear.getRadius() < Constants.GAME_START_X + Constants.GAME_LEFT_BOUND
                || centerX + bear.getRadius() > Constants.GAME_START_X + Constants.GAME_RIGHT_BOUND
                || centerY - bear.getRadius() > Constants.GAME_START_Y + Constants.GAME_HEIGHT) {
            if (! bear.getIsInvincible()) {
                endGame();
            }
        }

        ArrayList<Floater> spawnList = spawner.getSpawnList();
        Iterator<Floater> iter = spawnList.iterator();
        while (iter.hasNext()) {
            Floater floater = iter.next();
            if (Intersector.overlapConvexPolygons(floater.getCollider(), bear.getCollider())) {
                if (floater.getType() == FloaterEnum.BABYCUB) {
                    addToKarma(1);
                    if (! isMuted) {
                        AssetLoader.pickupSound.play(0.1f);
                    }
                    floater.die();
                    iter.remove();
                } else {
                    if (! bear.getIsInvincible()) {
                        endGame();
                    }
                }
            }
        }

    }

    public int addToScore(int increment) {
        return score += increment;
    }

    public int addToKarma(int increment) {
        deltaKarma += increment;
        return karma += increment;
    }

    public void saveKarma() {
        data.putInteger("karma", karma);
        data.flush();
    }

    public int getKarma() {
        return karma;
    }

    public int getDeltaKarma() {
        return deltaKarma;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        data.putInteger("highScore", highScore);
        data.flush();
    }

    public int getHighScore() {
        return highScore;
    }

    public Bear getBear() {
        return bear;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public int getScore() {
        return score;
    }

    public GameStateEnum getCurrentState() {
        return currentState;
    }

    public Spawner getSpawner() {
        return spawner;
    }

    public ArrayList<SimpleButton> getActiveButtons() {
        return activeButtons;
    }

    public ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }

    public boolean getIsMuted() {
        return isMuted;
    }

    public void setIsMuted(boolean isMuted) {
        if (this.isMuted == isMuted) {
            return;
        }
        this.isMuted = isMuted;
        if (isMuted) {
            currentMusic.stop();
            activeButtons.remove(muteButton);
            activeButtons.add(unmuteButton);
        } else {
            currentMusic.play();
            activeButtons.remove(unmuteButton);
            activeButtons.add(muteButton);
        }
        data.putBoolean("isMuted", isMuted);
        data.flush();
    }

    public void toggleInvincibility() {
        bear.setIsInvincible(! bear.getIsInvincible());
    }

    public void toggleDebug() {
        Constants.SHOW_DEBUG = ! Constants.SHOW_DEBUG;
    }

    public void resetData() {
        karma = 0;
        highScore = 0;
        data.putInteger("karma", 0);
        data.putInteger("highScore", 0);
        data.putBoolean("isMuted", false);
        data.putBoolean("firstTime", true);
        data.putString("activeBear", Constants.DEFAULT_BEAR.name);
        for (BearEnum bear : BearEnum.values()) {
            String name = bear.name;
            data.putBoolean(name, name.equals(Constants.DEFAULT_BEAR.name));
        }
        data.flush();
    }

}
