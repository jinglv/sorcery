package com.sorcery.platform.common.faker.card;

import com.google.common.collect.Maps;
import com.sorcery.platform.common.faker.BaseGenericGenerator;
import com.sorcery.platform.common.faker.address.ChineseAreaList;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 随机生成身份证号码
 * <p>
 * 1.号码的结构
 * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 2.地址码(前六位数）
 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 3.出生日期码（第七位至十四位）
 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 5.校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * （2）计算模 Y = mod(S, 11)
 * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
 *
 * @author jinglv
 * @date 2022/7/20 16:03
 */
public class ChineseIDCardNumberGenerator extends BaseGenericGenerator {
    private static final BaseGenericGenerator INSTANCE = new ChineseIDCardNumberGenerator();

    public static BaseGenericGenerator getInstance() {
        return INSTANCE;
    }

    public ChineseIDCardNumberGenerator() {
        // 身份证号码生成
    }

    /**
     * 生成签发机关：XXX公安局/XX区分局
     *
     * @return 签发机关
     */
    public static String generateIssueOrg() {
        return ChineseAreaList.getCITY_NAME_LIST()
                .get(RandomUtils.nextInt(0, ChineseAreaList.getCITY_NAME_LIST().size())) + "公安局某某分局";
    }

    /**
     * 身份证号码生成
     *
     * @return 生成身份证号
     */
    @Override
    public String generate() {
        Map<String, String> code = getAreaCode();
        String areaCode = code.keySet().toArray(new String[0])[RandomUtils
                .nextInt(0, code.size())]
                + StringUtils.leftPad((RandomUtils.nextInt(0, 9998) + 1) + "", 4,
                "0");
        String birthday = new SimpleDateFormat("yyyyMMdd").format(randomDate());
        String randomCode = String.valueOf(1000 + RandomUtils.nextInt(0, 999)).substring(1);
        String pre = areaCode + birthday + randomCode;
        String verifyCode = getVerifyCode(pre);
        return pre + verifyCode;
    }

    /**
     * 身份证生效期，例如：生成有效期限：20150906-20350906
     *
     * @return 返回身份证有效时间
     */
    public static String generateValidPeriod() {
        DateTime beginDate = new DateTime(randomDate());
        String formatter = "yyyyMMdd";
        DateTime endDate = beginDate.withYear(beginDate.getYear() + 20);
        return beginDate.toString(formatter) + "-" + endDate.toString(formatter);
    }

    /**
     * 随即生成时间
     *
     * @return 时间
     */
    public static Date randomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.FEBRUARY, 1);
        long earlierDate = calendar.getTime().getTime();
        calendar.set(2000, Calendar.FEBRUARY, 1);
        long laterDate = calendar.getTime().getTime();
        long chosenDate = RandomUtils.nextLong(earlierDate, laterDate);
        return new Date(chosenDate);
    }

    private static String getVerifyCode(String cardId) {
        String[] valCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        int tmp = 0;
        for (int i = 0; i < wi.length; i++) {
            tmp += Integer.parseInt(String.valueOf(cardId.charAt(i)))
                    * Integer.parseInt(wi[i]);
        }
        int modValue = tmp % 11;
        return valCodeArr[modValue];
    }

    private static Map<String, String> getAreaCode() {
        final Map<String, String> map = Maps.newHashMap();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        return map;
    }
}
