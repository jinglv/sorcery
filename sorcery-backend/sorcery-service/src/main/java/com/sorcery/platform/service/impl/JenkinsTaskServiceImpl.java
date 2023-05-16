package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.JenkinsTaskDAO;
import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.domain.JenkinsTask;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.jenkins.JenkinsClient;
import com.sorcery.platform.service.JenkinsService;
import com.sorcery.platform.service.JenkinsTaskService;
import com.sorcery.platform.vo.jenkins.JenkinsTaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author jinglv
 * @date 2023/4/28 17:51
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JenkinsTaskServiceImpl implements JenkinsTaskService {

    private final JenkinsTaskDAO jenkinsTaskDAO;

    private final JenkinsService jenkinsService;

    private final JenkinsClient jenkinsClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJenkinsTask(JenkinsTaskVO jenkinsTaskVO, Long userId) {
//        String jenkinsTaskName = jenkinsTaskVO.getJenkinsTaskName();
//        if (StringUtils.isNullOrEmpty(jenkinsTaskName)) {
//            throw new ConditionException("Jenkins任务名称不能为空！");
//        }
//        JenkinsTask jenkinsTaskInfo = this.getJenkinsTaskByName(jenkinsTaskName);
//        if (jenkinsTaskInfo != null) {
//            throw new ConditionException("Jenkins任务信息已存在！");
//        }
        Long jenkinsId = jenkinsTaskVO.getJenkinsId();
        JenkinsInfo jenkinsInfoById = jenkinsService.getJenkinsInfoById(jenkinsId);
        if (ObjectUtils.isEmpty(jenkinsInfoById)) {
            throw new ConditionException("根据Jenkins id: { " + jenkinsId + " }未查询到Jenkins基础信息");
        }
        JenkinsTask jenkinsTask = new JenkinsTask();
        jenkinsTask.setJenkinsTaskName(jenkinsTaskVO.getJenkinsTaskName());
        jenkinsTask.setJenkinsId(jenkinsTaskVO.getJenkinsId());
        jenkinsTask.setJenkinsJobName(jenkinsTaskVO.getJenkinsJobName());
        jenkinsTask.setBuildUrl(jenkinsTaskVO.getBuildUrl());
        // 新建任务 status=1
        jenkinsTask.setStatus(Constants.STATUS_ONE);
        jenkinsTask.setCommand(jenkinsTaskVO.getCommand());
        jenkinsTask.setRemark(jenkinsTaskVO.getRemark());
        jenkinsTask.setUserId(userId);
        jenkinsTask.setCreateTime(LocalDateTime.now());
        jenkinsTask.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsTaskDAO.addJenkinsTask(jenkinsTask);
        Assert.isFalse(result != 1, "新增Jenkins任务信息失败!");
    }

    @Override
    public JenkinsTask getJenkinsTaskById(Long jenkinsTaskId) {
        Optional<JenkinsTask> jenkinsTaskInfo = Optional.ofNullable(jenkinsTaskDAO.getJenkinsTaskById(jenkinsTaskId));
        // 如果jenkinsInfo为null，则返回null
        return jenkinsTaskInfo.orElse(null);
    }

    @Override
    public JenkinsTask getJenkinsTaskByName(String jenkinsTaskName) {
        Optional<JenkinsTask> jenkinsTaskInfo = Optional.ofNullable(jenkinsTaskDAO.getJenkinsTaskByName(jenkinsTaskName));
        // 如果jenkinsInfo为null，则返回null
        return jenkinsTaskInfo.orElse(null);
    }

    @Override
    public PageResult<JenkinsTask> pageJenkinsTaskList(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no - 1) * size);
        params.put("limit", size);
        Integer total = jenkinsTaskDAO.pageCountJenkinsTask(params);
        List<JenkinsTask> projectList = new ArrayList<>();
        if (total > 0) {
            projectList = jenkinsTaskDAO.pageJenkinsTaskList(params);
        }
        return new PageResult<>(total, projectList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJenkinsTask(Long jenkinsTaskId, JenkinsTaskVO jenkinsTaskVO, Long userId) {
        // 根据Jenkins任务id查询项目信息
        JenkinsTask jenkinsTaskInfo = this.getJenkinsTaskById(jenkinsTaskId);
        if (jenkinsTaskInfo == null) {
            throw new ConditionException("根据Jenkins任务Id{" + jenkinsTaskId + "}未查到Jenkins任务信息！");
        }
        // 更新项目信息
        jenkinsTaskInfo.setJenkinsTaskName(jenkinsTaskVO.getJenkinsTaskName());
        jenkinsTaskInfo.setJenkinsJobName(jenkinsTaskVO.getJenkinsJobName());
        jenkinsTaskInfo.setBuildUrl(jenkinsTaskVO.getBuildUrl());
        jenkinsTaskInfo.setCommand(jenkinsTaskVO.getCommand());
        jenkinsTaskInfo.setRemark(jenkinsTaskVO.getRemark());
        jenkinsTaskInfo.setUserId(userId);
        // 设置项目未删除逻辑
        jenkinsTaskInfo.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsTaskDAO.updateJenkinsTask(jenkinsTaskId, jenkinsTaskInfo);
        Assert.isFalse(result != 1, "Jenkins任务信息更新失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJenkinsTask(Long jenkinsTaskId) {
        // 根据Jenkins id查询项目信息
        JenkinsTask jenkinsTaskInfo = this.getJenkinsTaskById(jenkinsTaskId);
        if (jenkinsTaskInfo == null) {
            throw new ConditionException("根据Jenkins Id{" + jenkinsTaskId + "}未查到Jenkins任务信息！");
        }
        Integer result = jenkinsTaskDAO.deleteJenkinsTaskById(jenkinsTaskId);
        Assert.isFalse(result != 1, "删除Jenkins任务信息失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runJenkinsTask(JenkinsTaskVO jenkinsTaskVO, Long jenkinsTaskId, Long userId, String token) {
        // 1.获取Jenkins配置基础信息
        Long jenkinsId = jenkinsTaskVO.getJenkinsId();
        JenkinsInfo jenkinsInfo = jenkinsService.getJenkinsInfoById(jenkinsId);
        if (Objects.isNull(jenkinsInfo)) {
            throw new ConditionException("未查到Jenkins任务为：" + jenkinsTaskVO.getJenkinsTaskName() + "，Jenkins基础信息！");
        }
        // 2.获取Jenkins任务信息
        JenkinsTask jenkinsTaskInfo = jenkinsTaskDAO.getJenkinsTaskByName(jenkinsTaskVO.getJenkinsTaskName());
        // 3.Jenkins任务执行
        try {
            jenkinsClient.operateJenkinsJob(jenkinsId, jenkinsTaskId, jenkinsInfo.getJenkinsName(), jenkinsInfo.getJenkinsUrl(), token);
            // 更新任务为执行中 status=2
            jenkinsTaskInfo.setStatus(Constants.STATUS_TWO);
            Integer result = jenkinsTaskDAO.updateJenkinsTask(jenkinsTaskId, jenkinsTaskInfo);
            Assert.isFalse(result != 1, "Jenkins任务信息更新失败!");
        } catch (IOException e) {
            throw new ConditionException("Jenkins执行异常：{}", e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskStatus(Long jenkinsTaskId, String buildUrl, Integer status) {
        JenkinsTask jenkinsTaskInfo = this.getJenkinsTaskById(jenkinsTaskId);
        if (Objects.isNull(jenkinsTaskInfo)) {
            throw new ConditionException("未查询到Jenkins任务信息");
        }
        if (StringUtils.isBlank(buildUrl)) {
            throw new ConditionException("Jenkins任务构建地址不能为空");
        }
        if (ObjectUtils.isEmpty(status)) {
            throw new ConditionException("Jenkins任务状态码不能为空");
        }
        // 如果任务已经完成，则不需要修改
        if (Constants.STATUS_THREE.equals(jenkinsTaskInfo.getStatus())) {
            throw new ConditionException("Jenkins测试任务已完成，无需修改");
        }
        // 已完成的状态status=3时，进行修改
        if (Constants.STATUS_THREE.equals(status)) {
            jenkinsTaskInfo.setBuildUrl(buildUrl);
            jenkinsTaskInfo.setStatus(Constants.STATUS_THREE);
        }
        jenkinsTaskInfo.setUpdateTime(LocalDateTime.now());
        Integer result = jenkinsTaskDAO.updateJenkinsTask(jenkinsTaskId, jenkinsTaskInfo);
        Assert.isFalse(result != 1, "Jenkins任务状态信息更新失败!");
    }

    @Override
    public Map<String, String> getAllureReport(Long jenkinsTaskId) {
        // 根据Jenkins id查询项目信息
        JenkinsTask jenkinsTaskInfo = this.getJenkinsTaskById(jenkinsTaskId);
        if (jenkinsTaskInfo == null) {
            throw new ConditionException("根据Jenkins Id{" + jenkinsTaskId + "}未查到Jenkins任务信息！");
        }
        // 拼接Allure报告地址
        String allureReportUrl = jenkinsTaskInfo.getBuildUrl() + "/allure";
        Map<String, String> result = new HashMap<>();
        result.put("allureReportUrl", allureReportUrl);
        return result;
    }
}
