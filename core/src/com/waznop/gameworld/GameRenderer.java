package com.waznop.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.waznop.gameobjects.*;
import com.waznop.paddlebear.AssetLoader;
import com.waznop.paddlebear.Constants;
import com.waznop.paddlebear.InputHandler;

import java.util.ArrayList;

/**
 * Created by Waznop on 2016-08-17.
 */
public class GameRenderer {

    // general
    private GameWorld world;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private ScrollHandler scrollHandler;
    private Spawner spawner;

    // background
    private Scrollable river;
    private Scrollable land;
    private Scrollable sand;
    private TextureRegion backgroundLand;
    private TextureRegion backgroundSand;
    private Animation backgroundRiver;

    // bear
    private Bear bear;
    private TextureRegion paddlingLeft1;
    private TextureRegion paddlingLeft2;
    private TextureRegion paddlingLeft3;
    private TextureRegion paddlingRight1;
    private TextureRegion paddlingRight2;
    private TextureRegion paddlingRight3;
    private Animation standbyLeft;
    private Animation standbyRight;
    private Animation dying;
    private Animation teddyStandby;
    private Animation spaddulaStandby;
    private Animation jacubStandby;
    private Animation tsunderStandby;
    private Animation wolfStandby;

    // in game
    private Animation shortLog;
    private Animation longLog;
    private Animation babyCub;

    // UI
    private TextureRegion babyCubIcon;
    private TextureRegion postGameMenu;
    private TextureRegion extraPanel;
    private TextureRegion shopMenu;
    private TextureRegion shopItemUp;
    private TextureRegion shopItemDown;
    private TextureRegion shopItemSelect;
    private TextureRegion titleText;
    private TextureRegion titleBear1;
    private Animation titleBear;


    // font
    private BitmapFont font;

