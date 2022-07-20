package com.sorcery.platform.common.faker.bank;

/**
 * 银行卡类型枚举类
 *
 * @author jinglv
 * @date 2022/7/20 15:47
 */
public enum BankCardTypeEnum {
    /**
     * 借记卡/储蓄卡
     */
    DEBIT("借记卡/储蓄卡"),
    /**
     * 信用卡/贷记卡
     */
    CREDIT("信用卡/贷记卡");

    private final String name;

    BankCardTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
