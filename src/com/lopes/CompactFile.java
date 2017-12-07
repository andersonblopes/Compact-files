package com.lopes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompactFile {

	public static void main(String[] a) throws Exception {

		String directoryIn = "D:\\in";
		// String fileNameToCompact = "a.csv";
		String fileNameToCompact = "ALL";
		String pathFileOut = "D:\\out" + File.separator + removeExtension(fileNameToCompact) + ".zip";

		compactFiles(directoryIn, fileNameToCompact, pathFileOut);

		readOnlyOneZipFile(pathFileOut);

	}

	public static void compactFiles(String directoryIn, String fileNameToCompact, String pathFileOut) {
		if (fileNameToCompact != null && !fileNameToCompact.equals("ALL")) {
			directoryIn = directoryIn + File.separator + fileNameToCompact;
			compactOneFile(directoryIn, pathFileOut, fileNameToCompact);
		} else {
			compactAllFile(directoryIn, pathFileOut);
		}
	}

	static void compactOneFile(String directoryIn, String pathFileOut, String fileNameToCompact) {
		try {
			FileInputStream fis = new FileInputStream(directoryIn);
			FileOutputStream fos = new FileOutputStream(pathFileOut);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			zipOut.putNextEntry(new ZipEntry(fileNameToCompact));
			int content;
			while ((content = fis.read()) != -1) {
				zipOut.write(content);
			}
			zipOut.closeEntry();
			zipOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void compactAllFile(String directoryIn, String pathFileOut) {
		try {
			FileOutputStream fos = new FileOutputStream(pathFileOut);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File folder = new File(directoryIn);
			for (File arq : folder.listFiles()) {
				zipOut.putNextEntry(new ZipEntry(arq.getName().toString()));
				FileInputStream fis = new FileInputStream(arq);
				int content;
				while ((content = fis.read()) != -1) {
					zipOut.write(content);
				}
				zipOut.closeEntry();
			}
			zipOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readOnlyOneZipFile(String pathFileOut) {
		try {
			FileInputStream fis = new FileInputStream(pathFileOut);
			ZipInputStream zipIn = new ZipInputStream(fis);
			ZipEntry entry = zipIn.getNextEntry();

			StringBuilder textOfFile = new StringBuilder();

			while (zipIn.available() > 0) {
				textOfFile.append((char) zipIn.read());
			}

			System.out.println(textOfFile.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String removeExtension(String fileName) {
		if (fileName.contains(".")) {
			fileName = fileName.substring(0, fileName.indexOf("."));
		}
		return fileName;
	}
}
