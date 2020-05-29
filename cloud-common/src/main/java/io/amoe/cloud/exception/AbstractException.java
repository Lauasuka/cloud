package io.amoe.cloud.exception;

import io.amoe.cloud.enums.BizResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amoe
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractException extends RuntimeException {
    private BizResponseStatus status;

    AbstractException(BizResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
