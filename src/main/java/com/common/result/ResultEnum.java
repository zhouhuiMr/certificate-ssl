package com.common.result;

/**
 * 成功或者异常的信息记录， 0001的异常时可以自定义异常信息。
 * 
 * @author zhouhui
 */
public enum ResultEnum {
	/** -- 基本的异常信息 -- */
	/** 错误 */
	ERROR("0001", "错误"),
	/** 禁止访问 */
	FORBIDDEN("0002", "禁止访问"),
	/** 等待超时 */
	WAITINGTIMEOUT("0003", "等待超时"),
	/** 等待消息服务器返回结果超时 */
	WAITINGTIMEOUT_MQ("0004", "等待消息服务器返回结果超时"),
	/** 登录信息校验失败 */
	TOEKN_ERROR("0005", "登录信息校验失败"),
	/** 登录信息已过期 */
	TOEKN_EXPIRE("0006", "登录信息已过期"),
	/** 验证码错误 */
	AUTHCODE_ERROR("0007", "验证码错误"),
	/** 登录的用户名或者密码错误 */
	LOGIN_ERROR("0008", "登录的用户名或者密码错误"),
	/** 请求的参数异常 */
	PARAMETER_ERROR("0009", "请求的参数异常"),
	/** 请求数据异常 */
	HTTP_REQUEST_ERROR("0010", "请求数据异常"),
	/** JSON数据解析异常 */
	JSON_ERROR("0011", "JSON数据解析异常"),
	/** 系统已接收数据，等待处理结果 */
	ASYNC_SUCCESS("0012", "系统已接收数据，等待处理结果"),
	/** 保存的数据不完整 */
	DATA_INCOMPLETE("0013", "保存的数据不完整"),
	/** 没有用户的信息 */
	NO_USER_ID("0014", "没有用户的信息"),
	/** 服务接口调用失败 */
	FEIGN_ERROR("0015", "服务接口调用失败"),
	/** 服务接口没有返回数据 */
	FEIGN_NO_DATA_ERROR("0016", "服务接口没有返回数据"),
	/** 请求参数包含特殊字符 */
	PARAM_HAVE_SPECIAL_CHARACTER("0017", "请求参数包含特殊字符"),
	/** 重复提交数据 */
	SUBMIT_AGAIN_ERROR("0018", "重复提交数据"),
	/** 请求执行失败，未返回执行结果 */
	HTTP_NO_RESPONSE("0019", "请求执行失败，未返回执行结果"),
	
	/** -- 用户信息异常信息 -- */
	/** 未查询到用户信息 */
	NO_USER_INFO("1001", "未查询到用户信息"),
	/** 未查询到菜单信息 */
	NO_MENU_INFO("1002", "未查询到菜单信息"),
	/** 未查询到角色信息 */
	NO_ROLE_INFO("1003", "未查询到角色信息"),
	/** 未查询到请求地址信息 */
	NO_REQUEST_INFO("1004", "未查询到请求地址信息"),
	/** 登录用户名已存在 */
	REGISTER_CODE_EXIST("1005", "登录用户名已存在"),
	/** 请填写用户编号 */
	REGISTER_NO_CODE("1006", "请填写用户编号"),
	/** 请填写邮箱地址 */
	REGISTER_NO_MAIL("1007", "请填写邮箱地址"),
	/** 请填写密码 */
	REGISTER_NO_PASSWORD("1008", "请填写密码"),
	/** 请填写新密码 */
	REGISTER_NO_NEWPASSWORD("1009", "请填写新密码"),
	/** 密码需要包含数字、字母和,@_.特殊符号两两组合 */
	REGISTER_PASSWORD_FORMAT("1010", "密码需要包含数字、字母和,@_.特殊符号两两组合6-16个字符"),
	/** 输入的旧密码错误 */
	REGISTER_OLD_PASSWORD_ERROR("1011", "输入的旧密码错误"),
	/** 输入的手机号格式错误 */
	REGISTER_TEL_ERROR("1012", "输入的手机号格式错误"),
	/** 输入的邮箱格式错误 */
	REGISTER_MAIL_ERROR("1013", "输入的邮箱格式错误"),
	/** 输入的生日格式错误 */
	REGISTER_BIRTHDAY_ERROR("1014", "输入的生日格式错误"),
	/** 未查询到用户对应的角色信息 */
	NO_USER_ROLE_INFO("1015", "未查询到用户对应的角色信息"),

