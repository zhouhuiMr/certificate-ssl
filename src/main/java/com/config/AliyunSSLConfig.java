package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun.ssl")
public class AliyunSSLConfig {

	/** 提前创建证书的天数 */
	private Long advance;
	
	/** digicert-free-1-free（默认）：表示 DigiCert DV 单域名证书（3 个月免费证书） */
	private String productCode;
	
	/** 用户名 */
	private String userName;
	
	/** 手机号 */
	private String phone;
	
	/** 邮箱地址 */
	private String email;
	
	/** 创建证书的域名 */
	private String domain;
	
	/** 验证方式，DNS或者FILE （建议用FILE，将认证信息写入对应的路径） */
	private String validateType;
	
	/** 验证文件的路径（如果选择“FILE”验证，必须填写） */
	private String validateFileUrl;
	
	/** 证书保存的路径 */
	private String savePath;
	
	/** 证书超期后保存的天数 */
	private Integer expiredPeriod;
}
