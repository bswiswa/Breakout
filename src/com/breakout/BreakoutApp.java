package com.breakout;

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;

import javafx.scene.layout.Pane;

public class BreakoutApp extends GameApplication{
	private Assets assets;
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Breakout");
		settings.setVersion("dev");
		settings.setWidth(640);
		settings.setHeight(960);
		settings.setIntroEnabled(false);
		
		
	}

	@Override
	protected void initAssets() throws Exception {
		assets = assetManager.cache();
		assets.logCached();
		
	}

	@Override
	protected void initGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initUI(Pane uiRoot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
