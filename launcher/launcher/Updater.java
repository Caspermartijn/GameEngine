package launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

	public static void downloadUpdate() throws IOException {
		String newJarPath = getNewJarPath();
		downloadNewGameJar(newJarPath);
		writeUpdateCMD();
	}

	private static String getNewJarPath() throws IOException {
		Path path = Paths.get("").toAbsolutePath().getParent();
		File versionFile = new File(path.toString() + "/temp/temp.jarpath");
		File tempPath = new File(path.toString() + "/temp");
		tempPath.mkdirs();
		versionFile.createNewFile();

		String version = "";
		WebDownload.downloadFile("https://www.dropbox.com/s/vyqojy6oq68d6g4/jarpath.txt?dl=1",
				path.toString() + "/temp/temp.jarpath");

		Scanner scanner = new Scanner(versionFile);
		List<String> strings = new ArrayList<String>();
		while (scanner.hasNext()) {
			strings.add(scanner.nextLine());
		}
		scanner.close();
		version = strings.get(0);
		versionFile.delete();
		return version;
	}

	private static void writeUpdateCMD() {
		Path thispath = Paths.get("").toAbsolutePath().getParent();
		BufferedWriter fileOut;

		String itsFileLocation = thispath + "\\temp\\";
		try {
			fileOut = new BufferedWriter(new FileWriter(thispath + "\\temp\\updater.bat"));
			fileOut.write("cd\\" + "\n");
			fileOut.write("cd " + itsFileLocation + "\n");
			fileOut.write("updater.jar" + "\n");
			fileOut.write("exit" + "\n");

			fileOut.close(); // Close the output stream after all output is done.
		} catch (IOException e1) {
			e1.printStackTrace();
		} // Create the Buffered Writer object to write to a file called filename.txt
		try {
			Runtime.getRuntime().exec("updater.jar", null, new File(thispath + "\\temp\\"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		File versionFile = new File(thispath.toString() + "/temp/updater.bat");
		versionFile.deleteOnExit();
	}

	private static void downloadNewGameJar(String newJarPath) throws IOException {
		Path path = Paths.get("").toAbsolutePath().getParent();
		File versionFile = new File(path.toString() + "/temp/updater.jar");
		File tempPath = new File(path.toString() + "/temp");
		tempPath.mkdirs();
		versionFile.createNewFile();
		WebDownload.downloadFile(newJarPath, path.toString() + "/temp/updater.jar");
		versionFile.deleteOnExit();
	}

}
