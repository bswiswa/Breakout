package com.breakout;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsEntity;
import com.almasb.fxgl.physics.PhysicsManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BreakoutApp extends GameApplication{
	private enum Type implements EntityType{
		BAT, BALL, BRICK, SCREEN;
	}
	
	private PhysicsEntity bat, ball;
	private Assets assets;
	private IntegerProperty score = new SimpleIntegerProperty();
	
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Breakout");
		settings.setVersion("dev");
		settings.setWidth(640);
		settings.setHeight(740);
		settings.setIntroEnabled(true);
		
		
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
		initScreenBounds();
		
		physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK) {

			@Override
			public void onCollisionBegin(Entity a, Entity b) {
				removeEntity(b);
				score.set(score.get()+100);
				
			}

			@Override
			public void onCollision(Entity a, Entity b) {	
			}

			@Override
			public void onCollisionEnd(Entity a, Entity b) {	
			}
			
		});
		
		//to catch collision with the bottom of the screen
		//player loses 1000 points if they fail to catch the ball.
		physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.SCREEN) {

			@Override
			public void onCollisionBegin(Entity a, Entity b) {
				removeEntity(b);
				score.set(score.get()-1000);
				
			}

			@Override
			public void onCollision(Entity a, Entity b) {	
			}

			@Override
			public void onCollisionEnd(Entity a, Entity b) {	
			}
			
		});
		
	}
	
	private void initScreenBounds() {
		PhysicsEntity top = new PhysicsEntity(Type.SCREEN);
		top.setPosition(0, -5);
		top.setGraphics(new Rectangle(getWidth(), 5));
		
		PhysicsEntity bottom = new PhysicsEntity(Type.SCREEN);
		bottom.setPosition(0, getHeight());
		bottom.setGraphics(new Rectangle(getWidth(), 5));
		bottom.setCollidable(true);
		
		PhysicsEntity left = new PhysicsEntity(Type.SCREEN);
		left.setPosition(-5, 0);
		left.setGraphics(new Rectangle(5, getHeight()));
		
		PhysicsEntity right = new PhysicsEntity(Type.SCREEN);
		right.setPosition(getWidth(), 0);
		right.setGraphics(new Rectangle(5, getHeight()));
		
		
		addEntities(top);
		addEntities(left);
		addEntities(bottom);
		addEntities(right);
		
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
		//so that the collision event fires (setCollidable is false by default because Colliding is an expensive operation)
		ball.setCollidable(true);
		
		FixtureDef fd = new FixtureDef();
		fd.restitution = 0.8f;
		fd.shape = new CircleShape();
		fd.shape.setRadius(PhysicsManager.toMeters(15));
		ball.setFixtureDef(fd);
		addEntities(ball);
		
		ball.setLinearVelocity(5,-5);
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
			brick.setCollidable(true);
			addEntities(brick);
		}
		
	}

	@Override
	protected void initUI(Pane uiRoot) {
		Text scoreText = new Text();
		scoreText.setTranslateY(50);
		scoreText.setFont(Font.font(18));
		scoreText.textProperty().bind(score.asString());
		uiRoot.getChildren().add(scoreText);
		
	}

	@Override
	protected void initInput() {
		// move left options - left key or A
		inputManager.addKeyPressBinding(KeyCode.LEFT, ()->{
			bat.setLinearVelocity(-5, 0);
		});
		inputManager.addKeyPressBinding(KeyCode.A, ()->{
			bat.setLinearVelocity(-5, 0);
		});
		// move right options - right key or D
		inputManager.addKeyPressBinding(KeyCode.RIGHT, ()->{
			bat.setLinearVelocity(5, 0);
		});
		inputManager.addKeyPressBinding(KeyCode.D, ()->{
			bat.setLinearVelocity(5, 0);
		});
		
		
	}

	@Override
	protected void onUpdate() {
		//so bat doesn't move on its own but only when key is pressed
		bat.setLinearVelocity(0, 0);
		Point2D v = ball.getLinearVelocity();
		if(Math.abs(v.getY()) < 5) {
			double x = v.getX();
			double signY = Math.signum(v.getY());
			ball.setLinearVelocity(x, signY*5);
		}
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
