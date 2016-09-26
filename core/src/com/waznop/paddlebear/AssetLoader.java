package com.waznop.paddlebear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.waznop.gameworld.BearEnum;

/**
 * Created by Waznop on 2016-08-22.
 */
public class AssetLoader {

    public static Texture spriteSheet;
    public static Texture riverSheet;
    public static Texture landSheet;
    public static Texture spriteSheet2;

    public static TextureRegion paddlingLeft1;
    public static TextureRegion paddlingLeft2;
    public static TextureRegion paddlingLeft3;
    public static TextureRegion standbyLeft1;
    public static TextureRegion standbyLeft2;
    public static Animation standbyLeftAnimation;

    public static TextureRegion paddlingRight1;
    public static TextureRegion paddlingRight2;
    public static TextureRegion paddlingRight3;
    public static TextureRegion standbyRight1;
    public static TextureRegion standbyRight2;
    public static Animation standbyRightAnimation;

    public static TextureRegion dying1;
    public static TextureRegion dying2;
    public static TextureRegion dying3;
    public static TextureRegion dying4;
    public static TextureRegion dying5;
    public static TextureRegion dying6;
    public static TextureRegion dying7;
    public static TextureRegion dying8;
    public static TextureRegion dying9;
    public static TextureRegion dying10;
    public static TextureRegion dying11;
    public static TextureRegion dying12;
    public static Animation dyingAnimation;

    // background art
    public static TextureRegion backgroundLand;
    public static TextureRegion backgroundSand;
    public static TextureRegion backgroundRiver1;
    public static TextureRegion backgroundRiver2;
    public static Animation backgroundLowAnimation;

    // in game art
    public static TextureRegion shortLog1;
    public static TextureRegion shortLog2;
    public static TextureRegion longLog1;
    public static TextureRegion longLog2;
    public static TextureRegion babyCub1;
    public static TextureRegion babyCub2;
    public static Animation shortLogAnimation;
    public static Animation longLogAnimation;
    public static Animation babyCubAnimation;

    // post menu UI
    public static TextureRegion postGameMenu;
    public static TextureRegion playButtonUp;
    public static TextureRegion playButtonDown;
    public static TextureRegion shopButtonUp;
    public static TextureRegion shopButtonDown;
    public static TextureRegion restartButtonUp;
    public static TextureRegion restartButtonDown;
    public static TextureRegion backButtonUp;
    public static TextureRegion backButtonDown;
    public static TextureRegion buyButtonUp;
    public static TextureRegion buyButtonDown;

    // shop UI
    public static TextureRegion bearCubIcon;
    public static TextureRegion shopMenu;
    public static TextureRegion extraPanel;
    public static TextureRegion shopItemUp;
    public static TextureRegion shopItemDown;
    public static TextureRegion shopItemSelect;
    public static TextureRegion teddyStandby1;
    public static TextureRegion teddyStandby2;
    public static TextureRegion spaddulaStandby1;
    public static TextureRegion spaddulaStandby2;
    public static TextureRegion jacubStandby1;
    public static TextureRegion jacubStandby2;
    public static TextureRegion tsunderStandby1;
    public static TextureRegion tsunderStandby2;
    public static TextureRegion wolfStandby1;
    public static TextureRegion wolfStandby2;
    public static Animation teddyStandbyAnimation;
    public static Animation spaddulaStandbyAnimation;
    public static Animation jacubStandbyAnimation;
    public static Animation tsunderStandbyAnimation;
    public static Animation wolfStandbyAnimation;

    // menu UI
    public static TextureRegion creditsButtonUp;
    public static TextureRegion creditsButtonDown;
    public static TextureRegion helpButtonUp;
    public static TextureRegion helpButtonDown;
    public static TextureRegion cubButtonUp;
    public static TextureRegion cubButtonDown;
    public static TextureRegion muteButtonUp;
    public static TextureRegion muteButtonDown;
    public static TextureRegion unmuteButtonUp;
    public static TextureRegion unmuteButtonDown;
    public static TextureRegion waznopGames;
    public static TextureRegion paddleBearTitle;
    public static TextureRegion paddleBearTitleShade;
    public static TextureRegion titleBear1;
    public static TextureRegion titleBear2;
    public static TextureRegion titleBear3;
    public static TextureRegion titleBear4;
    public static Animation titleBearAnimation;

