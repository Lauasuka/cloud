package io.amoe.cloud.exception;


import io.amoe.cloud.enums.BizResponseStatus;

/**
 * @author Amoe
 */
public class BizException extends AbstractException {

    public BizException() {
        this(BizResponseStatus.BIZ_ERROR);
    }

    public BizException(BizResponseStatus status){
        super(status);
    }
}
