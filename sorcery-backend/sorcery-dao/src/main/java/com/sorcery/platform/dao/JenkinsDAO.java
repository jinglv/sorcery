package com.sorcery.platform.dao;

import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.vo.jenkins.JenkinsInfoSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @return JenkinsInfo
     */
    JenkinsInfo getJenkinsById(Long jenkinsId);

    /**
     * 根据Jenkins名称，查询Jenkins信息
     *
     * @param jenkinsName Jenkins名称
     * @return JenkinsInfo
     */
    JenkinsInfo getJenkinsByName(String jenkinsName);

    /**
     * 查询Jenkins信息的总数
     *
     * @param params 参数
     * @return JenkinsInfo
     */
    Integer pageCountJenkinsInfo(@Param("params") JenkinsInfoSearchVO params);

    /**
     * 查询所有Jenkins信息列表
     *
     * @param jenkinsInfoSearchVO 查询条件
     * @param pageNum             分页的每页数量
     * @param pageSize            分页页数
     * @return JenkinsInfo
     */
    List<JenkinsInfo> pageJenkinsInfoList(@Param("params") JenkinsInfoSearchVO jenkinsInfoSearchVO, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * 更新Jenkins信息
     *
     * @param jenkinsId   Jenkins id
     * @param jenkinsInfo 更新Jenkins信息参数
     * @return int
     */
    Integer updateJenkinsInfo(@Param("jenkinsId") Long jenkinsId, @Param("jenkins") JenkinsInfo jenkinsInfo);

    /**
     * 查询所有Jenkins信息
     *
     * @return JenkinsInfo
     */
    List<JenkinsInfo> selectAllJenkinsInfo();

}
