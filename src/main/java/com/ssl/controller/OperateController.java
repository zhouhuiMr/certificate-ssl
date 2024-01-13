package com.ssl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.result.R;
import com.ssl.service.AliyunApplySSLService;
import com.ssl.service.AutoApplyCertificate;

@Controller
@RequestMapping(path = "/operate")
public class OperateController {
	
	@Autowired
	private AliyunApplySSLService aliyunApplySSLService;
	
	@Autowired
	private AutoApplyCertificate autoApplyCertificate;
	

	@GetMapping(path = "/ssl/list")
	@ResponseBody
	public R<Object> getCertificateList() {
		aliyunApplySSLService.getCertificateList();
		return R.ok();
	}
	
	@GetMapping(path = "/ssl/save")
	@ResponseBody
	public R<Object> saveCertificate(){
		aliyunApplySSLService.saveCertificate();
		return R.ok();
	}
	
	@GetMapping(path = "/ssl/apply")
	@ResponseBody
	public R<Object> applyCertificate(){
		autoApplyCertificate.aliyunCertificate();
		return R.ok();
	}
}
