package com.breakout;

import org.jbox2d.dynamics.BodyType;

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.PhysicsEntity;

import javafx.scene.layout.Pane;

public class BreakoutApp extends GameApplication{
	private enum Type implements EntityType{
		BAT, BALL, BRICK;
	}
	
	private PhysicsEntity bat, ball;
	
	private Assets assets;
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Breakout");
		settings.setVersion("dev");
		settings.setWidth(640);
		settings.setHeight(740);
		settings.setIntroEnabled(false);
		
		
	}

	@Override
	protected void initAssets() throws Exception {
		assets = assetManager.cache();
		assets.logCached();
		
	}

	@Override
	protected void initGame() {
		//remove gravity
		physicsManager.setGravity(0,0);
		initBat();
		initBall();
		initBricks();
		
	}

	private void initBricks() {
		// TODO Auto-generated method stub
		
	}

	private void initBat() {
		bat = new PhysicsEntity(Type.BAT);
		bat.setPosition((getWidth()/2 - 128/2), (getHeight() - 40));
		bat.setGraphics(assets.getTexture("bat.png"));
		bat.setBodyType(BodyType.KINEMATIC);
		addEntities(bat);
	}
	
	private void initBall() {
		ball = new PhysicsEntity(Type.BALL);
		ball.setPosition((getWidth()/2) - 30/2, (getHeight()/2 - 30/2));
		ball.setGraphics(assets.getTexture("ball.png"));
		ball.setBodyType(BodyType.DYNAMIC);
		addEntities(ball);
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
