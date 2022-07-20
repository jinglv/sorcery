package com.sorcery.platform.common.faker.bank;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import com.sorcery.platform.common.utils.LuhnUtils;

/**
 * 银行卡号校验类
 *
 * @author jinglv
 * @date 2022/7/20 15:47
 */
public class BankCardNumberValidator {

    private static final int LEN_MIN = 16;
    private static final int LEN_MAX = 19;

    private BankCardNumberValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 校验银行卡号是否合法
     *
     * @param cardNo 银行卡号
     * @return 是否合法
     */
    public static boolean validate(String cardNo) {
        if (CharSequenceUtil.isEmpty(cardNo)) {
            return false;
        }
        if (!NumberUtil.isNumber(cardNo)) {
            return false;
        }
        if (cardNo.length() > LEN_MAX || cardNo.length() < LEN_MIN) {
            return false;
        }
        int luhnSum = LuhnUtils.getLuhnSum(cardNo.substring(0, cardNo.length() - 1).trim().toCharArray());
        char checkCode = (luhnSum % 10 == 0) ? '0' : (char) ((10 - luhnSum % 10) + '0');
        return cardNo.substring(cardNo.length() - 1).charAt(0) == checkCode;
    }
}
