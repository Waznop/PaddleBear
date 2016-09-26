package com.waznop.paddlebear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.waznop.screens.SplashScreen;

public class PBGame extends Game {
	
	@Override
	public void create () {
		AssetLoader.setSizes(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
