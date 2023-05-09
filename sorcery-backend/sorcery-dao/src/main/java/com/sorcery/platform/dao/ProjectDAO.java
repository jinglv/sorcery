package com.sorcery.platform.dao;

import com.sorcery.platform.domain.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    Project getProjectById(Long projectId);

    /**
     * 根据项目名称，查询项目信息
     *
     * @param projectName 项目名称
     * @return Project
     */
    Project getProjectByName(String projectName);

    /**
     * 查询项目信息的总数
     *
     * @param params 参数
     * @return Project
     */
    Integer pageCountProject(Map<String, Object> params);

    /**
     * 查询所有项目信息列表
     *
     * @param params 参数
     * @return Project
     */
    List<Project> pageProjectList(Map<String, Object> params);

    /**
     * 更新项目信息
     *
     * @param projectId 项目id
     * @param project   更新项目信息参数
     * @return int
     */
    Integer updateProject(@Param("projectId") Long projectId, @Param("project") Project project);
}
