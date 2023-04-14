package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.mysql.cj.util.StringUtils;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.ProjectDAO;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.ProjectService;
import com.sorcery.platform.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2022/9/15 10:25
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO;

    /**
     * 新增项目
     *
     * @param projectVO 项目信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProject(ProjectVO projectVO) {
        log.info("项目新增信息：{}", JSONUtil.parse(projectVO));
        String projectName = projectVO.getProjectName();
        if (StringUtils.isNullOrEmpty(projectName)) {
            throw new ConditionException("项目名称不能为空！");
        }
        Project projectInfo = this.getProjectByName(projectName);
        if (projectInfo != null) {
            throw new ConditionException("项目信息已存在！");
        }
        // 如果项目图片为空，设置默认图片
        if (Objects.isNull(projectVO.getImage()) || Objects.equals(projectVO.getImage(), "")) {
            projectVO.setImage("/images/40df0bcd6f5d48ba8c832fee71bb2f2d.jpeg");
        }
        Project project = new Project();
        project.setProjectName(projectVO.getProjectName());
        project.setDescription(projectVO.getDescription());
        project.setImage(projectVO.getImage());
        // 设置项目未删除逻辑
        project.setIsDelete(Constants.DEL_FLAG_ONE);
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        Integer result = projectDAO.addProject(project);
        Assert.isFalse(result != 1, "新增项目失败!");
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return null;
    }

    @Override
    public Project getProjectByName(String projectName) {
        return projectDAO.getProjectByName(projectName);
    }
}
