package com;

import java.io.File;
import java.net.URL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ClassUtils;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		setProjectPath();
		SpringApplication.run(Application.class, args);
	}
	
	private static void setProjectPath(){
		String classPathFile = null;
		String projectPath = "";
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		if(classLoader == null) {
			return;
		}
		URL resource = classLoader.getResource("");
		if(resource == null) {
			return;
		}
		classPathFile = classLoader.getResource("").getPath();
		File classpath = new File(classPathFile);
		projectPath = classpath.getParentFile().getParentFile().getParentFile().getPath();
		projectPath = projectPath.replaceFirst("file:", "");
		System.setProperty("projectpath", projectPath);
	}
}
