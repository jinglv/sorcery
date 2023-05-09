package com.sorcery.platform.dao;

import com.sorcery.platform.domain.JenkinsTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jinglv
 * @date 2023/4/27 16:52
 */
@Mapper
public interface JenkinsTaskDAO {
    /**
     * 新增Jenkins任务信息
     *
     * @param jenkinsTask Jenkins任务信息
     * @return int
     */
    Integer addJenkinsTask(JenkinsTask jenkinsTask);

    /**
     * 根据Jenkins任务Id，查询Jenkins任务信息
     *
     * @param jenkinsTaskId Jenkins Task id
     * @return Project
     */
    JenkinsTask getJenkinsTaskById(Long jenkinsTaskId);

    /**
     * 根据Jenkins任务名称，查询Jenkins任务信息
     *
     * @param jenkinsTaskName Jenkins任务名称
     * @return Project
     */
    JenkinsTask getJenkinsTaskByName(String jenkinsTaskName);

    /**
     * 查询Jenkins任务信息的总数
     *
     * @param params 参数
     * @return Project
     */
    Integer pageCountJenkinsTask(Map<String, Object> params);

    /**
     * 查询所有Jenkins任务信息列表
     *
     * @param params 参数
     * @return Project
     */
    List<JenkinsTask> pageJenkinsTaskList(Map<String, Object> params);

    /**
     * 更新Jenkins信息
     *
     * @param jenkinsTaskId Jenkins id
     * @param jenkinsTask   更新Jenkins信息参数
     * @return int
     */
    Integer updateJenkinsTask(@Param("jenkinsTaskId") Long jenkinsTaskId, @Param("jenkins") JenkinsTask jenkinsTask);

    /**
     * 根据Jenkins任务id，删除Jenkins任务
     *
     * @param jenkinsTaskId Jenkins Task id
     * @return int
     */
    Integer deleteJenkinsTaskById(Long jenkinsTaskId);
}
