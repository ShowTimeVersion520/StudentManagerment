/*
 * 广州丰石科技公司有限公司拥有本软件版权2015并保留所有权利。
 * Copyright 2015, GuangZhou Rich Stone Data Technologies Company Limited, 
 * All rights reserved.
 */
package com.showtime.model.message;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ErrorResponseMessage {

    private String errorResponseMessage;

    public ErrorResponseMessage(){
        super();
    }

    public ErrorResponseMessage(String errorResponseMessage){
        super();
        this.errorResponseMessage = errorResponseMessage;
    }

    public String getErrorResponseMessage() {
        return errorResponseMessage;
    }

    public void setErrorResponseMessage(String errorResponseMessage) {
        this.errorResponseMessage = errorResponseMessage;
    }

}
