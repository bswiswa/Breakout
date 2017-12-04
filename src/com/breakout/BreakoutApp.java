package com.breakout;

import org.jbox2d.dynamics.BodyType;

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.PhysicsEntity;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
		//remove gravity default is (0, 10)
		physicsManager.setGravity(0,0);
		initBat();
		initBall();
		initBricks();
		
	}

	private void initBat() {
		bat = new PhysicsEntity(Type.BAT);
		bat.setPosition((getWidth()/2 - 128/2), (getHeight() - 25));
		bat.setGraphics(assets.getTexture("bat.png"));
		bat.setBodyType(BodyType.KINEMATIC);
		addEntities(bat);
	}
	
	private void initBall() {
		ball = new PhysicsEntity(Type.BALL);
		ball.setPosition(getWidth()/2 - 30/2, getHeight()/2 -30/2);
		ball.setGraphics(assets.getTexture("ball.png"));
		ball.setBodyType(BodyType.DYNAMIC);
		addEntities(ball);
	}
	
	private void initBricks() {
		for(int i = 0; i < 48; i++) {
			PhysicsEntity brick = new PhysicsEntity(Type.BRICK);
			brick.setPosition((i%16)*40, 100 + (i/16)*40);
			/* there are 16 bricks that fit in a row so each row
			 * we specify the position by i%16 since it will keep repeating between 1 -16
			 * whereas for height, we want the height to change after 16 so instead of i%16 we do i/16
			 */
			brick.setGraphics(assets.getTexture("brick.png"));
			
			addEntities(brick);
		}
		
	}

	@Override
	protected void initUI(Pane uiRoot) {
		Text scoreText = new Text();
		scoreText.setTranslateY(50);
		scoreText.setFont(Font.font(18));
		scoreText.setText("SCORE:");
		uiRoot.getChildren().add(scoreText);
		
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
