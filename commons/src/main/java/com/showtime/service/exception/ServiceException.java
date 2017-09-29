/*
 * 广州丰石科技公司有限公司拥有本软件版权2016并保留所有权利。
 * Copyright 2016, GuangZhou Rich Stone Data Technologies Company Limited, 
 * All rights reserved.
 */
package com.showtime.service.exception;

public class ServiceException extends ApplicationException {

    /**
     * The Constant CONFLICT.
     */
    public static final String CONFLICT = "Conflict";

    /**
     * The Constant NOT_FOUND.
     */
    public static final String NOT_FOUND = "NotFound";

    /**
     * Serialization ID
     * @since diamond-service-commons 1.0
     */
    private static final long serialVersionUID = 3894491214688661572L;

    /**
     * Instantiates a new diamond service exception.
     */
    public ServiceException(){}

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message the message
     */
    public ServiceException(String message){
        super(message);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public ServiceException(String message, String errorCode){
        super(message);
        this.setErrorCode(errorCode);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause){
        super(message,cause);
    }

    /**
     * Instantiates a new Diamond service exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public ServiceException(String message, Throwable cause, String errorCode){
        super(message,cause);
        this.setErrorCode(errorCode);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause){
        super(cause);
    }

    /**
     * Instantiates a new diamond service exception.
     *
     * @param cause     the cause
     * @param errorCode the error code
     */
    public ServiceException(Throwable cause, String errorCode){
        super(cause);
        this.setErrorCode(errorCode);
    }

}
