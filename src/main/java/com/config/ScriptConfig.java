package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "script")
public class ScriptConfig {

	/** 脚本类型Linux/Window */
	private String type;
	
	/** 脚本路径 */
	private String path;
}
