package com.sorcery.platform.service;

import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.vo.jenkins.JenkinsInfoSearchVO;
import com.sorcery.platform.vo.jenkins.JenkinsInfoVO;

import java.util.List;

;

/**
 * @author jinglv
 * @date 2023/4/27 10:43
 */
public interface JenkinsService {
    /**
     * 新增Jenkins信息
     *
     * @param jenkinsInfoVO Jenkins入参
     * @param userId        用户id
     */
    void addJenkinsInfo(JenkinsInfoVO jenkinsInfoVO, Long userId);

    /**
     * 根据Id查询Jenkins信息
     *
     * @param jenkinsId Jenkins主键id
     * @return Jenkins信息
     */
    JenkinsInfo getJenkinsInfoById(Long jenkinsId);

    /**
     * 根据name查询Jenkins信息
     *
     * @param jenkinsName Jenkins名称
     * @return Jenkins信息
     */
    JenkinsInfo getJenkinsInfoByName(String jenkinsName);

    /**
     * 分页查询Jenkins信息列表
     *
     * @param jenkinsInfoSearchVO 搜索条件
     * @param pageNum             页码
     * @param pageSize            每页数量
     * @return Jenkins信息列表
     */
    PageResult<JenkinsInfo> pageJenkinsInfoList(Integer pageNum, Integer pageSize, JenkinsInfoSearchVO jenkinsInfoSearchVO);

    /**
     * 更新Jenkins信息
     *
     * @param jenkinsId     Jenkins id
     * @param jenkinsInfoVO Jenkins信息
     * @param userId        创建人
     */
    void updateJenkins(Long jenkinsId, JenkinsInfoVO jenkinsInfoVO, Long userId);


    /**
     * 删除Jenkins信息
     *
     * @param jenkinsId Jenkins id
     */
    void deleteJenkins(Long jenkinsId);

    /**
     * 查询所有Jenkins信息
     *
     * @return JenkinsInfo
     */
    List<JenkinsInfo> selectAllJenkinsInfo();
}
