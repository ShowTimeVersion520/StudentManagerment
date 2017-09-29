

package com.showtime.service.exception;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ServiceExceptionUtils {
    /**
     * Return http status.
     *
     * @param <T> the generic type
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<T> getHttpStatus(Throwable e, Class<T> requiredType) {
        if (e instanceof ServiceException) {
            ServiceException dse = (ServiceException) e;

            if (ServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
            } else if (ServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<List<T>> getHttpStatusWithList(Throwable e, Class<T> requiredType) {
        if (e instanceof ServiceException) {
            ServiceException dse = (ServiceException) e;

            if (ServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.NOT_FOUND);
            } else if (ServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<List<T>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e the e
     * @return the response entity
     */
    public static <K, T> ResponseEntity<Map<K, List<T>>> getHttpStatusWithMap(
            Throwable e, Class<K> requiredType1, Class<T> requiredType2) {
        if (e instanceof ServiceException) {
            ServiceException dse = (ServiceException) e;

            if (ServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(
                        HttpStatus.NOT_FOUND);
            } else if (ServiceException.CONFLICT
                    .equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<Map<K, List<T>>>(
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Write error log and return http status.
     *
     * @param errorMessage the error message
     * @param e            the e
     * @return the response entity
     */
    public static ResponseEntity<ErrorResponseMessage> getHttpStatusWithResponseMessage(String errorMessage, Throwable e) {
        if (e instanceof ServiceException) {
            ServiceException dse = (ServiceException) e;
            if (ServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new ErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.NOT_FOUND);
            } else if (ServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new ErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(new ErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new ErrorResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<ErrorResponseMessage> getHttpStatusWithResponseMessage(
            String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<ErrorResponseMessage>(
                new ErrorResponseMessage(errorMessage), httpStatus);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<Message<ErrorResponseMessage>> getHttpStatusWithResponseMessage(
            Message<ErrorResponseMessage> errorMessage, Throwable e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof ServiceException) {
            ServiceException dse = (ServiceException) e;
            if (ServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                status = HttpStatus.NOT_FOUND;
            } else if (ServiceException.CONFLICT.equals(dse.getErrorCode())) {
                status = HttpStatus.CONFLICT;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return new ResponseEntity<Message<ErrorResponseMessage>>(
                errorMessage, status);
    }
}