    public GameRenderer(GameWorld world) {
        this.world = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {
        bear = world.getBear();
        scrollHandler = world.getScrollHandler();
        spawner = world.getSpawner();
        river = scrollHandler.getRiver();
        land = scrollHandler.getLand();
        sand = scrollHandler.getSand();
    }

    private void initAssets() {
        paddlingLeft1 = AssetLoader.paddlingLeft1;
        paddlingLeft2 = AssetLoader.paddlingLeft2;
        paddlingLeft3 = AssetLoader.paddlingLeft3;
        paddlingRight1 = AssetLoader.paddlingRight1;
        paddlingRight2 = AssetLoader.paddlingRight2;
        paddlingRight3 = AssetLoader.paddlingRight3;
        standbyLeft = AssetLoader.standbyLeftAnimation;
        standbyRight = AssetLoader.standbyRightAnimation;
        dying = AssetLoader.dyingAnimation;
        teddyStandby = AssetLoader.teddyStandbyAnimation;
        spaddulaStandby = AssetLoader.spaddulaStandbyAnimation;
        jacubStandby = AssetLoader.jacubStandbyAnimation;
        tsunderStandby = AssetLoader.tsunderStandbyAnimation;
        wolfStandby = AssetLoader.wolfStandbyAnimation;

        backgroundLand = AssetLoader.backgroundLand;
        backgroundSand = AssetLoader.backgroundSand;
        backgroundRiver = AssetLoader.backgroundLowAnimation;

        shortLog = AssetLoader.shortLogAnimation;
        longLog = AssetLoader.longLogAnimation;
        babyCub = AssetLoader.babyCubAnimation;

        postGameMenu = AssetLoader.postGameMenu;
        extraPanel = AssetLoader.extraPanel;
        babyCubIcon = AssetLoader.bearCubIcon;
        shopMenu = AssetLoader.shopMenu;
        shopItemUp = AssetLoader.shopItemUp;
        shopItemDown = AssetLoader.shopItemDown;
        shopItemSelect = AssetLoader.shopItemSelect;
        titleText = AssetLoader.paddleBearTitle;
        titleBear1 = AssetLoader.titleBear1;
        titleBear = AssetLoader.titleBearAnimation;

        font = AssetLoader.font;
    }

    private Animation getAnimationByBear(BearEnum type) {
        Animation itemIcon;
        switch(type) {
            case SPADDULA:
                itemIcon = spaddulaStandby;
                break;
            case JACUB:
                itemIcon = jacubStandby;
                break;
            case TSUNDER:
                itemIcon = tsunderStandby;
                break;
            case WOLF:
                itemIcon = wolfStandby;
                break;
            case TEDDY:
            default:
                itemIcon = teddyStandby;
        }
        return itemIcon;
    }

    private void drawLowBackground(float runTime) {
        batcher.disableBlending();
        for (Vector2 position : river.getPositions()) {
            batcher.draw(backgroundRiver.getKeyFrame(runTime),
                    position.x, position.y,
                    river.getWidth(), river.getHeight());
        }
        batcher.enableBlending();
        for (Vector2 position : sand.getPositions()) {
            batcher.draw(backgroundSand,
                    position.x, position.y,
                    sand.getWidth(), sand.getHeight());
        }
    }

    private void drawFloaters(float runTime) {
        ArrayList<Floater> spawnList = spawner.getSpawnList();
        for (int i = 0; i < spawnList.size(); ++i) {
            Floater floater = spawnList.get(i);
            floater.getTrail().draw(batcher);
            switch(floater.getType()) {
                case SHORTLOG:
                    batcher.draw(shortLog.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth() / 2, floater.getHeight() / 2,
                            floater.getWidth(), floater.getHeight(),
                            1.1f, 1.1f, floater.getRotation());
                    break;
                case LONGLOG:
                    batcher.draw(longLog.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth() / 2, floater.getHeight() / 2,
                            floater.getWidth(), floater.getHeight(),
                            1.1f, 1.1f, floater.getRotation());
                    break;
                case BABYCUB:
                    batcher.draw(babyCub.getKeyFrame(runTime),
                            floater.getX(), floater.getY(),
                            floater.getWidth(), floater.getHeight());
            }
        }
    }

    private void drawBear(float runTime) {
        int w = bear.getWidth();
        int h = bear.getHeight();

        TextureRegion image;
        float paddleTimer = bear.getPaddleTimer();
        boolean paddlingLeft = bear.getPaddlingLeft();
        boolean paddlingFront = bear.getPaddlingFront();

        if (! bear.getIsAlive()) {
            image = dying.getKeyFrame(bear.getDyingTimer());
        } else if (paddleTimer <= 0) {
            image = paddlingLeft ? standbyLeft.getKeyFrame(runTime) :
                    standbyRight.getKeyFrame(runTime);
        } else if (paddleTimer > Constants.PADDLE_TIMER_B) {
            image = paddlingFront ?
                    (paddlingLeft ? paddlingLeft1 : paddlingRight1) :
                    (paddlingLeft ? paddlingLeft3 : paddlingRight3);
        } else if (paddleTimer > Constants.PADDLE_TIMER_C) {
            image = paddlingLeft ? paddlingLeft2 : paddlingRight2;
        } else {
            image = paddlingFront ?
                    (paddlingLeft ? paddlingLeft3 : paddlingRight3) :
                    (paddlingLeft ? paddlingLeft1 : paddlingRight1);
        }


        bear.getTrail().draw(batcher);

        batcher.draw(image, bear.getX(), bear.getY(), w / 2, h / 2, w, h, 1, 1, bear.getRotation());
    }

    private void drawHighBackground() {
        for (Vector2 position : land.getPositions()) {
            batcher.draw(backgroundLand,
                    position.x, position.y,
                    land.getWidth(), land.getHeight());
        }
    }

    private void drawUI(float runTime) {
        switch(world.getCurrentState()) {
            case MENU:
                int bearWidth = titleBear1.getRegionWidth();
                int bearHeight = titleBear1.getRegionHeight();
                int textWidth = titleText.getRegionWidth();
                batcher.draw(titleBear.getKeyFrame(runTime),
                        Constants.GAME_MID_X - bearWidth * 1.5f,
                        Constants.GAME_MID_Y - 60,
                        bearWidth * 3, bearHeight * 3);
                batcher.draw(titleText,
                        Constants.GAME_MID_X - textWidth * 1.5f,
                        Constants.GAME_MID_Y + 24,
                        textWidth * 3, titleText.getRegionHeight() * 3);
                break;
            case POSTMENU:
                font.setColor(85/255f, 50/255f, 7/255f, 1);
                batcher.draw(postGameMenu,
                        Constants.GAME_MID_X - postGameMenu.getRegionWidth() * 1.5f,
                        Constants.GAME_MID_Y + Constants.POST_MENU_OFFSET_Y,
                        postGameMenu.getRegionWidth() * 3, postGameMenu.getRegionHeight() * 3);

                font.draw(batcher, "Score",
                        Constants.GAME_MID_X + Constants.POST_MENU_FIELD_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_SCORE_OFFSET_Y);
                font.draw(batcher, "" + world.getScore(),
                        Constants.GAME_MID_X + Constants.POST_MENU_STAT_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_SCORE_OFFSET_Y,
                        0, 4, false);

                font.draw(batcher, "Best",
                        Constants.GAME_MID_X + Constants.POST_MENU_FIELD_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_BEST_OFFSET_Y);
                font.draw(batcher, "" + world.getHighScore(),
                        Constants.GAME_MID_X + Constants.POST_MENU_STAT_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_BEST_OFFSET_Y,
                        0, 4, false);

                batcher.draw(babyCubIcon,
                        Constants.GAME_MID_X + Constants.POST_MENU_FIELD_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_CUB_OFFSET_Y - 2,
                        babyCubIcon.getRegionWidth() * 2, babyCubIcon.getRegionHeight() * 2);
                font.draw(batcher, world.getKarma() + " (" + world.getDeltaKarma() + ")",
                        Constants.GAME_MID_X + Constants.POST_MENU_STAT_OFFSET_X,
                        Constants.GAME_MID_Y + Constants.POST_MENU_CUB_OFFSET_Y,
                        0, 4, false);
                font.setColor(1, 1, 1, 1);
                break;
            case PLAYING:
            case GAMEOVER:
                font.draw(batcher, "Score: " + world.getScore(),
                        10, Constants.GAME_START_Y + 10);
                font.draw(batcher, "+ " + world.getDeltaKarma(),
                        Constants.SCREEN_WIDTH - 22, Constants.GAME_START_Y + 10,
                        0, 4, false);
                batcher.draw(babyCubIcon,
                        Constants.SCREEN_WIDTH - 20, Constants.GAME_START_Y + 10,
                        babyCubIcon.getRegionWidth() * 1.3f, babyCubIcon.getRegionHeight() * 1.3f);
                break;
            case MENUSHOP:
            case POSTMENUSHOP:
                batcher.draw(shopMenu,
                        Constants.GAME_START_X + 3, Constants.GAME_START_Y + 3,
                        shopMenu.getRegionWidth() * 3, shopMenu.getRegionHeight() * 3);

                batcher.draw(babyCubIcon,
                        Constants.GAME_MID_X - 24, Constants.GAME_MID_Y - 83,
                        babyCubIcon.getRegionWidth() * 2, babyCubIcon.getRegionHeight() * 2);
                batcher.draw(babyCubIcon,
                        Constants.GAME_MID_X + 10, Constants.GAME_MID_Y - 83,
                        babyCubIcon.getRegionWidth() * 2, babyCubIcon.getRegionHeight() * 2);
                font.draw(batcher, "" + world.getKarma(),
                        Constants.GAME_MID_X - 8, Constants.GAME_MID_Y - 81, 16, 1, true);

                for (ShopItem item : world.getShopItems()) {
                    float itemX = item.getX();
                    float itemY = item.getY();
                    float itemWidth = item.getWidth();
                    float itemHeight = item.getHeight();
                    batcher.draw(item.getIsPressed() ? shopItemDown : shopItemUp,
                            itemX, itemY, itemWidth, itemHeight);

                    Animation itemIcon = getAnimationByBear(item.getType());
                    batcher.draw(itemIcon.getKeyFrame(runTime), itemX + Constants.SHOP_ICON_OFFSET,
                            itemY + Constants.SHOP_ICON_OFFSET, Constants.BEAR_SIZE, Constants.BEAR_SIZE);

                    font.setColor(85/255f, 50/255f, 7/255f, 1);
                    font.draw(batcher, item.getName(), itemX + 25, itemY + 10);
                    font.setColor(1, 1, 1, 1);

                    switch(item.getState()) {
                        case UNOWNED:
                            font.setColor(1, 0, 0, 1);
                            font.draw(batcher, "" + item.getPrice(), itemX + 89, itemY + 11, 16, 1, true);
                            font.setColor(1, 1, 1, 1);
                            batcher.draw(babyCubIcon,
                                    itemX + 106, itemY + 18,
                                    babyCubIcon.getRegionWidth(), babyCubIcon.getRegionHeight());
                            break;
                        case AFFORDABLE:
                            font.draw(batcher, "" + item.getPrice(), itemX + 89, itemY + 11, 16, 1, true);
                            batcher.draw(babyCubIcon,
                                    itemX + 106, itemY + 18,
                                    babyCubIcon.getRegionWidth(), babyCubIcon.getRegionHeight());
                            break;
                        case EQUIPPED:
                            batcher.draw(shopItemSelect, itemX, itemY, itemWidth, itemHeight);
                        case OWNED:
                            batcher.draw(babyCubIcon,
                                    itemX + 89, itemY + 8,
                                    babyCubIcon.getRegionWidth() * 2.4f, babyCubIcon.getRegionHeight() * 2.4f);
                            break;
                    }
                }

                if (world.isShowingItem()) {
                    int panelWidth = extraPanel.getRegionWidth();
                    int panelHeight = extraPanel.getRegionHeight();
                    float panelStartX = Constants.GAME_MID_X - panelWidth * 1.5f;
                    float panelStartY = Constants.GAME_MID_Y - 57;
                    batcher.draw(extraPanel, panelStartX, panelStartY, panelWidth * 3, panelHeight * 3);
                    ShopItem itemOnDisplay = world.getItemOnDisplay();
                    Animation itemIcon = getAnimationByBear(itemOnDisplay.getType());
                    batcher.draw(itemIcon.getKeyFrame(runTime), panelStartX + 10,
                            panelStartY + 10, Constants.BEAR_SIZE, Constants.BEAR_SIZE);
                    font.draw(batcher, "" + itemOnDisplay.getPrice(), panelStartX + 90,
                            panelStartY + 13, 0, 4, false);
                    batcher.draw(babyCubIcon, panelStartX + 93, panelStartY + 12,
                            babyCubIcon.getRegionWidth() * 2, babyCubIcon.getRegionHeight() * 2);
                    font.setColor(85/255f, 50/255f, 7/255f, 1);
                    font.draw(batcher, itemOnDisplay.getName(), panelStartX + 29, panelStartY + 13);
                    font.draw(batcher, itemOnDisplay.getDescription(),
                            panelStartX + 10, panelStartY + 35, 97, 1, true);
                    String description2 = itemOnDisplay.getState() == ShopItemStateEnum.EQUIPPED ?
                            Constants.ALREADY_OWN_STRING : Constants.CONFIRM_BUY_STRING;
                    font.draw(batcher, description2, panelStartX + 10, panelStartY + 60, 97, 1, true);
                    font.setColor(1, 1, 1, 1);
                }
                break;
            case CREDITS:
                batcher.draw(shopMenu,
                        Constants.GAME_START_X + 3, Constants.GAME_START_Y + 3,
                        shopMenu.getRegionWidth() * 3, shopMenu.getRegionHeight() * 3);
                font.setColor(85/255f, 50/255f, 7/255f, 1);
                font.draw(batcher, Constants.CREDITS_STRING_1,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 40,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_2,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 62,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_3,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 77,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_4,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 92,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_5,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 107,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_6,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 134,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_7,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 156,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_8,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 186,
                        109, 1, true);
                font.draw(batcher, Constants.CREDITS_STRING_9,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 210,
                        109, 1, true);
                font.setColor(1, 1, 1, 1);
                break;
            case HELP:
                batcher.draw(shopMenu,
                        Constants.GAME_START_X + 3, Constants.GAME_START_Y + 3,
                        shopMenu.getRegionWidth() * 3, shopMenu.getRegionHeight() * 3);
                font.getData().setScale(0.4f, -0.4f);
                font.setColor(85/255f, 50/255f, 7/255f, 1);
                font.draw(batcher, Constants.TUTORIAL_STRING_1,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 38,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_2,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 59,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_3,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 80,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_4,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 101,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_5,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 122,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_6,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 143,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_7,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 164,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_8,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 185,
                        109, 1, true);
                font.draw(batcher, Constants.TUTORIAL_STRING_9,
                        Constants.GAME_START_X + 13, Constants.GAME_START_Y + 206,
                        109, 1, true);
                font.getData().setScale(0.5f, -0.5f);
                font.setColor(1, 1, 1, 1);
                break;
        }

        for (SimpleButton button : world.getActiveButtons()) {
            button.draw(batcher);
        }
    }

    private void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        // draw collisions
        ArrayList<Floater> spawnList = spawner.getSpawnList();
        for (int i = 0; i < spawnList.size(); ++i) {
            Floater floater = spawnList.get(i);
            shapeRenderer.polygon(floater.getCollider().getTransformedVertices());
            shapeRenderer.circle(floater.getX(), floater.getY(), 1);
            shapeRenderer.circle(floater.getTrailX(), floater.getTrailY(), 1);
        }
        shapeRenderer.polygon(bear.getCollider().getTransformedVertices());
        shapeRenderer.circle(bear.getX(), bear.getY(), 1);
        shapeRenderer.circle(bear.getTrailX(), bear.getTrailY(), 1);

        // draw map bounds
        shapeRenderer.rect(Constants.GAME_START_X + Constants.GAME_LEFT_BOUND, Constants.GAME_START_Y,
                Constants.GAME_RIGHT_BOUND - Constants.GAME_LEFT_BOUND, Constants.GAME_HEIGHT);

        // draw the score line
        Vector2 tmp = scrollHandler.getScoreLine().getPositions().getFirst();
        shapeRenderer.line(Constants.GAME_START_X + Constants.GAME_LEFT_BOUND, tmp.y,
                Constants.GAME_START_X + Constants.GAME_RIGHT_BOUND, tmp.y);

        shapeRenderer.end();
    }

