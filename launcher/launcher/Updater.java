package launcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import files.web.WebDownload;

public class Updater {

	protected String versionFile = "";

	public static String getVersion() throws IOException {
		Path path = Paths.get("").toAbsolutePath().getParent();
		File versionFile = new File(path.toString() + "/temp/temp.version");
		File tempPath = new File(path.toString() + "/temp");
		tempPath.mkdirs();
		versionFile.createNewFile();

		String version = "";
		WebDownload.downloadFile("https://www.dropbox.com/s/6ixzzg4ry30jq4l/version.txt?dl=1",
				path.toString() + "/temp/temp.version");

		Scanner scanner = new Scanner(versionFile);
		List<String> strings = new ArrayList<String>();
		while (scanner.hasNext()) {
			strings.add(scanner.nextLine());
		}
		scanner.close();
		version = strings.get(0);
		versionFile.deleteOnExit();
		return version;
	}

}
