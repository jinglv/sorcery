package com.sorcery.platform.support;

import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author jinglv
 * @date 2022/9/1 18:12
 */
@Component
@ControllerAdvice(annotations = {UserToken.class})
public class UserSupport {

    /**
     * 根据登录信息，获取用户id
     *
     * @return 用户id
     */
    @ModelAttribute
    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = Objects.requireNonNull(requestAttributes).getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId < 0) {
            throw new ConditionException("非法用户！");
        }
        return userId;
    }
}
