# certificate-ssl

#### 介绍
阿里云SSL证书自动续期。<br>
目前并不是完全自动，每年还是需要购买次数，主要解决阿里云SSL证书3个月有效期的问题，<br>
但也可以选择腾讯免费的SSL证书，目前腾讯SSL证书还是1年！！

####  :exclamation: 注意事项
- 项目没有做认证处理，因此项目端口不要对外开放
- 目前项目只支持阿里云的SSL自动替换，每年还需要购买次数（阿里云每年会自动重置次数）
- 只支持单台服务器替换
- 只是在nginx环境下进行测试
- 域名校验通过文件的方式进行校验（目前也只实现这种方式，将校验文件写入指定目录）

####  :book: 项目环境
- Java 8
- Spring Boot 2.6.15
- Maven 3.6.3
- 阿里云RAM账号（登录阿里云管理端，并设置对应权限）

#### 调用接口
- [查询用户证书或者订单列表](https://help.aliyun.com/zh/ssl-certificate/developer-reference/api-cas-2020-04-07-listusercertificateorder?spm=a2c4g.11186623.0.0.1f244c279rKYIW)
- [完成DV证书购买申请和签发流程](https://help.aliyun.com/zh/ssl-certificate/developer-reference/api-cas-2020-04-07-createcertificaterequest?spm=a2c4g.11186623.0.0.c6f47b2a4gWflJ)
- [查询DV证书的申请状态](https://help.aliyun.com/zh/ssl-certificate/developer-reference/api-cas-2020-04-07-describecertificatestate?spm=a2c4g.11186623.0.0.58f06622ljH56s)
- [删除证书](https://help.aliyun.com/zh/ssl-certificate/developer-reference/api-cas-2020-04-07-deleteusercertificate?spm=a2c4g.11186623.0.0.3bfc46b6LPKsUr)
