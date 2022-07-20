package com.sorcery.platform.common.faker.mail;

import com.sorcery.platform.common.faker.BaseGenericGenerator;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机生成邮箱地址
 *
 * @author jinglv
 * @date 2022/7/20 16:09
 */
public class EmailAddressGenerator extends BaseGenericGenerator {

    private static final BaseGenericGenerator INSTANCE = new EmailAddressGenerator();

    public static BaseGenericGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String generate() {
        String result = RandomStringUtils.randomAlphanumeric(10) +
                "@" +
                RandomStringUtils.randomAlphanumeric(5) +
                "." +
                RandomStringUtils.randomAlphanumeric(3);
        return result.toLowerCase();
    }
}
