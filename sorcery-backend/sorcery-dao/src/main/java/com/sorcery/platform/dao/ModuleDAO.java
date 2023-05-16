package com.sorcery.platform.dao;

import com.sorcery.platform.domain.Modules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jinglv
 * @date 2023/5/15 16:23
 */
@Mapper
public interface ModuleDAO {
    /**
     * 新增模块
     *
     * @param modules 模块信息
     * @return int
     */
    Integer addModule(Modules modules);

    /**
     * 根据模块Id，查询模块信息
     *
     * @param moduleId 模块Id
     * @return Modules
     */
    Modules getModuleById(Long moduleId);

    /**
     * 根据模块名称，查询模块信息
     *
     * @param moduleName 模块名称
     * @return Modules
     */
    Modules getModuleByName(String moduleName);

    /**
     * 更新模块信息
     *
     * @param moduleId 模块id
     * @param modules  更新模块信息参数
     * @return int
     */
    Integer updateModule(@Param("moduleId") Long moduleId, @Param("module") Modules modules);

    /**
     * 根据项目id 查询项目下所有的模块
     *
     * @param projectId 项目id
     * @return modules
     */
    List<Modules> selectModulesByProject(Long projectId);

}
