package com.home.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);
	

	/**
	 * 
	 * @param startPath
	 * @param destPath
	 * @throws UpdateFileException
	 */
	public static void moveFiles(String startPath, String destPath, boolean recursive) throws UpdateFileException {
		try {
			LOG.info("Starting Copying");
			if (!destPath.endsWith("\\")){
				destPath = destPath + "\\";
			}
			File file = new File(startPath);
			List<File> fileList = new ArrayList<File>();
			LOG.info("Is Directory :" + file.isDirectory());
			if (file.isDirectory()){
				fileList.addAll(getFilesFromDir(file, recursive));
			} else {
				fileList.add(file);
			}
			LOG.info("List Size :" + fileList.size());
			for (File moveFile : fileList){
				String fName = moveFile.getPath();
				fName = fName.substring(3);
				File newFile = new File(destPath + fName);
				if (moveFile.isDirectory()){
					newFile.mkdirs();
					LOG.info("Making Dir :" + newFile.getName());
				} else if (moveFile.isFile()){
					LOG.info("Moving File :" + newFile.getName());
					moveFile.renameTo(newFile);
					LOG.info("Moved");
				}
			}
			File oldDir = new File(startPath);
			deleteFoldersFromDir(oldDir);
			
			LOG.info("Finished Moving " + fileList.size() + " Files!");
		} catch (Exception e){
			throw new UpdateFileException(e);
		}
	}
	
	
	/**
	 * 
	 * @param file - Directory to get files from.
	 * @return List of Files within that directory and all sub directories.
	 */
	public static List<File> getFilesFromDir(File file, boolean recursive){
		List<File> list = new ArrayList<File>();
		File[] files = file.listFiles();
		if (files != null){
			LOG.info("Number of Files :" + files.length);
			for (int i = 0; i < files.length; i++){
				if (files[i].isFile()){
					list.add(files[i]);
					LOG.info("Adding File :" + files[i].getName());
				} else if (recursive) {
					list.add(files[i]);
					LOG.info("Sub Dir :" + files[i].getName());
					list.addAll(getFilesFromDir(files[i], recursive));;
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param file - Directory to get files from.
	 * @return List of Files within that directory and all sub directories.
	 * @throws UpdateFileException 
	 */
	public static void deleteFoldersFromDir(File file) throws UpdateFileException{
		try {
			File[] files = file.listFiles();
			if (files != null){
				for (int i = 0; i < files.length; i++){
					if (files[i].listFiles().length != 0){
						LOG.info("Sub Dir :" + files[i].getName());
						deleteFoldersFromDir(files[i]);
					} else {
						LOG.info("Dir :" + files[i].getName());
						files[i].delete();
					}
				}
			}
			file.delete();
		} catch (Exception e){
			throw new UpdateFileException("Given String does not exist as either a File or Directory"); 
		}
	}
	
	
	/**
	 * 
	 * @param path - Directory of the files you want to be changed.
	 * @param startTrim - The number of characters at the start of the File name you want removing.
	 * @param endTrim - The number of characters from the end of the File name you want removing.
	 * @throws UpdateFileException 
	 */
	public static int updateFileNames(String path, boolean uppercaseify) throws UpdateFileException {
		File file = new File(path);
		int fileNum = 0;
		if (file.exists()){
			if (file.isDirectory()) {
				if (!path.endsWith("\\")){
					path = path + "\\";
				}
				File[] files =  file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()){
						String fName = getFileName(files[i], uppercaseify);
						LOG.info("Multiple Files :" + path + fName);
						fileNum = i + 1;
						LOG.info("File Number: " + fileNum);
						files[i].renameTo(new File(path + fName));
					}
				}
			} else if (file.isFile()) {
				path = path.substring(0, path.lastIndexOf("\\") + 1);
				String fName = getFileName(file, uppercaseify);
				LOG.info("Single File :" + path + fName);
				file.renameTo(new File(path + fName));
			} else {
				throw new UpdateFileException("Given String does not exist as either a File or Directory");
			}
		} else {
			throw new UpdateFileException("Given File/Directory does not exist.");
		}
		return fileNum;
	}
	
	/**
	 * 
	 * @param file - The File to trim the name of.
	 * @param startTrim - The number of characters at the start of the File name you want removing.
	 * @param endTrim - The number of characters from the end of the File name you want removing.
	 * @return The end File name with Extension.
	 */
	private static String getFileName(final File file, boolean uppercaseify) {
		String fName = file.getName();
		String extension = fName.substring(fName.lastIndexOf('.'));
		fName = fName.replace('.', ' ').replace('_', ' ').replaceAll("\\[(.*?)\\]", "").replaceAll("\\((.*?)\\)", "");
		if (uppercaseify) {
			char[] characters = fName.toCharArray();
			for (int i = 0; i < characters.length; i++) {
				if (i == 0) {
					characters[i] = Character.toUpperCase(characters[i]);
				}
				if (characters[i] == ' ' && i + 1 < characters.length) {
					characters[i + 1] = Character.toUpperCase(characters[i + 1]);
				}
			}
			fName = String.valueOf(characters);
		}
		
		fName = (fName.substring(0, fName.length() - extension.length()).trim() + extension);
		return fName;
	}
	
}
