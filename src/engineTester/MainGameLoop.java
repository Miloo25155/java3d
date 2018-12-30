package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class MainGameLoop {

	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("white-texture"));
		texture.setReflectivity(1);
		texture.setShineDamper(50);
		
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(texturedModel, new Vector3f(0,-5,-50), 0, 0, 0, 1);		
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(200, 200, 100), new Vector3f(1,1,1));
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {			
			camera.move();
			
			entity.increaseRotation(0, 0.6f, 0);
			
			renderer.processEntity(entity);
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
