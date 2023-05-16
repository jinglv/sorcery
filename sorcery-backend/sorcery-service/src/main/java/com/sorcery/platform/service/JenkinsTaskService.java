package com.sorcery.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.JenkinsTask;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.vo.jenkins.JenkinsTaskVO;

import java.util.Map;

/**
 * @author jinglv
 * @date 2023/4/27 16:47
 */
public interface JenkinsTaskService {

    /**
     * 新增Jenkins任务信息
     *
     * @param jenkinsTaskVO Jenkins任务入参
     * @param userId        用户id
     */
    void addJenkinsTask(JenkinsTaskVO jenkinsTaskVO, Long userId);

    /**
     * 根据Id查询Jenkins任务信息
     *
     * @param jenkinsTaskId Jenkins任务主键id
     * @return Jenkins信息
     */
    JenkinsTask getJenkinsTaskById(Long jenkinsTaskId);

    /**
     * 根据name查询Jenkins信息
     *
     * @param jenkinsTaskName Jenkins任务名称
     * @return Jenkins信息
     */
    JenkinsTask getJenkinsTaskByName(String jenkinsTaskName);

    /**
     * 分页查询Jenkins任务信息列表
     *
     * @param params 分页信息
     * @return Jenkins信息列表
     */
    PageResult<JenkinsTask> pageJenkinsTaskList(JSONObject params);

    /**
     * 更新Jenkins任务信息
     *
     * @param jenkinsTaskId Jenkins任务id
     * @param jenkinsTaskVO Jenkins任务信息
     * @param userId        创建人
     */
    void updateJenkinsTask(Long jenkinsTaskId, JenkinsTaskVO jenkinsTaskVO, Long userId);


    /**
     * 删除Jenkins任务信息
     *
     * @param jenkinsTaskId Jenkins任务id
     */
    void deleteJenkinsTask(Long jenkinsTaskId);

    /**
     * 执行Jenkins
     *
     * @param jenkinsTaskVO Jenkins任务信息
     * @param userId        user Id
     * @param jenkinsTaskId Jenkins任务id
     * @param token         token
     */
    void runJenkinsTask(JenkinsTaskVO jenkinsTaskVO, Long jenkinsTaskId, Long userId, String token);

    /**
     * 更新Jenkins任务状态
     *
     * @param jenkinsTaskId Jenkins任务id
     * @param buildUrl      Jenkins构建地址
     * @param status        任务状态
     */
    void updateTaskStatus(Long jenkinsTaskId, String buildUrl, Integer status);

    /**
     * 根据执行的Jenkins任务获取该任务的Allure报告
     *
     * @param jenkinsTaskId Jenkins任务id
     * @return allure报告地址
     */
    Map<String, String> getAllureReport(Long jenkinsTaskId);

}
