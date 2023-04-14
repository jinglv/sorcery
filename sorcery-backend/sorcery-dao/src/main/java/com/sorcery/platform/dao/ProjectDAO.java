package com.sorcery.platform.dao;

import com.sorcery.platform.domain.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jinglv
 * @date 2022/9/15 10:26
 */
@Mapper
public interface ProjectDAO {

    /**
     * 新增项目
     *
     * @param project 项目信息
     * @return int
     */
    Integer addProject(Project project);

    /**
     * 根据项目Id，查询项目信息
     *
     * @param projectId 项目Id
     * @return Project
     */
    Project getProjectById(Integer projectId);

    /**
     * 根据项目名称，查询项目信息
     *
     * @param projectName 项目名称
     * @return Project
     */
    Project getProjectByName(String projectName);
}
