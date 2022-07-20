package com.sorcery.platform.common.faker.card;

import org.junit.jupiter.api.Test;

/**
 * @author jinglv
 * @date 2022/7/20 16:06
 */
class ChineseIDCardNumberGeneratorTest {

    @Test
    void generate() {
        String idCard = ChineseIDCardNumberGenerator.getInstance().generate();
        System.out.println(idCard);
    }

    @Test
    void generateValidPeriod() {
        String times = ChineseIDCardNumberGenerator.generateValidPeriod();
        System.out.println(times);
    }
}