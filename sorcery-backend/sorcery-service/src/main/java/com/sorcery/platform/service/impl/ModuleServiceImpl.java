package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.ModuleDAO;
import com.sorcery.platform.domain.Modules;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.hepler.ModuleTreeHelper;
import com.sorcery.platform.service.ModuleService;
import com.sorcery.platform.service.ProjectService;
import com.sorcery.platform.vo.module.ModuleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author jinglv
 * @date 2023/5/15 18:09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleDAO moduleDAO;
    private final ProjectService projectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addModule(ModuleVO moduleVO, Long userId) {
        String moduleName = moduleVO.getModuleName();
        if (StringUtils.isBlank(moduleName)) {
            throw new ConditionException("模块名称不能为空！");
        }
        Modules moduleInfo = this.getModuleByName(moduleName);
        if (moduleInfo != null) {
            throw new ConditionException("模块信息已存在！");
        }
        Project projectInfo = projectService.getProjectById(moduleVO.getProjectId());
        if (projectInfo == null) {
            throw new ConditionException("项目信息不存在！");
        }
        Modules modules = new Modules();
        modules.setLabel(moduleVO.getModuleName());
        modules.setModuleParentId(moduleVO.getModuleParentId());
        modules.setProjectId(moduleVO.getProjectId());
        modules.setUserId(userId);
        // 设置模块未删除逻辑
        modules.setIsDelete(Constants.DEL_FLAG_ZERO);
        modules.setCreateTime(LocalDateTime.now());
        modules.setUpdateTime(LocalDateTime.now());
        Integer result = moduleDAO.addModule(modules);
        Assert.isFalse(result != 1, "新增模块失败!");
    }

    @Override
    public Modules getModuleById(Long moduleId) {
        Optional<Modules> modules = Optional.ofNullable(moduleDAO.getModuleById(moduleId));
        // 如果modules为null，则返回null
        return modules.orElse(null);
    }

    @Override
    public Modules getModuleByName(String moduleName) {
        Optional<Modules> modules = Optional.ofNullable(moduleDAO.getModuleByName(moduleName));
        // 如果modules为null，则返回null
        return modules.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModule(Long moduleId, ModuleVO moduleVO, Long userId) {
        // 根据模块id查询模块信息
        Modules moduleInfo = this.getModuleById(moduleId);
        if (moduleInfo == null) {
            throw new ConditionException("根据模块Id{" + moduleId + "}未查到模块信息！");
        }
        // 更新模块信息
        moduleInfo.setLabel(moduleVO.getModuleName());
        moduleInfo.setModuleParentId(moduleVO.getModuleParentId());
        moduleInfo.setProjectId(moduleVO.getProjectId());
        moduleInfo.setUserId(userId);
        // 设置项目未删除逻辑
        moduleInfo.setIsDelete(Constants.DEL_FLAG_ZERO);
        moduleInfo.setUpdateTime(LocalDateTime.now());
        Integer result = moduleDAO.updateModule(moduleId, moduleInfo);
        Assert.isFalse(result != 1, "更新模块失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModule(Long moduleId) {
        // 根据模块id查询项目信息
        Modules moduleInfo = this.getModuleById(moduleId);
        if (moduleInfo == null) {
            throw new ConditionException("根据模块Id{" + moduleId + "}未查到模块信息！");
        }
        moduleInfo.setIsDelete(Constants.DEL_FLAG_ONE);
        moduleInfo.setUpdateTime(LocalDateTime.now());
        Integer result = moduleDAO.updateModule(moduleId, moduleInfo);
        Assert.isFalse(result != 1, "删除模块失败!");
    }

    @Override
    public List<Modules> getModulesTree(Long projectId) {
        // 获取项目下的所有模块
        List<Modules> modulesList = moduleDAO.selectModulesByProject(projectId);
        // 判断是否为空
        if (CollectionUtils.isEmpty(modulesList)) {
            return null;
        } else {
            // 构建树形模块
            return ModuleTreeHelper.buildModuleTree(modulesList);
        }
    }
}
