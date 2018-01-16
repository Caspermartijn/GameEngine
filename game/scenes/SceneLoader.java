package scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import files.FileScanner;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import utils.SourceFile;
import utils.models.ModelLoader;
import utils.models.ModelMaster;

public class SceneLoader {

	@SuppressWarnings("unused")
	public static Scene getScene(MasterRenderer master, SkyboxRenderer skybrener, String scene_name) {
		Scene scene = new Scene(scene_name, master, skybrener) {
		};
		SourceFile folder = new SourceFile("/scenes/" + scene_name + "/");
		SourceFile modelsFolder = new SourceFile(folder, "models/");
		SourceFile scene_dataFolder = new SourceFile(folder, "scene_data/");
		SourceFile worldFolder = new SourceFile(folder, "world/");

		// Individual files
		SourceFile sceneDataFile = new SourceFile(scene_dataFolder, "data.dat");
		SourceFile objectivFile = new SourceFile(scene_dataFolder, "objective.dat");

		SourceFile entitiesFile = new SourceFile(worldFolder, "entities.dat");
		SourceFile hitboxesFile = new SourceFile(worldFolder, "hitboxes.dat");
		SourceFile terrainFile = new SourceFile(worldFolder, "terrains.dat");
		SourceFile othersFile = new SourceFile(worldFolder, "others.dat");

		scene = loadModels(scene, modelsFolder);

		scene.setObjective(getObjective(objectivFile));
		scene.entities = getEntities(entitiesFile);

		scene = loadSceneDataIntoScene(scene, scene_name, sceneDataFile);

		return scene;
	}

	private static Scene loadModels(Scene scene, SourceFile modelsFolder) {
		List<String> models = FileScanner.getStringList(new SourceFile(modelsFolder, "models.dat"));
		for (String s : models) {
			SourceFile datafile = new SourceFile(modelsFolder, "/" + s + "/data.dat");
		}
		return scene;
	}

	private static String[] getObjective(SourceFile file) {
		String[] strings = new String[] { "", "", "" };
		List<String> ss = FileScanner.getStringList(file);
		int i = 0;
		for (String s : ss) {
			if (s.equalsIgnoreCase("-")) {
				i++;
			}
			strings[i] += s + " ";
		}
		return strings;
	}

	private static List<Entity> getEntities(SourceFile file) {
		List<Entity> entities = new ArrayList<Entity>();
		List<String> ss = FileScanner.getStringList(file);
		for (String s : ss) {
			String model_name = "";
			float x = 0;
			float y = 0;
			float z = 0;
			float rotX = 0;
			float rotY = 0;
			float rotZ = 0;
			float scale = 0;
			UUID uuid = null;
			UUID parentUUID = null;
			String[] ent = s.split(":");

			model_name = ent[1];
			x = Float.parseFloat(ent[2]);
			y = Float.parseFloat(ent[3]);
			z = Float.parseFloat(ent[4]);

			rotX = Float.parseFloat(ent[5]);
			rotY = Float.parseFloat(ent[6]);
			rotZ = Float.parseFloat(ent[7]);

			scale = Float.parseFloat(ent[8]);

			uuid = UUID.fromString(ent[9]);
			if (ent.length > 8) {
				parentUUID = UUID.fromString(ent[10]);
			}
			Entity entity = new Entity(ModelMaster.getModel(model_name), new Vector3f(x, y, z),
					new Vector3f(rotX, rotY, rotZ), scale);
			entity.setUuid(uuid);
			entity.setParent_uuid(parentUUID);
		}
		return entities;
	}

	private static Scene loadSceneDataIntoScene(Scene scene, String scene_name, SourceFile dataFile) {

		List<String> ss = FileScanner.getStringList(dataFile);

		for (String s : ss) {
			if (s.startsWith("data")) {
				String s2 = s.replaceAll("data:", "");
				if (s2.startsWith("scene_name")) {
					scene.setDisplayName(s2.split(":")[1]);
				}
				if (s2.startsWith("preview_image:")) {
					SourceFile preview_image = new SourceFile("/scenes/" + scene_name + "/" + s2.split(":")[1]);
					scene.setPreview(ModelLoader.loadTexture(preview_image));
				}
			}

			if (s.startsWith("skybox")) {
				SourceFile ame_nebula = new SourceFile("/res/skybox/" + s.split(":")[1]);
				Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
						new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
						new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
						new SourceFile(ame_nebula, "face_front.png") }, 512);
				scene.setBox(skybox);
			}
		}

		return scene;
	}

}
