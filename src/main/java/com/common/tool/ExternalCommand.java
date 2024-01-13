package com.common.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExternalCommand {

	private ExternalCommand() {
	}

	/**
	 * 执行外部命令。
	 * 
	 * @param command 命令行
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	public static void execCommand(String[] command) {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = null;
		try {
			process = processBuilder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (process == null) {
			return;
		}
		try (InputStream input = process.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {

			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuilder.append(line);
			}
			int code = process.waitFor();
			log.info("执行内容：{}，操作码：{}", strBuilder.toString(), code);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 执行Linux服务器的shell脚本
	 * @param 脚本的路径
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	public static void execLinuxShell(String scriptPath) {
		String[] command = { "/bin/bash", "-c", scriptPath };
		execCommand(command);
	}
}
