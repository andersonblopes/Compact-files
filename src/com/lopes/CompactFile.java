package com.lopes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompactFile {

	private static final Logger LOG = Logger.getLogger(CompactFile.class.getName());

	public static void main(String[] args) {
		LOG.info("========== Starting File Compression ==========");

		String sourceFolder = "/home/anderson/Documentos/Anderson/teste/csv";
		String destinationFolder = "/home/anderson/Documentos/Anderson/teste";
		String nameZipFile = "csv.zip";
		String destinationFile = destinationFolder + File.separator + nameZipFile;
		zipFiles(sourceFolder, destinationFile);
	}

	public static void zipFiles(String sourceFolder, String destinationFile) {
		try {
			File inFolder = new File(sourceFolder);
			File outFolder = new File(destinationFile);
			if (inFolder.exists()) {
				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
				BufferedInputStream in = null;
				byte[] data = new byte[1000];
				String files[] = inFolder.list();
				for (int i = 0; i < files.length; i++) {
					in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
					out.putNextEntry(new ZipEntry(files[i]));
					int count;
					while ((count = in.read(data, 0, 1000)) != -1) {
						out.write(data, 0, count);
					}
					out.closeEntry();
				}
				out.flush();
				out.close();
				LOG.info("========== Process completed successfully ==========");
			} else {
				LOG.severe("Directory not found");
			}
		} catch (Exception e) {
			LOG.severe("Process failed: ");
			e.printStackTrace();
		}
	}

}
