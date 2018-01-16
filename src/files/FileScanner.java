package files;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.SourceFile;

public class FileScanner {

	public static List<String> getStringList(SourceFile file) {
		Scanner sc = new Scanner(Class.class.getResourceAsStream(file.getPath()));
		ArrayList<String> list = new ArrayList<String>();
		while (sc.hasNext()) {
			list.add(sc.next());
		}
		sc.close();
		return list;
	}

}