	/** -- 菜单相关操作 -- */
	/** 错误 */
	MENU_INSERT_ERROR("1021", "新增菜单信息失败"),
	/** 未查询对应的父菜单 */
	MENU_PARENT_QUERY_ERROR("1022", "未查询对应的父菜单"),
	/** 当前菜单存在对应的子菜单，禁止进行删除 */
	MENU_HAVE_CHILDREN_ERROR("1023", "当前菜单存在对应的子菜单，禁止进行删除"),

	/** -- 数据库查询相关的异常 -- */
	/** 数据查询异常 */
	DATA_QUERY_ERROR("2001", "数据查询异常"),
	/** 数据更新失败 */
	DATA_UPDATE_ERROR("2002", "数据更新失败"),
	/** 数据新增失败 */
	DATA_INSERT_ERROR("2003", "数据新增失败"),
	/** 存在多个查询结果集 */
	DATA_QUERY_MULTIPLE("2004", "存在多个查询结果集"),
	/** 存在相同的数据 */
	DATA_QUERY_SAME("2005", "存在相同的数据"),
	/** 数据删除失败 */
	DATA_DELETE_ERROR("2006", "数据删除失败"),
	/** 没有查询到数据 */
	DATA_QUERY_NO_RESULT("2007", "未查询到数据"),

	/** -- 字典项信息异常 -- */
	/** 字典数据不匹配 */
	DICTIONARY_VERIFY_ERROR("2101", "字典数据不匹配"),
	/** 没有查询到字典数据的值 */
	DICTIONARY_VALUE2LABEL_ERROR("2102", "没有查询到字典数据的值"),
	/** 没有查询到字典数据的描述 */
	DICTIONARY_LABEL2VALUE_ERROR("2103", "没有查询到字典数据的描述"),

	/** -- 文件上传的异常信息 -- */
	/** 未上传文件信息 */
	NO_FILE_UPLOAD("3001", "未上传文件信息"),
	/** 未获取EXCEL文件的数据 */
	EXCEL_ANALYSIS_ERROR("3002", "未获取EXCEL文件的数据"),
	/** 上传的文件数据异常 */
	FILE_UPLOAD_DATA_ERROR("3003", "上传的文件数据异常"),
	/** 文件转换zip失败 */
	FILE_TRANSITION_ZIP_ERROR("3004", "文件转换zip失败"),
	/** 导出EXCEL失败 */
	EXPORT_EXCEL_ERROR("3005", "导出EXCEL失败"),
	/** 文件目录不存在 */
	CATALOG_NO_EXIST("3006", "文件目录不存在"),
	/** 上传文件目录不存在 */
	FILE_UPLOAD_PATH_EXIST("3007", "文件目录不存在"),
	/** 上传文件过大 */
	FILE_UPLOAD_SIZE_ERROR("3008", "上传文件过大"),

	/** -- ECS云服务器信息 -- */
	/** 没有查询到ECS服务器信息 */
	NO_ECS_MESSAGE("3011", "没有查询到ECS服务器信息"),
	/** 创建请求客户端失败 */
	REQUEST_CLIENT_ERROR("3012", "创建请求客户端失败"),
	
	/** -- 工作流信息 -- */
	WORKFLOW_NO_DEPLOY_ID("3101", "未设置工作流部署ID"),
	WORKFLOW_NO_INSTANCE("3102", "未查询到工作流"),
	
	/** -- 发送消息异常信息 -- */
	/** 发送邮件消息失败 */
	INFO_MAIL_SEND_ERROR("4001", "发送邮件消息失败"),
	/** 未设置收件人 */
	INFO_MAIL_NO_TO("4002", "未设置收件人"),
	/** 未设置模板信息 */
	INFO_MAIL_NO_TEMPLATE("4003", "未设置模板信息"),

	/** -- 工作流异常信息 -- */
	WORKFLOW_TASK_NO_ASSIGNEE("50001", "工作流任务未设置执行人"),

	SUCCESS("0000", "成功");

	private String code = "";
	private String message = "";

	private ResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
