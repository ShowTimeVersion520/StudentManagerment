package com.showtime.service.commons.utils.message;

import com.showtime.model.message.Message;

public class MessageUtils {

    /**
     * 设置请求返回参数
     *
     * @param messageCode 消息状态码
     * @param messageStatus 消息状态
     * @param messageDescription 消息描述
     * @param data 消息具体数据
     * @param <T> 消息具体数据类型
     * @return TorinoSrcMessage封装好的数据
     */
    public static <T> Message<T> setMessage(String messageCode, String messageStatus, String messageDescription, T data){
        Message<T> message = new Message<T>();
        message.setMessageCode(messageCode);
        message.setMessageStatus(messageStatus);
        message.setMessageDescription(messageDescription);
        message.setData(data);
        return message;
    }
}
