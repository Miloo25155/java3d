package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.util.Log;

import toolbox.Maths;

public class Camera extends MovableObject{
	private float pitch;
	private float yaw;
	private float roll;
	
	private static final float MAX_PITCH = 75;
	private static final float MIN_PITCH = -20;
	
	private float distanceFromTarget = 50;
	private float angleAroundTarget = 0;
	
	private Entity target;
	
	public Camera(Entity target) {
		super(target.getOffsetPosition(), 0, 0, 0, 0);
		this.target = target;
		this.target.setCamera(this);
		pitch = (MAX_PITCH + MIN_PITCH) / 2;
	}
	
	public Camera(Vector3f position, float pitch, float yaw, float roll) {
		super(position, pitch, yaw, roll, 0);
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundTarget();
		
		Mouse.setCursorPosition(0, 0);
		Mouse.setGrabbed(true);
		
		float horizontalDistance = (float) (distanceFromTarget * Math.cos(Math.toRadians(pitch)));
		float verticalDistance = (float) (distanceFromTarget * Math.sin(Math.toRadians(pitch)));
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
	}
	
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}	
	
	public float getAngleAroundTarget() {
		return angleAroundTarget;
	}

	private void calculateCameraPosition(float horizDist, float vertiDist) {
		float theta = target.getRotY() + angleAroundTarget;
		float offsetX = (float) (horizDist * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDist * Math.cos(Math.toRadians(theta)));
		
		super.setPosition(new Vector3f(target.getPosition().x - offsetX, target.getOffsetPosition().y + vertiDist, target.getPosition().z - offsetZ));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		distanceFromTarget -= zoomLevel;
	}
	
	private void calculatePitch() {
		float pitchChange = Mouse.getDY() * 0.1f;
		pitch -= pitchChange;
		pitch = Maths.Clamp(pitch, MIN_PITCH, MAX_PITCH);
	}
	
	private void calculateAngleAroundTarget() {
		float angleChange = Mouse.getDX() * 0.2f;
		angleAroundTarget -= angleChange;
		angleAroundTarget = angleAroundTarget % 360;
		this.yaw = 180 - angleAroundTarget;
	}
}
