package com.showtime.service.commons.constants;

/**
 * 短信验证码常量类
 * Created by Administrator on 2017/7/31.
 */
public final class SmsConstant {

    // 短信验证服务的 url
    public static final String SMS_SERVER_URL = "http://gw.api.taobao.com/router/rest";
    // App Key
    public static final String SMS_APP_KEY = "24564798";
    // 密文
    public static final String SMS_APP_SECRET = "20eee08a967ad3464673091d0b3bd5e1";
    // 扩展
    public static final String SMS_EXTEND = "";
    // 短信类型
    public static final String SMS_TYPE = "normal";


    /*------------------------注册验证-------------------------*/
    // 签名
    public static final String SMS_REGISTER_FREE_SIGN_NAME = "注册验证";
    // 模板编号
    public static final String SMS_REGISTER_TEMPLATE_CODE = "SMS_2740230";
    // 参数
    public static final String SMS_REGISTER_PARAM_STRING = "{code:'%d',product:'酒商网'}";



    /*------------------------登录验证-------------------------*/
    // 签名
    public static final String SMS_LOGIN_FREE_SIGN_NAME = "登录验证";

    // 模板编号
    public static final String SMS_LOGIN_TEMPLATE_CODE = "SMS_2740232";
    // 参数
    public static final String SMS_LOGIN_PARAM_STRING = "{code:'%d',product:'酒商网'}";


    /*------------------------修改密码验证-------------------------*/
    // 签名
    public static final String SMS_PASSWORD_FREE_SIGN_NAME = "变更验证";

    // 模板编号
    public static final String SMS_PASSWORD_TEMPLATE_CODE = "SMS_2740228";
    // 参数
    public static final String SMS_PASSWORD_PARAM_STRING = "{code:'%d',product:'酒商网'}";


    /*------------------------账号绑定-------------------------*/
    // 签名
    public static final String SMS_BINDING_FREE_SIGN_NAME = "变更验证";

    // 模板编号
    public static final String SMS_BINDING_TEMPLATE_CODE = "SMS_85660029";
    // 参数
    public static final String SMS_BINDING_PARAM_STRING = "{code:'%d',product:'酒商网'}";

}
