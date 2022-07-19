package com.sorcery.platform.common.utils;

import com.mifmif.common.regex.Generex;

/**
 * 正则表达式
 *
 * @author jinglv
 * @date 2022/7/18 17:40
 */
public class RegexUtils {
    
    private static final RegexUtils INSTANCE = new RegexUtils();

    public static RegexUtils getInstance() {
        return INSTANCE;
    }

    public Object genDataByReg(String reg) {
        Generex generex = new Generex(reg);
        return generex.random();
    }
}
