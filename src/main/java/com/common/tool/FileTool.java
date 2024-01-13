package com.common.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class FileTool {

	private FileTool() {}

	/**
	 * 将字符串写入项目同级目录的指定文件中
	 * @param fileName 文件名称
	 * @param str 字符串
	 *
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public static void writeToRootFile(String fileName, String str) {
		String root = System.getProperty("projectpath");
		writeToFile(root, fileName, str);
	}
	
	/**
	 * 将字符串写入项目同级目录的指定文件中
	 * @param path 文件所在的路径
	 * @param fileName 文件名称
	 * @param str 字符串
	 *
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public static void writeToFile(String path, String fileName, String str) {
		File filePath = new File(path);
		if (!filePath.exists() && !filePath.mkdirs()) {
			return;
		}
		File file = new File(path, fileName);
		if(!file.exists()) {
			try {
				if(!file.createNewFile()) {
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {
			bufferedWriter.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件内容。
	 * @param fileName 文件名称
	 *
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public static String readValueFromRoot(String fileName) {
		String root = System.getProperty("projectpath");
		return readValueFromPath(root, fileName);
	}
	
	/**
	 * 读取指定路径下文件内容。
	 * @param fileName 文件名称
	 *
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public static String readValueFromPath(String path, String fileName) {
		File file = new File(path, fileName);
		if(!file.exists()) {
			return null;
		}
		StringBuilder strBuilder = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strBuilder.toString();
	}
}
