package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity{

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static float GRAVITY = 50;
	private static float JUMP_POWER = 25;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed;
	private float upwardSpeed;
	
	private boolean isJumping = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
	}
	
	public void move() {
		checkInputs();
		float delta = DisplayManager.getFrameTimeSeconds();
		super.increaseRotation(0, currentTurnSpeed * delta,  0);
		
		float distance = currentSpeed * delta;
		
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		//upwardSpeed -= GRAVITY * delta;
		super.increasePosition(0, upwardSpeed * delta, 0);
		
		if(super.getPosition().y < TERRAIN_HEIGHT) {
			upwardSpeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT;
			isJumping = false;
		}
	}
	
	private void jump() {
		if(!isJumping) {
			upwardSpeed = JUMP_POWER;
			isJumping = true;
		}
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			currentSpeed = RUN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			currentSpeed = -RUN_SPEED;
		} 
		else {
			currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			currentTurnSpeed = TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			currentTurnSpeed = -TURN_SPEED;
		} 
		else {
			currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
