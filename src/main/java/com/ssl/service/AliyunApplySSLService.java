package com.ssl.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.aliyun.cas20200407.Client;
import com.aliyun.cas20200407.models.CreateCertificateRequestRequest;
import com.aliyun.cas20200407.models.CreateCertificateRequestResponse;
import com.aliyun.cas20200407.models.CreateCertificateRequestResponseBody;
import com.aliyun.cas20200407.models.DeleteUserCertificateRequest;
import com.aliyun.cas20200407.models.DeleteUserCertificateResponse;
import com.aliyun.cas20200407.models.DescribeCertificateStateRequest;
import com.aliyun.cas20200407.models.DescribeCertificateStateResponse;
import com.aliyun.cas20200407.models.DescribeCertificateStateResponseBody;
import com.aliyun.cas20200407.models.ListUserCertificateOrderRequest;
import com.aliyun.cas20200407.models.ListUserCertificateOrderResponse;
import com.aliyun.cas20200407.models.ListUserCertificateOrderResponseBody;
import com.aliyun.cas20200407.models.ListUserCertificateOrderResponseBody.ListUserCertificateOrderResponseBodyCertificateOrderList;
import com.common.tool.ExternalCommand;
import com.common.tool.FileTool;
import com.config.AliyunSSLConfig;
import com.config.ScriptConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AliyunApplySSLService {
	
	@Autowired
	private AliyunSSLConfig aliyunSSLConfig;
	
	@Autowired
	private ScriptConfig scriptConfig;
	
	@Autowired
	private Client casClient;
	
	private static final String[] NO_CREATE_STATUS = {"PAYED", "CHECKING", "NOTACTIVATED"};
	
	private static final String[] IN_USE_STATUS = {"ISSUED", "WILLEXPIRED"};
	
	/** 不需要重新创建证书的状态 */
	private static final List<String> NO_CREATE_STATUS_LIST = new ArrayList<>();
	
	/** 证书正常使用状态 */
	private static final List<String> IN_USE_STATUS_LIST = new ArrayList<>();
	
	/** 数据写入的文件名称 */
	private static final String FILE_NAME = "certificate-order.txt";
	
	/** 私钥文件的后缀 */
	private static final String PRIVATE_KEY_SUFFIX = ".key";
	
	/** 公钥文件的后缀 */
	private static final String PUBLIC_KEY_SUFFIX = ".pem";
	
	/** 文件路径分隔符 */
	private static final String FILE_SEPARATOR = "/";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public AliyunApplySSLService() {
		NO_CREATE_STATUS_LIST.addAll(Arrays.asList(NO_CREATE_STATUS));
		IN_USE_STATUS_LIST.addAll(Arrays.asList(IN_USE_STATUS));
	}

	/**
	 * 查询所有的证书信息列表。
	 * 操作权限 yundun-cert:ListUserCertificateOrder
	 * @return ListUserCertificateOrderResponseBody 证书信息列表
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	public ListUserCertificateOrderResponseBody getCertificateList() {
		ListUserCertificateOrderRequest request = new ListUserCertificateOrderRequest();
		ListUserCertificateOrderResponse result = null;
		try {
			result = casClient.listUserCertificateOrder(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null || result.getStatusCode().intValue() != HttpStatus.OK.value()) {
			return null;
		}
		return result.getBody();
	}
	
	/**
	 * <pre>
	 * 是否需要创建新的SSL证书。
	 * 1、如果存在审批中或者待申请的，则也不会继续创建；
	 * 2、针对即将过期或者已签发状态的数据，结束时间与当前时间是否超过设置的天数，如果没有临界数据则也不需要创建；
	 * 注： 
	 * 1、如果存在的证书列表过多需要处理，还会增加定时删除超期的证书，保证数据一页可以查询完全！！
	 * 2、如果查询数据异常则不会继续创建证书，防止过多消耗证书次数！！
	 * </pre>
	 * @return boolean true需要；false不需要
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	public boolean isCreateCertificate() {
		ListUserCertificateOrderResponseBody body = getCertificateList();
		if(body == null) {
			return false;
		}
		
		LocalDateTime curr = LocalDateTime.now();
		
		List<ListUserCertificateOrderResponseBodyCertificateOrderList> dataList = body.getCertificateOrderList();
		for(ListUserCertificateOrderResponseBodyCertificateOrderList item: dataList) {
			//判断是否存在审批中的证书
			if(NO_CREATE_STATUS_LIST.contains(item.getStatus())) {
				return false;
			}
			if(IN_USE_STATUS_LIST.contains(item.getStatus())) {
				//需要判断一下结束日期和当前日期的差
				LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getCertEndTime()), ZoneId.systemDefault());
				if(endTime.isAfter(curr.plusDays(aliyunSSLConfig.getAdvance()))) {
					//存在正在使用的证书则不需要重新创建！！
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * <pre>
	 * 创建SSL证书。
	 * 操作权限 yundun-cert:CreateCertificateRequest
	 * </pre>
	 * 
	 * @author zhouhui
	 * @since 1.0.0
	 */
	public boolean createCertificate() {
		CreateCertificateRequestRequest request = new CreateCertificateRequestRequest();
		request.setProductCode(aliyunSSLConfig.getProductCode());
		request.setUsername(aliyunSSLConfig.getUserName());
		request.setPhone(aliyunSSLConfig.getPhone());
		request.setEmail(aliyunSSLConfig.getEmail());
		request.setDomain(aliyunSSLConfig.getDomain());
		request.setValidateType(aliyunSSLConfig.getValidateType());
		try {
			CreateCertificateRequestResponse result = casClient.createCertificateRequest(request);
			if(result == null || result.getStatusCode().intValue() != HttpStatus.OK.value()) {
				return false;
			}
			CreateCertificateRequestResponseBody body = result.getBody();
			FileTool.writeToRootFile(FILE_NAME, body.getOrderId() + "");
			log.info("订单编号：" + body.getOrderId());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * <pre>
	 * 根据订单号查询SSL证书的状态。
	 * 1、如果返回已签发，则将证书保存到指定的路径中；
	 * 2、清空配置文件中的订单编号。
	 * 
	 * domain_verify：待验证，表示证书申请提交后，您还没有完成域名验证。
	 * process：审核中，表示证书申请处于 CA 中心审核环节。
	 * verify_fail：审核失败，表示证书申请审核失败。
	 * certificate：已签发，表示证书已经签发。
	 * payed：待申请，表示待申请证书。
	 * unknow：状态未知。
	 * 
	 * 操作权限 yundun-cert:DescribeCertificateState
	 * </pre>
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public void saveCertificate() {
		String orderId = FileTool.readValueFromRoot(FILE_NAME);
		if(!StringUtils.hasText(orderId)) {
			return;
		}
		DescribeCertificateStateRequest request = new DescribeCertificateStateRequest();
		request.setOrderId(Long.valueOf(orderId));
		try {
			DescribeCertificateStateResponse result = casClient.describeCertificateState(request);
			if(result == null || result.getStatusCode().intValue() != HttpStatus.OK.value()) {
				return;
			}
			DescribeCertificateStateResponseBody body = result.getBody();
			//写入日志方便查询
			if(body.getType().equals("certificate")) {
				//已经签发
				//清空订单编号，防止重复触发查询
				FileTool.writeToRootFile(FILE_NAME, "");
				
				String filePrefix = aliyunSSLConfig.getDomain();
				//保存私钥
				saveCertificateKey(filePrefix + PRIVATE_KEY_SUFFIX, body.getPrivateKey());
				//保存公钥
				saveCertificateKey(filePrefix + PUBLIC_KEY_SUFFIX, body.getCertificate());
				
				//执行一下服务器的脚本
				if(StringUtils.hasText(scriptConfig.getPath())) {
					execCommand();
				}
			}else if(body.getType().equals("domain_verify")){
				//域名验证
				if(body.getValidateType().equals("FILE") && StringUtils.hasText(body.getUri())) {
					saveValidateFile(body.getUri(), body.getContent());
				}
			}else {
				log.info("SSL申请信息：" + mapper.writeValueAsString(body));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行脚本命令
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	private void execCommand() {
		if("Linux".equals(scriptConfig.getType())) {
			ExternalCommand.execLinuxShell(scriptConfig.getPath());
		}else if("Window".equals(scriptConfig.getType())) {
			String[] commands = {scriptConfig.getPath()};
			ExternalCommand.execCommand(commands);
		}
	}
	
	/**
	 * 保存认证文件内容
	 * @param fileUrl 认证文件路径+文件名
	 * @param content 认证内容
	 *
	 * @author zhouhui
	 * @since 1.0.0
	 */
	private void saveValidateFile(String fileUrl, String content) {
		String[] paths = fileUrl.split(FILE_SEPARATOR);
		if (paths == null || paths.length == 0) {
			return;
		}
		StringBuilder pathBuilder = new StringBuilder();
		String fileName = null;
		int index = 0;
		for (int i = 0; i < paths.length; i++) {
			if(i == paths.length - 1) {
				fileName = paths[i];
			}else {
				if(index > 0) {
					pathBuilder.append(FILE_SEPARATOR);
				}
				pathBuilder.append(paths[i]);
				index ++;
			}
		}
		String path = null;
		if(aliyunSSLConfig.getValidateFileUrl().endsWith(FILE_SEPARATOR)) {
			path = aliyunSSLConfig.getValidateFileUrl() + pathBuilder.toString();
		}else {
			path = aliyunSSLConfig.getValidateFileUrl() + FILE_SEPARATOR + pathBuilder.toString();
		}
		FileTool.writeToFile(path, fileName, content);
	}
	
	/**
	 * <pre>
	 * 保存证书的文件到指定路径
	 * </pre>
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	private void saveCertificateKey(String filePrefix, String content) {
		String savePath = aliyunSSLConfig.getSavePath();
		if(!StringUtils.hasText(savePath)) {
			savePath = System.getProperty("projectpath");
		}
		FileTool.writeToFile(savePath, filePrefix, content);
	}
	
	/**
	 * 删除超期的SSL证书信息
	 * 
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public void deleteExpiredCertificate() {
		ListUserCertificateOrderResponseBody body = getCertificateList();
		if(body == null) {
			return;
		}
		
		LocalDateTime curr = LocalDateTime.now();
		
		List<ListUserCertificateOrderResponseBodyCertificateOrderList> dataList = body.getCertificateOrderList();
		for(ListUserCertificateOrderResponseBodyCertificateOrderList item: dataList) {
			if("EXPIRED".equals(item.getStatus())) {
				LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getCertEndTime()), ZoneId.systemDefault());
				if(curr.isAfter(endTime.plusDays(aliyunSSLConfig.getExpiredPeriod()))) {
					//超过保留的天数
					Long certificateId = item.getCertificateId();
					deleteCertificate(certificateId);
				}
			}
		}
	}
	
	/**
	 * 根据证书的Id删除对应的证书
	 * 操作权限 yundun-cert:DeleteUserCertificate
	 * 
	 * @author zhouhui
	 * @since 1.0.0 
	 */
	public void deleteCertificate(Long certificateId) {
		DeleteUserCertificateRequest request = new DeleteUserCertificateRequest();
		request.setCertId(certificateId);
		try {
			DeleteUserCertificateResponse result = casClient.deleteUserCertificate(request);
			if(result == null || result.getStatusCode().intValue() != HttpStatus.OK.value()) {
				return;
			}
			log.info("删除证书Id：{}，请求Id：{}", certificateId, result.getBody().getRequestId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
