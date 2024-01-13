package com.ssl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoApplyCertificate {
	
	@Autowired
	private AliyunApplySSLService aliyunApplySSLService;

	/**
	 * 定时判断是否需要需要新建SSL证书，
	 * 如果需要进行申请（保存订单编号，用于后续查询），申请之后进行一次申请状态的查询，
	 * 如果通过进行创建并保存SSL证书信息
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	@Scheduled(cron = "0 0 4 * * ?")
	public void aliyunCertificate() {
		if(aliyunApplySSLService.isCreateCertificate() && aliyunApplySSLService.createCertificate()) {
			aliyunApplySSLService.saveCertificate();
		}
	}
	
	/**
	 * 保存SSL证书信息。
	 * 
	 * @author zhouhui
	 * @since 1.0.0
	 */
	@Scheduled(cron = "0 0 0/2 * * ?")
	public void saveCertificate() {
		aliyunApplySSLService.saveCertificate();
	}
	
	/**
	 * 删除已经过期，并且超过保留期的证书信息。
	 * 
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	@Scheduled(cron = "0 20 2 * * ?")
	public void deleteCertificate() {
		aliyunApplySSLService.deleteExpiredCertificate();
	}
}
