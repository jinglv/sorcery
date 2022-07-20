package com.sorcery.platform.common.faker;

import java.util.Random;

/**
 * 生成数据基础类
 *
 * @author jinglv
 * @date 2022/7/19 18:09
 */
public abstract class BaseGenericGenerator {
    /**
     * 生成抽象方法
     *
     * @return String
     */
    public abstract String generate();

    private Random random = null;

    protected Random getRandomInstance() {
        if (random == null) {
            random = new Random(System.currentTimeMillis());
        }
        return random;
    }
}
