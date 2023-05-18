package com.sorcery.platform.service;

import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.vo.project.ProjectSearchVO;
import com.sorcery.platform.vo.project.ProjectVO;

import java.util.List;

/**
 * @author jinglv
 * @date 2023/3/29 14:59
 */
public interface ProjectService {
    /**
     * 新增项目
     *
     * @param projectVO 项目信息
     * @param userId    创建人
     */
    void addProject(ProjectVO projectVO, Long userId);

    /**
     * 根据id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    Project getProjectById(Long projectId);

    /**
     * 根据项目名称查询项目信息
     *
     * @param projectName 项目名称
     * @return 项目信息
     */
    Project getProjectByName(String projectName);

    /**
     * 分页查询项目列表
     *
     * @param projectSearchVO 项目搜索信息
     * @param pageNum         页码
     * @param pageSize        每页数量
     * @return 项目信息列表
     */
    PageResult<Project> pageProjectList(Integer pageNum, Integer pageSize, ProjectSearchVO projectSearchVO);

    /**
     * 更新项目
     *
     * @param projectId 项目id
     * @param projectVO 项目信息
     * @param userId    创建人
     */
    void updateProject(Long projectId, ProjectVO projectVO, Long userId);

    /**
     * 删除项目
     *
     * @param projectId 项目id
     */
    void deleteProject(Long projectId);

    /**
     * 查询所有项目信息
     *
     * @return Project
     */
    List<Project> selectAllProjectList();
}
