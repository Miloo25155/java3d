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
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		StaticShader shader = new StaticShader();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer(shader);
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("white-texture"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(texturedModel, new Vector3f(0,-5,-50), 0, 0, 0, 1);		
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0, 0, -35), new Vector3f(1,1,1));
		
		while(!Display.isCloseRequested()) {
			//entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0.3f, 0);
			
			camera.move();
			renderer.prepare();
			
			shader.start();
			
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			
			renderer.render(entity, shader);
			
			shader.stop();
			
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
