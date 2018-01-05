package files.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {
	List<String> fileList;

	public void unZipIt(boolean temp, String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];

		try {

			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				File fldr = new File(outputFolder) ;
				if(!fldr.exists()) {
					fldr.mkdirs();
				}
				
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				if(!newFile.exists()) {
					newFile.mkdir();
					newFile.createNewFile();
				}
				if (temp) {
					newFile.deleteOnExit();
				}
				System.out.println("File unzip : " + newFile.getAbsoluteFile());

				File f = new File(newFile.getParent());
				f.mkdirs();
				if (temp) {
					f.deleteOnExit();
				}
				
				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}