    // data
    public static Preferences data;

    // font
    public static BitmapFont font;

    // particles
    public static ParticleEffect bearTrail;
    public static ParticleEffect rectTrail;
    public static ParticleEffectPool particlePool;

    // audio
    public static Sound gameOverSound;
    public static Sound paddling1Sound;
    public static Sound paddling2Sound;
    public static Sound pickupSound;
    public static Sound scoreupSound;
    public static Sound clickSound;
    public static Sound noclickSound;
    public static Music menuMusic;
    public static Music gameMusic;

    public static void setSizes(float scaledScreenWidth, float scaledScreenHeight) {
        float whRatio = scaledScreenWidth / scaledScreenHeight;
        int gameWidth = Constants.GAME_WIDTH;
        int gameHeight = Constants.GAME_HEIGHT;
        int screenWidth;
        int screenHeight;
        int gameStartX;
        int gameStartY;
        if (whRatio < Constants.WH_RATIO) {
            screenWidth = gameWidth;
            screenHeight = (int) (screenWidth / whRatio);
            gameStartX = 0;
            gameStartY = (screenHeight - gameHeight) / 2;
        } else {
            screenHeight = gameHeight;
            screenWidth = (int) (screenHeight * whRatio);
            gameStartY = 0;
            gameStartX = (screenWidth - gameWidth) / 2;
        }
        Constants.SCALED_SCREEN_WIDTH = scaledScreenWidth;
        Constants.SCALED_SCREEN_HEIGHT = scaledScreenHeight;
        Constants.SCREEN_WIDTH = screenWidth;
        Constants.SCREEN_HEIGHT = screenHeight;
        Constants.GAME_SCALE = scaledScreenWidth / screenWidth;
        Constants.GAME_START_X = gameStartX;
        Constants.GAME_START_Y = gameStartY;
        Constants.GAME_MID_X = screenWidth / 2;
        Constants.GAME_MID_Y = screenHeight / 2;
    }

