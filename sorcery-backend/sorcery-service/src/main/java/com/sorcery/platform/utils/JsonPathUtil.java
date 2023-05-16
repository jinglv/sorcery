package com.sorcery.platform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

/**
 * @author jinglv
 * @date 2023/5/9 17:19
 */
public class JsonPathUtil {
    /**
     * 根据JSONPath表达式提取JSON字符串中的数据
     *
     * @param jsonStr  JSON字符串
     * @param jsonPath JSONPath表达式
     * @return 提取结果
     */
    public static Object extract(String jsonStr, String jsonPath) {
        Object obj = JSON.parse(jsonStr);
        return JSONPath.eval(obj, jsonPath);
    }

    /**
     * 根据JSONPath表达式提取JSON字符串中的数据，并转为指定类型
     *
     * @param jsonStr  JSON字符串
     * @param jsonPath JSONPath表达式
     * @param clazz    指定类型
     * @param <T>      泛型
     * @return 提取结果
     */
    public static <T> T extract(String jsonStr, String jsonPath, Class<T> clazz) {
        Object obj = JSON.parse(jsonStr);
        Object result = JSONPath.eval(obj, jsonPath);
        return JSONObject.parseObject(JSONObject.toJSONString(result), clazz);
    }
}
