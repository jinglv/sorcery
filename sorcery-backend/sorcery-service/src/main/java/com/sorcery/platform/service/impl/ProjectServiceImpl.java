package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.ProjectDAO;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.ProjectService;
import com.sorcery.platform.vo.project.ProjectSearchVO;
import com.sorcery.platform.vo.project.ProjectVO;
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
 * @date 2022/9/15 10:25
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDAO projectDAO;

    /**
     * 新增项目
     *
     * @param projectVO 项目信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProject(ProjectVO projectVO, Long userId) {
        String projectName = projectVO.getProjectName();
        if (StringUtils.isBlank(projectName)) {
            throw new ConditionException("项目名称不能为空！");
        }
        Project projectInfo = this.getProjectByName(projectName);
        if (projectInfo != null) {
            throw new ConditionException("项目信息已存在！");
        }
        Project project = new Project();
        // 如果项目图片为空，设置默认图片
        if (StringUtils.isBlank(projectVO.getImage())) {
            project.setImage("static/images/4d928a0132b4434c922326256664a635.png");
        } else {
            project.setImage(projectVO.getImage());
        }
        project.setProjectName(projectVO.getProjectName());
        project.setDescription(projectVO.getDescription());
        project.setUserId(userId);
        // 设置项目未删除逻辑
        project.setIsDelete(Constants.DEL_FLAG_ZERO);
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        Integer result = projectDAO.addProject(project);
        Assert.isFalse(result != 1, "新增项目失败!");
    }

    /**
     * 根据id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    @Override
    public Project getProjectById(Long projectId) {
        Optional<Project> project = Optional.ofNullable(projectDAO.getProjectById(projectId));
        // 如果project为null，则返回null
        return project.orElse(null);
    }

    /**
     * 根据项目名称查询项目信息
     *
     * @param projectName 项目名称
     * @return 项目信息
     */
    @Override
    public Project getProjectByName(String projectName) {
        Optional<Project> project = Optional.ofNullable(projectDAO.getProjectByName(projectName));
        // 如果project为null，则返回null
        return project.orElse(null);
    }

    /**
     * 分页查询项目列表
     *
     * @param projectSearchVO 查询条件信息
     * @return 项目信息列表
     */
    @Override
    public PageResult<Project> pageProjectList(Integer pageNum, Integer pageSize, ProjectSearchVO projectSearchVO) {
        // 根据条件查询总数
        Integer total = projectDAO.pageCountProject(projectSearchVO);
        List<Project> projectList = new ArrayList<>();
        if (total > 0) {
            projectList = projectDAO.pageProjectList(projectSearchVO, (pageNum - 1) * pageSize, pageSize);
        }
        return new PageResult<>(total, projectList);
    }

    /**
     * 更新项目
     *
     * @param projectId 项目id
     * @param projectVO 项目信息
     * @param userId    创建人
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProject(Long projectId, ProjectVO projectVO, Long userId) {
        // 根据项目id查询项目信息
        Project projectInfo = this.getProjectById(projectId);
        if (projectInfo == null) {
            throw new ConditionException("根据项目Id{" + projectId + "}未查到项目信息！");
        }
        // 更新项目信息
        projectInfo.setProjectName(projectVO.getProjectName());
        projectInfo.setDescription(projectVO.getDescription());
        projectInfo.setImage(projectVO.getImage());
        projectInfo.setUserId(userId);
        // 设置项目未删除逻辑
        projectInfo.setIsDelete(Constants.DEL_FLAG_ZERO);
        projectInfo.setUpdateTime(LocalDateTime.now());
        Integer result = projectDAO.updateProject(projectId, projectInfo);
        Assert.isFalse(result != 1, "更新项目失败!");
    }

    /**
     * 删除项目
     *
     * @param projectId 项目id
     * @param userId    删除人
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProject(Long projectId, Long userId) {
        // 根据项目id查询项目信息
        Project projectInfo = this.getProjectById(projectId);
        if (projectInfo == null) {
            throw new ConditionException("根据项目Id{" + projectId + "}未查到项目信息！");
        }
        projectInfo.setIsDelete(Constants.DEL_FLAG_ONE);
        projectInfo.setUpdateTime(LocalDateTime.now());
        projectInfo.setUserId(userId);
        Integer result = projectDAO.updateProject(projectId, projectInfo);
        Assert.isFalse(result != 1, "删除项目失败!");
    }

    /**
     * 查询所有项目信息
     *
     * @return Projects {@link Project}
     */
    @Override
    public List<Project> selectAllProjectList() {
        return projectDAO.selectAllProjectList();
    }
}
