package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

	/** AccessKey ID */
	private String accessKeyId;
	
	/** AccessKey Secret */
	private String accessKeySecret;
	
	/** Endpoint 请参考 https://api.aliyun.com/product/cas */
	private String sslEndpoint;
}
