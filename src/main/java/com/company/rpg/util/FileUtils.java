package com.company.rpg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Util class for handling file operations
 * 
 * @author chv8kor
 *
 */
public class FileUtils {
	private static final String EXCEPTION_WHILE_READING_FILE_CONTENT = "exception while reading file content";
	private final static Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

	/**
	 * Reading file contents to a String object.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileContentToString(String fileName) {
		String outputString = null;
		String filePath = deriveFilePath(fileName);
		try {
			//contents needs to read from jar
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(FileUtils.class.getResourceAsStream(filePath)));

			outputString =  buffer.lines().collect(Collectors.joining("\n"));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, EXCEPTION_WHILE_READING_FILE_CONTENT, e);
		}
		return outputString;

	}

	public static List<String> listFileContents(String fileName) {
		List<String> characterList = new ArrayList<>();
		String filePath = deriveFilePath(fileName);
		try {
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(FileUtils.class.getResourceAsStream(filePath)));
			buffer.lines().forEach(s -> {
				characterList.add(s);
			});
	
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, EXCEPTION_WHILE_READING_FILE_CONTENT, e);
		}
		return characterList;

	}

	/**
	 * Deriving resource name.
	 * 
	 * @param fileName
	 * @return
	 */
	private static String deriveFilePath(String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append("/");
		builder.append(fileName);
		builder.append(".txt");
		return builder.toString();
	}
}
