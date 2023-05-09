package com.sorcery.platform.dao;

import com.sorcery.platform.domain.JenkinsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jinglv
 * @date 2023/4/27 10:57
 */
@Mapper
public interface JenkinsDAO {

    /**
     * 新增Jenkins信息
     *
     * @param jenkinsInfo 项目信息
     * @return int
     */
    Integer addJenkinsInfo(JenkinsInfo jenkinsInfo);

    /**
     * 根据Jenkins Id，查询Jenkins信息
     *
     * @param jenkinsId Jenkins id
     * @return Project
     */
    JenkinsInfo getJenkinsById(Long jenkinsId);

    /**
     * 根据Jenkins名称，查询Jenkins信息
     *
     * @param jenkinsName Jenkins名称
     * @return Project
     */
    JenkinsInfo getJenkinsByName(String jenkinsName);

    /**
     * 查询Jenkins信息的总数
     *
     * @param params 参数
     * @return Project
     */
    Integer pageCountJenkinsInfo(Map<String, Object> params);

    /**
     * 查询所有Jenkins信息列表
     *
     * @param params 参数
     * @return Project
     */
    List<JenkinsInfo> pageJenkinsInfoList(Map<String, Object> params);

    /**
     * 更新Jenkins信息
     *
     * @param jenkinsId   Jenkins id
     * @param jenkinsInfo 更新Jenkins信息参数
     * @return int
     */
    Integer updateJenkinsInfo(@Param("jenkinsId") Long jenkinsId, @Param("jenkins") JenkinsInfo jenkinsInfo);
}
