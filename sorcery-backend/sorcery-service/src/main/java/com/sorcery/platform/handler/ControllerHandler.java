package com.sorcery.platform.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.enums.ApiCodeEnum;
import com.sorcery.platform.exception.ConditionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jinglv
 * @date 2022/9/7 13:52
 */
@Component
@Slf4j
@Aspect
public class ControllerHandler {

    private final static String TRACE_ID = "TRACE_ID";

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(public * com.sorcery.platform.api..*.*(..)) ")
    public void recordLog() {
    }

    public void setTraceId() {
        if (request != null && request.getHeader("Trace-Id") != null) {
            MDC.put(TRACE_ID, request.getHeader("Trace-Id"));
        } else {
            MDC.put(TRACE_ID, UUID.randomUUID().toString());
        }
    }

    @Around("recordLog()")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        setTraceId();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzSimpleName = clazz.getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameters = joinPoint.getArgs();
        Map<String, Object> parameterMap = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            String parameterName = parameterNames[i];
            Object parameter = parameters[i];
            parameterMap.put(parameterName, parameter);
        }

        String parametersJsonString = JSON.toJSONString(parameterMap, SerializerFeature.WriteMapNullValue);
        log.info("{}#{} args:{}", clazzSimpleName, methodName, parametersJsonString);

        Object response;

        try {
            response = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            log.error("{}#{}, exception:{}:", clazzSimpleName, methodName, e.getClass().getSimpleName(), e);

            JsonResponse<Object> res = JsonResponse.fail(ApiCodeEnum.SERVICE_ERROR);

            if (e instanceof IllegalArgumentException || e instanceof NullPointerException) {
                res.setCode(ApiCodeEnum.BAD_REQUEST.getCode());
                res.setMsg(e.getMessage());
            } else if (e instanceof ConditionException) {
                res.setMsg(e.getMessage());
            }
            response = res;
        }

        if (response instanceof JsonResponse) {
            ((JsonResponse) response).setTraceId(MDC.get(TRACE_ID));
        }

        String resultJsonString = JSON.toJSONString(response, SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
        log.info("{}#{} response:{}", clazzSimpleName, methodName, resultJsonString);
        return response;
    }
}
