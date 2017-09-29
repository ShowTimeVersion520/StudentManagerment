package com.showtime.service.commons.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.showtime.service.commons.constants.SmsConstant;

/**
 * 短信验证的 util
 */
public class SmsUtils {

    /**
     * 短信验证服务
     * @param phoneNumber   手机号
     * @param freeSignName  签名
     * @param templateCode  模板编号
     * @param paramString   参数
     * @return              随机码（4位）
     * @throws ApiException
     */
    public static int smsVerification(String phoneNumber, String freeSignName, String templateCode, String paramString) throws ApiException {
        // 生成四位随机数
        int smsCode = (int) (Math.random() * 9000 + 1000);
        TaobaoClient client = new DefaultTaobaoClient(SmsConstant.SMS_SERVER_URL, SmsConstant.SMS_APP_KEY, SmsConstant.SMS_APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(SmsConstant.SMS_EXTEND);
        req.setSmsType(SmsConstant.SMS_TYPE);
        req.setRecNum(phoneNumber);

        req.setSmsFreeSignName(freeSignName);
        req.setSmsTemplateCode(templateCode);
        req.setSmsParamString(String.format(paramString,smsCode));

        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return smsCode;
    }

}
