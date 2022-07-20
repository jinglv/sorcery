package com.sorcery.platform.exception;

import com.sorcery.platform.response.ApiCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author jinglv
 * @date 2022/7/20 15:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    private final String code;

    public CustomException() {
        super();
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public CustomException(String message) {
        super(message);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.code = ApiCodeEnum.FAIL.getCode();
    }

    public CustomException(String code, String message) {
        this(code, message, null);
    }

    public CustomException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
