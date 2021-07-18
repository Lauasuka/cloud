package io.amoe.cloud.exception;

import io.amoe.cloud.enums.IStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amoe
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractException extends RuntimeException {
    private IStatusEnum status;

    public AbstractException(IStatusEnum status) {
        super(status.getMessage());
        this.status = status;
    }
}
