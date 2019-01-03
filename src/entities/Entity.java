package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Maths;

public class Entity extends MovableObject{
	
	private TexturedModel model;
	//private Vector3f localFrontVector;
	
	private Camera camera;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(position, rotX, rotY, rotZ, scale);
		this.model = model;
		
		//localFrontVector = new Vector3f((float) Math.sin(Math.toRadians(rotY)), this.position.y, (float) Math.cos(Math.toRadians(rotY)));
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float entityYRotOffset) {
		super(position, rotX, rotY, rotZ, scale, entityYRotOffset);
		this.model = model;
	}
	
	
	
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
