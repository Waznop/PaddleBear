package com.waznop.paddlebear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by Waznop on 2016-08-22.
 */
public class AssetLoader {

    public static Texture spriteSheet;
    public static Texture riverSheet;
    public static Texture landSheet;

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

    public static TextureRegion backgroundLand;
    public static TextureRegion backgroundSand;
    public static TextureRegion backgroundRiver1;
    public static TextureRegion backgroundRiver2;
    public static Animation backgroundLowAnimation;

    public static TextureRegion shortLog1;
    public static TextureRegion shortLog2;
    public static TextureRegion longLog1;
    public static TextureRegion longLog2;
    public static TextureRegion babyCub1;
    public static TextureRegion babyCub2;
    public static Animation shortLogAnimation;
    public static Animation longLogAnimation;
    public static Animation babyCubAnimation;

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

    public static Preferences data;

    public static BitmapFont font;

    public static void load() {
        spriteSheet = new Texture(Gdx.files.internal("spriteSheet.png"));
        landSheet = new Texture(Gdx.files.internal("landSheet.png"));
        riverSheet = new Texture(Gdx.files.internal("riverSheet.png"));

        paddlingLeft1 = new TextureRegion(spriteSheet, 0, 0, 16, 16);
        paddlingRight1 = new TextureRegion(paddlingLeft1);
        paddlingLeft1.flip(false, true);
        paddlingRight1.flip(true, true);

        paddlingLeft2 = new TextureRegion(spriteSheet, 0, 16, 16, 16);
        paddlingRight2 = new TextureRegion(paddlingLeft2);
        paddlingLeft2.flip(false, true);
        paddlingRight2.flip(true, true);

        paddlingLeft3 = new TextureRegion(spriteSheet, 0, 32, 16, 16);
        paddlingRight3 = new TextureRegion(paddlingLeft3);
        paddlingLeft3.flip(false, true);
        paddlingRight3.flip(true, true);

        standbyLeft1 = new TextureRegion(spriteSheet, 0, 48, 16, 16);
        standbyRight1 = new TextureRegion(standbyLeft1);
        standbyLeft1.flip(false, true);
        standbyRight1.flip(true, true);

        standbyLeft2 = new TextureRegion(spriteSheet, 0, 64, 16, 16);
        standbyRight2 = new TextureRegion(standbyLeft2);
        standbyLeft2.flip(false, true);
        standbyRight2.flip(true, true);

        TextureRegion[] standbyLeft = {standbyLeft1, standbyLeft2};
        standbyLeftAnimation = new Animation(Constants.PADDLE_TIMER_C, standbyLeft);
        standbyLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[] standbyRight = {standbyRight1, standbyRight2};
        standbyRightAnimation = new Animation(Constants.PADDLE_TIMER_C, standbyRight);
        standbyRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

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

        shortLog1 = new TextureRegion(spriteSheet, 0, 83, 32, 12);
        shortLog1.flip(false, true);
        shortLog2 = new TextureRegion(spriteSheet, 32, 83, 32, 12);
        shortLog2.flip(false, true);
        longLog1 = new TextureRegion(spriteSheet, 64, 83, 48, 12);
        longLog1.flip(false, true);
        longLog2 = new TextureRegion(spriteSheet, 112, 83, 48, 12);
        longLog2.flip(false, true);
        babyCub1 = new TextureRegion(spriteSheet, 160, 80, 16, 16);
        babyCub1.flip(false, true);
        babyCub2 = new TextureRegion(spriteSheet, 176, 80, 16, 16);
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

        dying1 = new TextureRegion(spriteSheet, 0, 96, 16, 16);
        dying1.flip(false, true);
        dying2 = new TextureRegion(spriteSheet, 16, 96, 16, 16);
        dying2.flip(false, true);
        dying3 = new TextureRegion(spriteSheet, 32, 96, 16, 16);
        dying3.flip(false, true);
        dying4 = new TextureRegion(spriteSheet, 48, 96, 16, 16);
        dying4.flip(false, true);
        dying5 = new TextureRegion(spriteSheet, 64, 96, 16, 16);
        dying5.flip(false, true);
        dying6 = new TextureRegion(spriteSheet, 80, 96, 16, 16);
        dying6.flip(false, true);
        dying7 = new TextureRegion(spriteSheet, 96, 96, 16, 16);
        dying7.flip(false, true);
        dying8 = new TextureRegion(spriteSheet, 112, 96, 16, 16);
        dying8.flip(false, true);
        dying9 = new TextureRegion(spriteSheet, 128, 96, 16, 16);
        dying9.flip(false, true);
        dying10 = new TextureRegion(spriteSheet, 144, 96, 16, 16);
        dying10.flip(false, true);
        dying11 = new TextureRegion(spriteSheet, 160, 96, 16, 16);
        dying11.flip(false, true);
        dying12 = new TextureRegion(spriteSheet, 176, 96, 16, 16);
        dying12.flip(false, true);

        TextureRegion[] dying = {dying1, dying2, dying3, dying4, dying5, dying6,
                dying7, dying8, dying9, dying10, dying11, dying12};
        dyingAnimation = new Animation(0.05f, dying);

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(0.6f, -0.6f);

        data = Gdx.app.getPreferences("gameData");

        if (! data.contains("highScore")) {
            data.putInteger("highScore", 0);
        }

        if (! data.contains("karma")) {
            data.putInteger("karma", 0);
        }
    }

    public static void dispose() {
        spriteSheet.dispose();
        riverSheet.dispose();
        landSheet.dispose();
        font.dispose();
    }

}
