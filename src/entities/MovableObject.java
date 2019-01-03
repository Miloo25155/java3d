package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class MovableObject {
	private Vector3f position;
	
	//private Vector3f localFrontVector;
	
	private float rotX, rotY, rotZ;
	private float scale;
	
	private float targetPosYOffset;
	private float entityYRotOffset;
	
	public MovableObject(Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		
		this.targetPosYOffset = 1f;
		this.entityYRotOffset = 0;
		
		//localFrontVector = new Vector3f((float) Math.sin(Math.toRadians(rotY)), this.position.y, (float) Math.cos(Math.toRadians(rotY)));
	}
	
	public MovableObject(Vector3f position, float rotX, float rotY, float rotZ, float scale, float entityYRotOffset) {
		this.position = position;
		
		this.entityYRotOffset = entityYRotOffset;
		
		this.rotX = rotX;
		this.rotY = rotY + entityYRotOffset;
		this.rotZ = rotZ;
		this.scale = scale;
		
		this.targetPosYOffset = scale;
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX+= dx;
		this.rotX = this.rotX % 360f;
		
		this.rotY += dy;
		this.rotY = this.rotY % 360f;
		
		this.rotZ += dz;
		this.rotZ = this.rotZ % 360f;
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getOffsetPosition() {
		return new Vector3f(position.x, position.y + targetPosYOffset, position.z);
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY + this.entityYRotOffset;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getTargetPosYOffset() {
		return targetPosYOffset;
	}

	public void setTargetPosYOffset(float targetPosYOffset) {
		this.targetPosYOffset = targetPosYOffset;
	}	
}