    private void drawScreenBounds() {
        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, Constants.SCREEN_WIDTH, Constants.GAME_START_Y);
        shapeRenderer.rect(0, Constants.GAME_START_Y + Constants.GAME_HEIGHT,
                Constants.SCREEN_WIDTH, Constants.GAME_START_Y);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(1, 1, 1, 0.1f);
        switch(InputHandler.touchSection) {
            case TOPLEFT:
                shapeRenderer.rect(0, Constants.GAME_START_Y,
                        Constants.GAME_MID_X, Constants.GAME_MID_Y);
                break;
            case TOPRIGHT:
                shapeRenderer.rect(Constants.GAME_MID_X, Constants.GAME_START_Y,
                        Constants.SCREEN_WIDTH, Constants.GAME_MID_Y);
                break;
            case BOTLEFT:
                shapeRenderer.rect(0, Constants.GAME_MID_Y,
                        Constants.GAME_MID_X, Constants.SCREEN_HEIGHT);
                break;
            case BOTRIGHT:
                shapeRenderer.rect(Constants.GAME_MID_X, Constants.GAME_MID_Y,
                        Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                break;
        }

        shapeRenderer.end();
    }

    public void render(float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(22/255f, 125/255f, 1/255f, 1);
        shapeRenderer.rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        shapeRenderer.end();

        batcher.begin();

        drawLowBackground(runTime);
        drawFloaters(runTime);
        drawBear(runTime);
        drawHighBackground();
        drawUI(runTime);

        batcher.end();

        if (Constants.SHOW_DEBUG) {
            drawDebug();
        }

        // if screen is too narrow
        drawScreenBounds();

    }

}
