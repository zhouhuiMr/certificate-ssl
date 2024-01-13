package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.aliyun.cas20200407.Client;
import com.aliyun.teaopenapi.models.Config;

@Component
public class AliyunRequestClient {
	
	@Autowired
	private AliyunConfig aliyunConfig;

	@Bean
	public Client casClient() {
		Config config = new Config();
		config.setAccessKeyId(aliyunConfig.getAccessKeyId());
		config.setAccessKeySecret(aliyunConfig.getAccessKeySecret());
		config.setEndpoint(aliyunConfig.getSslEndpoint());
		Client casClient = null;
		try {
			casClient = new Client(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return casClient;
	}
}
