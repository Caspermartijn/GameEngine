package scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import files.FileScanner;
import log.Log;
import objects.Model_3D;
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
		
		Log.append("", false);
		Log.append("Loading scene : " + scene_name, true);
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
		scene = getEntities(scene, entitiesFile);

		scene = loadSceneDataIntoScene(scene, scene_name, sceneDataFile);
		// TODO hitboxes
		return scene;
	}

	private static Scene loadModels(Scene scene, SourceFile modelsFolder) {
		List<String> models = FileScanner.getStringList(new SourceFile(modelsFolder, "models.dat"));
		Log.append("Loading scene models : " + models.size(), true);
		for (String s : models) {
			String appendText = "========== model : " + s + " ==========";
			Log.append(appendText, true);
			SourceFile datafile = new SourceFile(modelsFolder, "/" + s + "/data.dat");
			List<String> data = FileScanner.getStringList(datafile);
			for (String a : data) {
				Log.append("          - " + a, true);
			}
			boolean colorMap = false;
			boolean specularMap = false;
			boolean backfaceculling = false;
			for (String s2 : data) {
				if (s2.startsWith("colormap")) {
					colorMap = Boolean.getBoolean(s2.split(":")[1]);
				}
				if (s2.startsWith("specularmap")) {
					specularMap = Boolean.getBoolean(s2.split(":")[1]);
				}
				if (s2.startsWith("backfaceculling")) {
					backfaceculling = Boolean.getBoolean(s2.split(":")[1]);
				}
			}
			Model_3D model = ModelMaster.getModel(s, new SourceFile(modelsFolder, "/" + s));
			model.setBackfaceCullingEnabled(backfaceculling);

			if (colorMap) {
				model.setColorMap(ModelLoader.loadTexture(new SourceFile(modelsFolder, s + "/colormap.png")));
			}

			if (specularMap) {
				model.setColorMap(ModelLoader.loadTexture(new SourceFile(modelsFolder, s + "/specularmap.png")));
			}

			scene.models.put(s.toLowerCase(), model);

			String appendText2 = "";
			for (int i = 0; i < appendText.length(); i++) {
				appendText2 += "=";
			}

			Log.append(appendText2, true);
			Log.append("", true);
		}
		return scene;
	}

	private static String[] getObjective(SourceFile file) {
		Log.append("Loading scene objective", true);
		Log.append("========================================", true);
		String[] strings = new String[] { "", "", "" };
		List<String> ss = FileScanner.getStringList(file);
		int i = 0;
		for (String s : ss) {
			if (s.equalsIgnoreCase("-")) {
				i++;
			}
			strings[i] += s + " ";
		}

		for (int ii = 0; ii < 3; ii++) {
			strings[ii] = strings[ii].replaceFirst("- ", "");
		}

		for (String string : strings) {
			Log.append("          - " + string, true);
		}
		Log.append("========================================", true);Log.append("", true);
		return strings;
	}

	private static Scene getEntities(Scene scene, SourceFile file) {
		List<Entity> entities = new ArrayList<Entity>();
		List<String> ss = FileScanner.getStringList(file);
		Log.append("Loading scene entities : " + ss.size(), true);
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
				// parentUUID = UUID.fromString(ent[10]);
			}
			Vector3f position = new Vector3f(x, y, z);
			Vector3f rotation = new Vector3f(rotX, rotY, rotZ);

			String appendText = "========== entity : " + uuid.toString() + " ==========";
			Log.append(appendText, true);
			Log.append("          - model:" + model_name, true);
			Log.append("", true);
			Log.append("          - position:" + x + " " + y + " " + z, true);
			Log.append("          - rotation:" + rotX + " " + rotY + " " + rotZ, true);
			Log.append("          - scale:" + scale, true);
			Log.append("", true);
			Log.append("          - uuid:" + uuid.toString(), true);

			Entity entity = new Entity(scene.getModel(model_name), position, rotation, scale);
			entity.setUuid(uuid);
			entity.setParent_uuid(parentUUID);
			entities.add(entity);

			String appendText2 = "";
			for (int i = 0; i < appendText.length(); i++) {
				appendText2 += "=";
			}
			Log.append(appendText2, true);
			Log.append("", true);
		}
		scene.entities.addAll(entities);
		return scene;
	}

	private static Scene loadSceneDataIntoScene(Scene scene, String scene_name, SourceFile dataFile) {

		List<String> ss = FileScanner.getStringList(dataFile);
		Log.append("Loading scene data", true);

		for (String s : ss) {
			if (s.startsWith("data")) {
				String s2 = s.replaceAll("data:", "");
				if (s2.startsWith("scene_name")) {
					scene.setDisplayName(s2.split(":")[1]);
				}
				if (s2.startsWith("preview_image:")) {
					SourceFile preview_image = new SourceFile(
							"/scenes/" + scene_name + "/" + s2.split(":")[1] + ".png");
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
