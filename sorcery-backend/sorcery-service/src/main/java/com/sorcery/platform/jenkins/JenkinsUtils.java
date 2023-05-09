package com.sorcery.platform.jenkins;

import cn.hutool.json.JSONObject;
import com.sorcery.platform.constant.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * Jenkins工具类
 *
 * @author jinglv
 * @date 2023/5/5 14:19
 */
@Slf4j
public class JenkinsUtils {
    /**
     * Jenkins回调(拼接执行任务修改状态接口)，更新执行测试任务状态
     *
     * @param jenkinsBaseUrl Jenkins基础url
     * @param token          token
     * @param jenkinsTaskId  Jenkins任务Id
     * @return 返回结果
     */
    public static StringBuilder getUpdateTaskStatusUrl(String localUrl, String token, Long jenkinsTaskId) {
        StringBuilder updateStatusUrl = new StringBuilder();
        updateStatusUrl.append("curl -X PUT ");
        updateStatusUrl.append("\"").append(localUrl).append("/api/v1/jenkins/task/status\" ");
        updateStatusUrl.append("-H \"Content-Type: application/json\" ");
        updateStatusUrl.append("-H \"token: ").append(token).append("\" ");
        updateStatusUrl.append("-d ");

        JSONObject json = new JSONObject();
        json.set("jenkinsTaskId", jenkinsTaskId);
        // 更新任务执行状态：执行完成
        json.set("status", Constants.STATUS_THREE);
        // 获取Jenkins中的构建地址
        json.set("buildUrl", "${BUILD_URL}");
        updateStatusUrl.append("'").append(json).append("'");
        log.info("返回更新状态的Url：{}", updateStatusUrl);
        return updateStatusUrl;
    }
}
