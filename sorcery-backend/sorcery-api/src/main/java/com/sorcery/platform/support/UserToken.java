package com.sorcery.platform.support;

import java.lang.annotation.*;

/**
 * @author jinglv
 * @date 2024/3/15 14:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserToken {
}
