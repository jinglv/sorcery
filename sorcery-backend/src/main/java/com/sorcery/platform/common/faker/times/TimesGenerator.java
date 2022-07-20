package com.sorcery.platform.common.faker.times;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.sorcery.platform.common.faker.BaseGenericGenerator;

import java.util.Date;

/**
 * 时间格式化
 *
 * @author jinglv
 * @date 2022/7/20 16:17
 */
public class TimesGenerator extends BaseGenericGenerator {

    private static final BaseGenericGenerator INSTANCE = new TimesGenerator();

    public static BaseGenericGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String generate() {
        return String.valueOf(getTimeStamp());
    }

    /**
     * 获取当前时间戳
     *
     * @return 返回当前时间戳
     */
    private static Long getTimeStamp() {
        return System.currentTimeMillis();
    }


    /**
     * 获取当前的时间，传入指定偏移天数
     *
     * @param num 偏移天数量
     * @return 日期格式化输出格式：yyyy-MM-dd HH:mm:ss
     */
    public static String dateOfMigration(int num) {
        // 获取当前时间
        Date nowDate = DateUtil.parse(DateUtil.now());
        // 根据传入的值，当前时间偏移天数
        DateTime dateTime = DateUtil.offsetDay(nowDate, num);
        // 格式化输出，格式为：yyyy-MM-dd HH:mm:ss
        return DateUtil.format(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前的时间格式化，传入指定偏移天数
     *
     * @param num       偏移天数量
     * @param formatStr 格式化的格式
     * @return 日期格式化输出格式：yyyy-MM-dd HH:mm:ss
     */
    public static String dateOfMigration(int num, String formatStr) {
        // 获取当前时间
        Date nowDate = DateUtil.parse(DateUtil.now());
        // 根据传入的值，当前时间偏移天数
        DateTime dateTime = DateUtil.offsetDay(nowDate, num);
        // 格式化输出，格式为：yyyy-MM-dd HH:mm:ss
        return DateUtil.format(dateTime, formatStr);
    }
}
