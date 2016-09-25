package com.waznop.paddlebear.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.waznop.paddlebear.PBGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(405, 720);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PBGame();
        }
}