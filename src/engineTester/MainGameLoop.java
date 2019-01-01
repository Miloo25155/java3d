package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();		
		Loader loader = new Loader();	
		
		ModelData treeData = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));	
		TexturedModel treeTexturedModel = new TexturedModel(treeModel, treeTexture);
		
		ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));	
		grassTexture.setHasTransparency(true);
		grassTexture.setUseFakeLighting(true);
		TexturedModel grassTexturedModel = new TexturedModel(grassModel, grassTexture);
		
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));	
		fernTexture.setHasTransparency(true);
		fernTexture.setUseFakeLighting(true);
		TexturedModel fernTexturedModel = new TexturedModel(fernModel, fernTexture);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 100; i++) {
			Vector3f randomPos = new Vector3f(random.nextInt(400), 0, random.nextInt(400));
			float randomRotY = random.nextInt(360);;
			Entity entity = new Entity(treeTexturedModel,randomPos, 0, randomRotY, 0, 3);
			entities.add(entity);
		}
		
		for(int i = 0; i < 250; i++) {
			Vector3f randomPos = new Vector3f(random.nextFloat()*400, 0, random.nextFloat()*400);
			float randomRotY = random.nextInt(360);
			float randomScale = random.nextFloat()*2f;
			randomScale = Math.max(randomScale, 0.8f);
			Entity entity = new Entity(grassTexturedModel,randomPos, 0, randomRotY, 0, randomScale);
			entities.add(entity);
			
			randomPos = new Vector3f(random.nextFloat()*400, 0, random.nextFloat()*400);
			randomRotY = random.nextInt(360);
			Entity entity2 = new Entity(fernTexturedModel,randomPos, 0, randomRotY, 0, 1f);
			entities.add(entity2);
		}
		
		ModelData playerData = OBJFileLoader.loadOBJ("lowPolyEagle");
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		ModelTexture playerTexture = new ModelTexture(loader.loadTexture("eagleColor"));	
		TexturedModel playerTexturedModel = new TexturedModel(playerModel, playerTexture);
		
		Player player = new Player(playerTexturedModel, new Vector3f(200, 7, 100), 0, 0, 0, 1f);
		
		Camera camera = new Camera(new Vector3f(200, 10, 200), 5, 0, 0);
		Light light = new Light(new Vector3f(0, 2000, 1000), new Vector3f(1,1,1));
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("grass"));
		Terrain terrain = new Terrain(0, 0, loader, terrainTexture);
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {			
			camera.move();
			player.move();
			
			renderer.processEntity(player);
			renderer.render(light, camera);
			
			renderer.processTerrain(terrain);
			
			for(Entity entity: entities) {
				renderer.processEntity(entity);
			}
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
