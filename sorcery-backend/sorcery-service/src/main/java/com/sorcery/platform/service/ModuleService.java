package com.sorcery.platform.service;

import com.sorcery.platform.domain.Modules;
import com.sorcery.platform.vo.module.ModuleVO;

import java.util.List;

/**
 * @author jinglv
 * @date 2023/5/15 16:14
 */
public interface ModuleService {
    /**
     * 新增模块
     *
     * @param moduleVO 模块信息
     * @param userId   创建人
     */
    void addModule(ModuleVO moduleVO, Long userId);

    /**
     * 根据id查询模块信息
     *
     * @param moduleId 模块id
     * @return 模块信息
     */
    Modules getModuleById(Long moduleId);

    /**
     * 根据模块名称查询模块信息
     *
     * @param moduleName 模块名称
     * @return 项目信息
     */
    Modules getModuleByName(String moduleName);

    /**
     * 更新模块
     *
     * @param moduleId 模块id
     * @param moduleVO 项目信息
     * @param userId   创建人
     */
    void updateModule(Long moduleId, ModuleVO moduleVO, Long userId);

    /**
     * 删除模块
     *
     * @param moduleId 模块id
     * @param userId   删除人
     */
    void deleteModule(Long moduleId, Long userId);

    /**
     * 获取指定项目下的树形模块
     *
     * @param projectId 项目id
     * @return 模块树形数据
     */
    List<Modules> getModulesTree(Long projectId);
}
