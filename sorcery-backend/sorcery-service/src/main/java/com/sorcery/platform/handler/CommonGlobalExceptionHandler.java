package com.sorcery.platform.handler;

import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.enums.ApiCodeEnum;
import com.sorcery.platform.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用全局异常处理器
 *
 * @author jinglv
 * @date 2022/8/31 17:49
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request, Exception e) {
        String errorMsg = e.getMessage();
        if (e instanceof ConditionException) {
            return JsonResponse.fail();
        } else {
            e.printStackTrace();
            return JsonResponse.ofCode(ApiCodeEnum.SERVICE_ERROR, errorMsg);
        }
    }
}
