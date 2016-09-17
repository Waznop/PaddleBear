package com.waznop.paddlebear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.waznop.screens.GameScreen;

public class PBGame extends Game {
	
	@Override
	public void create () {
		float scaledScreenWidth = Gdx.graphics.getWidth();
		float scaledScreenHeight = Gdx.graphics.getHeight();
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
		Constants.SCREEN_WIDTH = screenWidth;
		Constants.SCREEN_HEIGHT = screenHeight;
		Constants.GAME_SCALE = scaledScreenWidth / screenWidth;
		Constants.GAME_START_X = gameStartX;
		Constants.GAME_START_Y = gameStartY;
		Constants.GAME_MID_X = screenWidth / 2;
		Constants.GAME_MID_Y = screenHeight / 2;

		AssetLoader.load();

		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
