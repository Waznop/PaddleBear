package com.waznop.gameworld;

import com.waznop.gameobjects.ScoreLine;
import com.waznop.gameobjects.Scrollable;
import com.waznop.paddlebear.Constants;

/**
 * Created by Waznop on 2016-09-16.
 */
public class ScrollHandler {

    private GameWorld gameWorld;

    private Scrollable river;
    private Scrollable land;
    private Scrollable sand;
    private ScoreLine scoreLine;

    public ScrollHandler(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        river = new Scrollable(2, Constants.GAME_START_X, Constants.GAME_START_Y,
                Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                Constants.RIVER_SCROLL_Y);

        land = new Scrollable(2, Constants.GAME_START_X, Constants.GAME_START_Y,
                Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                Constants.LAND_SCROLL_Y);

        sand = new Scrollable(2, Constants.GAME_START_X, Constants.GAME_START_Y,
                Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                Constants.LAND_SCROLL_Y);

        scoreLine = new ScoreLine(Constants.GAME_START_X, Constants.GAME_START_Y + Constants.SCORE_LINE_HEIGHT,
                Constants.GAME_WIDTH, 1,
                Constants.LAND_SCROLL_Y, gameWorld);
    }

    public void reset() {
        // river.reset();
        land.reset();
        sand.reset();
        scoreLine.reset();
    }

    public void update(float delta) {
        update(delta, 0);
    }

    public void update(float delta, float extraSpeedY) {
        river.update(delta, extraSpeedY);
        land.update(delta, extraSpeedY);
        sand.update(delta, extraSpeedY);
        scoreLine.update(delta, extraSpeedY);
    }

    public Scrollable getRiver() {
        return river;
    }

    public Scrollable getLand() {
        return land;
    }

    public Scrollable getSand() {
        return sand;
    }

    public ScoreLine getScoreLine() {
        return scoreLine;
    }

}
