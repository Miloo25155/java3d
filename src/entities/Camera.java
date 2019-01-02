package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.util.Log;

public class Camera {

	private Vector3f position = new Vector3f(0, 10, 10);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float distanceFromTarget = 50;
	private float angleAroundTarget = 0;
	
	private Entity target;
	
	public Camera(Entity target) {
		this.target = target;
		this.target.setCamera(this);
	}
	
	public Camera(Vector3f position, float pitch, float yaw, float roll) {
		this.position = position;
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
		
		float horizontalDistance = calculateHorizontalDistanceFromTarget();
		float verticalDistance = calculateVerticalDistanceFromTarget();
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (target.getRotY() + angleAroundTarget);
	}
	
	public Vector3f getPosition() {
		return position;
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
		
		position.x = target.getPosition().x - offsetX;
		position.z = target.getPosition().z - offsetZ;
		position.y = target.getPosition().y + target.getTargetPosYOffset() + vertiDist;
	}
	
	private float calculateHorizontalDistanceFromTarget() {
		return (float) (distanceFromTarget * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistanceFromTarget() {
		return (float) (distanceFromTarget * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		distanceFromTarget -= zoomLevel;
	}
	
	private void calculatePitch() {
		float pitchChange = Mouse.getDY() * 0.1f;
		pitch -= pitchChange;
		if(pitch < -40) {
			pitch = -40;
		} else if(pitch > 85) {
			pitch = 85;
		}
	}
	
	private void calculateAngleAroundTarget() {
		float angleChange = Mouse.getDX() * 0.2f;
		angleAroundTarget -= angleChange;
	}
}