    public static void load() {
        spriteSheet = new Texture(Gdx.files.internal("spriteSheet.png"));
        spriteSheet2 = new Texture(Gdx.files.internal("spriteSheet2.png"));
        landSheet = new Texture(Gdx.files.internal("landSheet.png"));
        riverSheet = new Texture(Gdx.files.internal("riverSheet.png"));

        // data
        data = Gdx.app.getPreferences("gameData");
        if (! data.contains("highScore")) {
            data.putInteger("highScore", 0);
        }
        if (! data.contains("karma")) {
            data.putInteger("karma", 0);
        }
        if (! data.contains("isMuted")) {
            data.putBoolean("isMuted", false);
        }
        if (! data.contains("firstTime")) {
            data.putBoolean("firstTime", true);
        }
        if (! data.contains("activeBear")) {
            data.putString("activeBear", Constants.DEFAULT_BEAR.name);
        }
        for (BearEnum bear : BearEnum.values()) {
            String name = bear.name;
            if (! data.contains(name)) {
                data.putBoolean(name, name.equals(Constants.DEFAULT_BEAR.name));
            }
        }
        data.flush();

        // background art
        backgroundLand = new TextureRegion(landSheet, 0, 0, 45, 80);
        backgroundLand.flip(false, true);
        backgroundSand = new TextureRegion(landSheet, 45, 0, 45, 80);
        backgroundSand.flip(false, true);
        backgroundRiver1 = new TextureRegion(riverSheet, 0, 0, 45, 80);
        backgroundRiver1.flip(false, true);
        backgroundRiver2 = new TextureRegion(riverSheet, 45, 0, 45, 80);
        backgroundRiver2.flip(false, true);
        TextureRegion[] backgroundLow = {backgroundRiver1, backgroundRiver2};
        backgroundLowAnimation = new Animation(Constants.PADDLE_TIMER_A, backgroundLow);
        backgroundLowAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // in game art
        shortLog1 = new TextureRegion(spriteSheet, 0, 48, 32, 12);
        shortLog1.flip(false, true);
        shortLog2 = new TextureRegion(spriteSheet, 32, 48, 32, 12);
        shortLog2.flip(false, true);
        longLog1 = new TextureRegion(spriteSheet, 64, 48, 48, 12);
        longLog1.flip(false, true);
        longLog2 = new TextureRegion(spriteSheet, 112, 48, 48, 12);
        longLog2.flip(false, true);
        babyCub1 = new TextureRegion(spriteSheet, 160, 48, 16, 16);
        babyCub1.flip(false, true);
        babyCub2 = new TextureRegion(spriteSheet, 176, 48, 16, 16);
        babyCub2.flip(false, true);
        TextureRegion[] shortLog = {shortLog1, shortLog2};
        shortLogAnimation = new Animation(Constants.PADDLE_TIMER_B, shortLog);
        shortLogAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] longLog = {longLog1, longLog2};
        longLogAnimation = new Animation(Constants.PADDLE_TIMER_B, longLog);
        longLogAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] babyCub = {babyCub1, babyCub2};
        babyCubAnimation = new Animation(Constants.PADDLE_TIMER_C, babyCub);
        babyCubAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // post menu UI
        postGameMenu = new TextureRegion(spriteSheet2, 82, 0, 23, 51);
        postGameMenu.flip(false, true);
        playButtonUp = new TextureRegion(spriteSheet2, 82, 64, 23, 15);
        playButtonUp.flip(false, true);
        playButtonDown = new TextureRegion(spriteSheet2, 105, 64, 23, 15);
        playButtonDown.flip(false, true);
        shopButtonUp = new TextureRegion(spriteSheet2, 43, 30, 19, 9);
        shopButtonUp.flip(false, true);
        shopButtonDown = new TextureRegion(spriteSheet2, 62, 30, 19, 9);
        shopButtonDown.flip(false, true);
        restartButtonUp = new TextureRegion(spriteSheet2, 43, 39, 19, 9);
        restartButtonUp.flip(false, true);
        restartButtonDown = new TextureRegion(spriteSheet2, 62, 39, 19, 9);
        restartButtonDown.flip(false, true);
        backButtonUp = new TextureRegion(spriteSheet2, 43, 48, 19, 9);
        backButtonUp.flip(false, true);
        backButtonDown = new TextureRegion(spriteSheet2, 62, 48, 19, 9);
        backButtonDown.flip(false, true);
        buyButtonUp = new TextureRegion(spriteSheet2, 43, 57, 19, 9);
        buyButtonUp.flip(false, true);
        buyButtonDown = new TextureRegion(spriteSheet2, 62, 57, 19, 9);
        buyButtonDown.flip(false, true);

        // shop UI
        bearCubIcon = new TextureRegion(spriteSheet2, 43, 66, 7, 6);
        bearCubIcon.flip(false, true);
        shopMenu = new TextureRegion(spriteSheet2, 0, 0, 43, 78);
        shopMenu.flip(false, true);
        extraPanel = new TextureRegion(spriteSheet2, 105, 0, 39, 39);
        extraPanel.flip(false, true);
        shopItemUp = new TextureRegion(spriteSheet2, 43, 0, 39, 10);
        shopItemUp.flip(false, true);
        shopItemDown = new TextureRegion(spriteSheet2, 43, 10, 39, 10);
        shopItemDown.flip(false, true);
        shopItemSelect = new TextureRegion(spriteSheet2, 43, 20, 39, 10);
        shopItemSelect.flip(false, true);
        teddyStandby1 = new TextureRegion(spriteSheet, 48, 0, 16, 16);
        teddyStandby1.flip(false, true);
        teddyStandby2 = new TextureRegion(spriteSheet, 64, 0, 16, 16);
        teddyStandby2.flip(false, true);
        spaddulaStandby1 = new TextureRegion(spriteSheet, 48, 16, 16, 16);
        spaddulaStandby1.flip(false, true);
        spaddulaStandby2 = new TextureRegion(spriteSheet, 64, 16, 16, 16);
        spaddulaStandby2.flip(false, true);
        jacubStandby1 = new TextureRegion(spriteSheet, 48, 32, 16, 16);
        jacubStandby1.flip(false, true);
        jacubStandby2 = new TextureRegion(spriteSheet, 64, 32, 16, 16);
        jacubStandby2.flip(false, true);
        tsunderStandby1 = new TextureRegion(spriteSheet, 128, 0, 16, 16);
        tsunderStandby1.flip(false, true);
        tsunderStandby2 = new TextureRegion(spriteSheet, 144, 0, 16, 16);
        tsunderStandby2.flip(false, true);
        wolfStandby1 = new TextureRegion(spriteSheet, 128, 16, 16, 16);
        wolfStandby1.flip(false, true);
        wolfStandby2 = new TextureRegion(spriteSheet, 144, 16, 16, 16);
        wolfStandby2.flip(false, true);
        TextureRegion[] teddyStandby = {teddyStandby1, teddyStandby2};
        teddyStandbyAnimation = new Animation(Constants.PADDLE_TIMER_C, teddyStandby);
        teddyStandbyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] spaddulaStandby = {spaddulaStandby1, spaddulaStandby2};
        spaddulaStandbyAnimation = new Animation(Constants.PADDLE_TIMER_C, spaddulaStandby);
        spaddulaStandbyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] jacubStandby = {jacubStandby1, jacubStandby2};
        jacubStandbyAnimation = new Animation(Constants.PADDLE_TIMER_C, jacubStandby);
        jacubStandbyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] tsunderStandby = {tsunderStandby1, tsunderStandby2};
        tsunderStandbyAnimation = new Animation(Constants.PADDLE_TIMER_C, tsunderStandby);
        tsunderStandbyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] wolfStandby = {wolfStandby1, wolfStandby2};
        wolfStandbyAnimation = new Animation(Constants.PADDLE_TIMER_C, wolfStandby);
        wolfStandbyAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // menu UI
        helpButtonUp = new TextureRegion(spriteSheet, 160, 0, 16, 16);
        helpButtonUp.flip(false, true);
        helpButtonDown = new TextureRegion(spriteSheet, 176, 0, 16, 16);
        helpButtonDown.flip(false, true);
        creditsButtonUp = new TextureRegion(spriteSheet, 160, 16, 16, 16);
        creditsButtonUp.flip(false, true);
        creditsButtonDown = new TextureRegion(spriteSheet, 176, 16, 16, 16);
        creditsButtonDown.flip(false, true);
        cubButtonUp = new TextureRegion(spriteSheet, 160, 32, 16, 16);
        cubButtonUp.flip(false, true);
        cubButtonDown = new TextureRegion(spriteSheet, 176, 32, 16, 16);
        cubButtonDown.flip(false, true);
        muteButtonUp = new TextureRegion(spriteSheet, 96, 32, 16, 16);
        muteButtonUp.flip(false, true);
        muteButtonDown = new TextureRegion(spriteSheet, 112, 32, 16, 16);
        muteButtonDown.flip(false, true);
        unmuteButtonUp = new TextureRegion(spriteSheet, 128, 32, 16, 16);
        unmuteButtonUp.flip(false, true);
        unmuteButtonDown = new TextureRegion(spriteSheet, 144, 32, 16, 16);
        unmuteButtonDown.flip(false, true);
        waznopGames = new TextureRegion(spriteSheet2, 113, 39, 31, 15);
        paddleBearTitle = new TextureRegion(spriteSheet2, 50, 66, 25, 13);
        paddleBearTitle.flip(false, true);
        paddleBearTitleShade = new TextureRegion(spriteSheet2, 82, 51, 25, 13);
        paddleBearTitleShade.flip(false, true);
        titleBear1 = new TextureRegion(spriteSheet2, 144, 0, 25, 17);
        titleBear1.flip(false, true);
        titleBear2 = new TextureRegion(spriteSheet2, 144, 17, 25, 17);
        titleBear2.flip(false, true);
        titleBear3 = new TextureRegion(spriteSheet2, 144, 34, 25, 17);
        titleBear3.flip(false, true);
        titleBear4 = new TextureRegion(spriteSheet2, 144, 51, 25, 17);
        titleBear4.flip(false, true);
        TextureRegion[] titleBear = {titleBear1, titleBear2, titleBear3, titleBear4};
        titleBearAnimation = new Animation(Constants.PADDLE_TIMER_A * 1.5f, titleBear);
        titleBearAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // bear art
        paddlingLeft1 = new TextureRegion();
        paddlingLeft1.setTexture(spriteSheet);
        paddlingRight1 = new TextureRegion();
        paddlingLeft2 = new TextureRegion();
        paddlingLeft2.setTexture(spriteSheet);
        paddlingRight2 = new TextureRegion();
        paddlingLeft3 = new TextureRegion();
        paddlingLeft3.setTexture(spriteSheet);
        paddlingRight3 = new TextureRegion();
        standbyLeft1 = new TextureRegion();
        standbyLeft1.setTexture(spriteSheet);
        standbyRight1 = new TextureRegion();
        standbyLeft2 = new TextureRegion();
        standbyLeft2.setTexture(spriteSheet);
        standbyRight2 = new TextureRegion();
        dying1 = new TextureRegion();
        dying1.setTexture(spriteSheet);
        dying2 = new TextureRegion();
        dying2.setTexture(spriteSheet);
        dying3 = new TextureRegion();
        dying3.setTexture(spriteSheet);
        dying4 = new TextureRegion();
        dying4.setTexture(spriteSheet);
        dying5 = new TextureRegion();
        dying5.setTexture(spriteSheet);
        dying6 = new TextureRegion();
        dying6.setTexture(spriteSheet);
        dying7 = new TextureRegion();
        dying7.setTexture(spriteSheet);
        dying8 = new TextureRegion();
        dying8.setTexture(spriteSheet);
        dying9 = new TextureRegion();
        dying9.setTexture(spriteSheet);
        dying10 = new TextureRegion();
        dying10.setTexture(spriteSheet);
        dying11 = new TextureRegion();
        dying11.setTexture(spriteSheet);
        dying12 = new TextureRegion();
        dying12.setTexture(spriteSheet);
        reloadBear(HelperFunctions.getBearEnumFromString(data.getString("activeBear")));
        TextureRegion[] standbyLeft = {standbyLeft1, standbyLeft2};
        standbyLeftAnimation = new Animation(Constants.PADDLE_TIMER_C, standbyLeft);
        standbyLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] standbyRight = {standbyRight1, standbyRight2};
        standbyRightAnimation = new Animation(Constants.PADDLE_TIMER_C, standbyRight);
        standbyRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] dying = {dying1, dying2, dying3, dying4, dying5, dying6,
                dying7, dying8, dying9, dying10, dying11, dying12};
        dyingAnimation = new Animation(0.05f, dying);

        // font
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(0.5f, -0.5f);

        // particles
        bearTrail = new ParticleEffect();
        bearTrail.load(Gdx.files.internal("bearTrail.p"), Gdx.files.internal(""));
        rectTrail = new ParticleEffect();
        rectTrail.load(Gdx.files.internal("rectTrail.p"), Gdx.files.internal(""));
        particlePool = new ParticleEffectPool(rectTrail,
                Constants.PARTICLE_POOL_SIZE, Constants.PARTICLE_POOL_SIZE);

        // audio
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("pickup.ogg"));
        scoreupSound = Gdx.audio.newSound(Gdx.files.internal("scoreup.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
        noclickSound = Gdx.audio.newSound(Gdx.files.internal("noclick.wav"));
        paddling1Sound = Gdx.audio.newSound(Gdx.files.internal("paddling1.ogg"));
        paddling2Sound = Gdx.audio.newSound(Gdx.files.internal("paddling2.ogg"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("rowYourBoat.mp3"));
        menuMusic.setLooping(true);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("everydayImPaddling.mp3"));
        gameMusic.setLooping(true);
    }

    public static void reloadBear(BearEnum bear) {
        int aliveX;
        int aliveY;
        int dyingX;
        int dyingY;

        switch(bear) {
            case SPADDULA:
                aliveX = 0;
                aliveY = 16;
                dyingX = 0;
                dyingY = 80;
                standbyLeft1.setRegion(spaddulaStandby1);
                standbyLeft2.setRegion(spaddulaStandby2);
                break;
            case JACUB:
                aliveX = 0;
                aliveY = 32;
                dyingX = 0;
                dyingY = 96;
                standbyLeft1.setRegion(jacubStandby1);
                standbyLeft2.setRegion(jacubStandby2);
                break;
            case TSUNDER:
                aliveX = 80;
                aliveY = 0;
                dyingX = 0;
                dyingY = 112;
                standbyLeft1.setRegion(tsunderStandby1);
                standbyLeft2.setRegion(tsunderStandby2);
                break;
            case WOLF:
                aliveX = 80;
                aliveY = 16;
                dyingX = 0;
                dyingY = 128;
                standbyLeft1.setRegion(wolfStandby1);
                standbyLeft2.setRegion(wolfStandby2);
                break;
            case TEDDY:
            default:
                aliveX = 0;
                aliveY = 0;
                dyingX = 0;
                dyingY = 64;
                standbyLeft1.setRegion(teddyStandby1);
                standbyLeft2.setRegion(teddyStandby2);
        }

        paddlingLeft1.setRegion(aliveX, aliveY, 16, 16);
        paddlingRight1.setRegion(paddlingLeft1);
        paddlingLeft1.flip(false, true);
        paddlingRight1.flip(true, true);
        paddlingLeft2.setRegion(aliveX + 16, aliveY, 16, 16);
        paddlingRight2.setRegion(paddlingLeft2);
        paddlingLeft2.flip(false, true);
        paddlingRight2.flip(true, true);
        paddlingLeft3.setRegion(aliveX + 32, aliveY, 16, 16);
        paddlingRight3.setRegion(paddlingLeft3);
        paddlingLeft3.flip(false, true);
        paddlingRight3.flip(true, true);
        standbyRight1.setRegion(standbyLeft1);
        standbyRight1.flip(true, false);
        standbyRight2.setRegion(standbyLeft2);
        standbyRight2.flip(true, false);
        dying1.setRegion(dyingX, dyingY, 16, 16);
        dying1.flip(false, true);
        dying2.setRegion(dyingX + 16, dyingY, 16, 16);
        dying2.flip(false, true);
        dying3.setRegion(dyingX + 32, dyingY, 16, 16);
        dying3.flip(false, true);
        dying4.setRegion(dyingX + 48, dyingY, 16, 16);
        dying4.flip(false, true);
        dying5.setRegion(dyingX + 64, dyingY, 16, 16);
        dying5.flip(false, true);
        dying6.setRegion(dyingX + 80, dyingY, 16, 16);
        dying6.flip(false, true);
        dying7.setRegion(dyingX + 96, dyingY, 16, 16);
        dying7.flip(false, true);
        dying8.setRegion(dyingX + 112, dyingY, 16, 16);
        dying8.flip(false, true);
        dying9.setRegion(dyingX + 128, dyingY, 16, 16);
        dying9.flip(false, true);
        dying10.setRegion(dyingX + 144, dyingY, 16, 16);
        dying10.flip(false, true);
        dying11.setRegion(dyingX + 160, dyingY, 16, 16);
        dying11.flip(false, true);
        dying12.setRegion(dyingX + 176, dyingY, 16, 16);
        dying12.flip(false, true);
    }

    public static void dispose() {
        spriteSheet.dispose();
        spriteSheet2.dispose();
        riverSheet.dispose();
        landSheet.dispose();
        font.dispose();
        bearTrail.dispose();
        rectTrail.dispose();
        gameOverSound.dispose();
        pickupSound.dispose();
        scoreupSound.dispose();
        clickSound.dispose();
        noclickSound.dispose();
        paddling1Sound.dispose();
        paddling2Sound.dispose();
        menuMusic.dispose();
        gameMusic.dispose();
    }

}
