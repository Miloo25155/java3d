package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity{

	private static final float RUN_SPEED = 20;
	private static final float LATERAL_SPEED = 12;
	private static final float TURN_SPEED = 160;
	private static float GRAVITY = 50;
	private static float JUMP_POWER = 25;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float lateralSpeed = 0;
	//private float currentTurnSpeed;
	private float upwardSpeed;
	
	private boolean isJumping = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float offsetRotY) {
		super(model, position, rotX, rotY, rotZ, scale, offsetRotY);
	}
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move() {
		checkInputs();
		float delta = DisplayManager.getFrameTimeSeconds();
		
		//float deltaRot = super.getCamera().getAngleAroundTarget();// - this.getRotY();
		//super.increaseRotation(0, TURN_SPEED * delta * 1f,  0);
		float cameraAngleAroundPlayer = super.getCamera().getAngleAroundTarget();
		if(cameraAngleAroundPlayer < 0 && currentSpeed > 0) {
			super.increaseRotation(0, -TURN_SPEED * delta * 1f,  0);
		} else if(cameraAngleAroundPlayer > 0 && currentSpeed > 0) {
			super.increaseRotation(0, TURN_SPEED * delta * 1f,  0);
		}
		
		float distance = currentSpeed * delta;
		float lateralDistance = lateralSpeed * delta;
		
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())) + (lateralDistance * Math.cos(Math.toRadians(super.getRotY()))));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())) + (lateralDistance * Math.sin(Math.toRadians(super.getRotY()))));
		super.increasePosition(dx, 0, dz);
		
		
		upwardSpeed -= GRAVITY * delta;
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
			lateralSpeed = LATERAL_SPEED;
			//currentTurnSpeed = TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			lateralSpeed = -LATERAL_SPEED;
			//currentTurnSpeed = -TURN_SPEED;
		} 
		else {
			lateralSpeed = 0;
			//currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
