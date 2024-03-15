package com.sorcery.platform.exception;

import com.sorcery.platform.enums.ApiCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2022/8/31 17:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConditionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    public ConditionException() {
        super();
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public ConditionException(String message) {
        super(message);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public ConditionException(Throwable cause) {
        super(cause);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public ConditionException(String message, Throwable cause) {
        super(message, cause);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public ConditionException(String code, String message) {
        this(code, message, null);
    }

    public ConditionException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
