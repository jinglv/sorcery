package com.sorcery.platform.service;

import com.sorcery.platform.domain.Project;
import com.sorcery.platform.vo.ProjectVO;

/**
 * @author jinglv
 * @date 2023/3/29 14:59
 */
public interface ProjectService {
    /**
     * 新增项目
     *
     * @param projectVO 项目信息
     */
    void addProject(ProjectVO projectVO);

    /**
     * 根据id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    Project getProjectById(Integer projectId);

    /**
     * 根据项目名称查询项目信息
     *
     * @param projectName 项目名称
     * @return 项目信息
     */
    Project getProjectByName(String projectName);

}
