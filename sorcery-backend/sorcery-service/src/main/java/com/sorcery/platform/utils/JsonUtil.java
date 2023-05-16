package com.sorcery.platform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author jinglv
 * @date 2023/5/9 15:32
 */
public class JsonUtil {

    /**
     * 将json字符串转化为Map对象
     *
     * @param jsonStr json字符串
     * @return Map对象
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        return jsonObj.getInnerMap();
    }

    /**
     * 将json字符串转化为指定类型的Map对象
     *
     * @param jsonStr json字符串
     * @param clazz   Map的value类型
     * @return Map对象
     */
    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {
        });
    }
}
