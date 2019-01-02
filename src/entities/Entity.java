package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Maths;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private float targetPosYOffset;
	
	private Camera camera;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
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
	
	public void setTargetRotation(float targetRotX, float targetRotY, float targetRotZ, float f) {
		this.rotX = Maths.Lerp(this.rotX, targetRotX, f);
		this.rotY = Maths.Lerp(this.rotY, targetRotY, f);
		this.rotZ = Maths.Lerp(this.rotZ, targetRotZ, f);
	}
	

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
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
		this.rotY = rotY;
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

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
