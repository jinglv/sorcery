package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.JenkinsDAO;
import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.JenkinsService;
import com.sorcery.platform.vo.jenkins.JenkinsInfoSearchVO;
import com.sorcery.platform.vo.jenkins.JenkinsInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author jinglv
 * @date 2023/4/27 14:01
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JenkinsServiceImpl implements JenkinsService {

    private final JenkinsDAO jenkinsDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJenkinsInfo(JenkinsInfoVO jenkinsInfoVO, Long userId) {
        String jenkinsName = jenkinsInfoVO.getJenkinsName();
        if (StringUtils.isBlank(jenkinsName)) {
            throw new ConditionException("Jenkins名称不能为空！");
        }
        JenkinsInfo jenkinsInfo = this.getJenkinsInfoByName(jenkinsName);
        if (jenkinsInfo != null) {
            throw new ConditionException("Jenkins信息已存在！");
        }
        JenkinsInfo jenkins = new JenkinsInfo();
        jenkins.setJenkinsName(jenkinsInfoVO.getJenkinsName());
        jenkins.setJenkinsUrl(jenkinsInfoVO.getJenkinsUrl());
        jenkins.setJenkinsUsername(jenkinsInfoVO.getJenkinsUsername());
        jenkins.setJenkinsPassword(jenkinsInfoVO.getJenkinsPassword());
        jenkins.setRemark(jenkinsInfoVO.getRemark());
        jenkins.setUserId(userId);
        // 设置项目未删除逻辑
        jenkins.setIsDelete(Constants.DEL_FLAG_ZERO);
        jenkins.setCreateTime(LocalDateTime.now());
        jenkins.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsDAO.addJenkinsInfo(jenkins);
        Assert.isFalse(result != 1, "新增Jenkins信息失败!");
    }

    @Override
    public JenkinsInfo getJenkinsInfoById(Long jenkinsId) {
        Optional<JenkinsInfo> jenkinsInfo = Optional.ofNullable(jenkinsDAO.getJenkinsById(jenkinsId));
        // 如果jenkinsInfo为null，则返回null
        return jenkinsInfo.orElse(null);
    }

    @Override
    public JenkinsInfo getJenkinsInfoByName(String jenkinsName) {
        Optional<JenkinsInfo> jenkinsInfo = Optional.ofNullable(jenkinsDAO.getJenkinsByName(jenkinsName));
        // 如果jenkinsInfo为null，则返回null
        return jenkinsInfo.orElse(null);
    }

    @Override
    public PageResult<JenkinsInfo> pageJenkinsInfoList(Integer pageNum, Integer pageSize, JenkinsInfoSearchVO jenkinsInfoSearchVO) {
        // 根据条件查询总数
        Integer total = jenkinsDAO.pageCountJenkinsInfo(jenkinsInfoSearchVO);
        List<JenkinsInfo> jenkinsInfoList = new ArrayList<>();
        if (total > 0) {
            jenkinsInfoList = jenkinsDAO.pageJenkinsInfoList(jenkinsInfoSearchVO, (pageNum - 1) * pageSize, pageSize);
        }
        return new PageResult<>(total, jenkinsInfoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJenkins(Long jenkinsId, JenkinsInfoVO jenkinsInfoVO, Long userId) {
        // 根据Jenkins id查询项目信息
        JenkinsInfo jenkinsInfo = this.getJenkinsInfoById(jenkinsId);
        if (jenkinsInfo == null) {
            throw new ConditionException("根据Jenkins Id{" + jenkinsId + "}未查到Jenkins信息！");
        }
        // 更新项目信息
        jenkinsInfo.setJenkinsName(jenkinsInfoVO.getJenkinsName());
        jenkinsInfo.setJenkinsUrl(jenkinsInfoVO.getJenkinsUrl());
        jenkinsInfo.setJenkinsUsername(jenkinsInfoVO.getJenkinsUsername());
        jenkinsInfo.setJenkinsPassword(jenkinsInfoVO.getJenkinsPassword());
        jenkinsInfo.setRemark(jenkinsInfoVO.getRemark());
        jenkinsInfo.setUserId(userId);
        // 设置项目未删除逻辑
        jenkinsInfo.setIsDelete(Constants.DEL_FLAG_ZERO);
        jenkinsInfo.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsDAO.updateJenkinsInfo(jenkinsId, jenkinsInfo);
        Assert.isFalse(result != 1, "Jenkins信息更新失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJenkins(Long jenkinsId) {
        // 根据Jenkins id查询项目信息
        JenkinsInfo jenkinsInfo = this.getJenkinsInfoById(jenkinsId);
        if (jenkinsInfo == null) {
            throw new ConditionException("根据Jenkins Id{" + jenkinsId + "}未查到项目信息！");
        }
        jenkinsInfo.setIsDelete(Constants.DEL_FLAG_ONE);
        jenkinsInfo.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsDAO.updateJenkinsInfo(jenkinsId, jenkinsInfo);
        Assert.isFalse(result != 1, "删除Jenkins信息失败!");
    }

    @Override
    public List<JenkinsInfo> selectAllJenkinsInfo() {
        return jenkinsDAO.selectAllJenkinsInfo();
    }
}
