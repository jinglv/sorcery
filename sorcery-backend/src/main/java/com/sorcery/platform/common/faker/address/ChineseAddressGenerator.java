package com.sorcery.platform.common.faker.address;

import com.sorcery.platform.common.faker.BaseGenericGenerator;
import org.apache.commons.lang3.RandomUtils;

/**
 * 中国地址数据生成
 *
 * @author jinglv
 * @date 2022/7/20 15:23
 */
public class ChineseAddressGenerator extends BaseGenericGenerator {

    private static final BaseGenericGenerator INSTANCE = new ChineseAddressGenerator();

    public static BaseGenericGenerator getInstance() {
        return INSTANCE;
    }

    private ChineseAddressGenerator() {
    }

    @Override
    public String generate() {
        return genProvinceAndCity() + ChineseChar.genRandomLengthChineseChars(2, 3) + "路" +
                RandomUtils.nextInt(1, 8000) + "号" +
                ChineseChar.genRandomLengthChineseChars(2, 3) + "小区" +
                RandomUtils.nextInt(1, 20) + "单元" +
                RandomUtils.nextInt(101, 2500) + "室";
    }

    private static String genProvinceAndCity() {
        return ChineseAreaList.getPROVINCE_CITY_LIST().get(
                RandomUtils.nextInt(0, ChineseAreaList.getPROVINCE_CITY_LIST().size()));
    }

}
