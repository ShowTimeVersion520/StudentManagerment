/*
 * 广州丰石科技公司有限公司拥有本软件版权2016并保留所有权利。
 * Copyright 2016, GuangZhou Rich Stone Data Technologies Company Limited, 
 * All rights reserved.
 */
package com.showtime.service.exception;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1960405186815260702L;
    /**
     * Serialization ID
     *
     * @since diamond-service-commons 1.0
     */

    private String errorCode;

    /**
     * Instantiates a new Diamond application exception.
     */
    public ApplicationException() {
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param cause the cause
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the errorCode
     *
     * @return the errorCode
     * @since diamond -service-commons project_version
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the errorCode
     *
     * @param errorCode the errorCode to set
     * @since diamond -service-commons project_version
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
