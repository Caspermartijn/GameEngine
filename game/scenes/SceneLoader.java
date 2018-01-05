package scenes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.utils.UnZip;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;

public class SceneLoader {

	public static Scene getScene(MasterRenderer master, SkyboxRenderer skybrener, String scene_name) {
		Scene scene = new Scene(scene_name, master, skybrener) {
		};
		Path path = (Path) Paths.get("").toAbsolutePath().getParent();

		File f = new File(path.toString() + "/scenes");
		if (!f.exists())
			f.mkdirs();
		File f2 = new File(path.toString() + "/scenes/temp");
		if (!f2.exists())
			f2.mkdirs();
		File zip = new File(f, scene_name + ".zip");
		UnZip unzip = new UnZip();
		unzip.unZipIt(true,zip.getPath(), path.toString() + "/scenes/temp/" + scene_name);

		return scene;
	}

}
