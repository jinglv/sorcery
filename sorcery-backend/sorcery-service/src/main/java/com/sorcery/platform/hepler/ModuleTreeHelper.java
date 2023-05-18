package com.sorcery.platform.hepler;

import com.sorcery.platform.domain.Modules;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造模块树的帮助类
 *
 * @author jinglv
 * @date 2023/5/16 11:11
 */
public class ModuleTreeHelper {

    /**
     * 构建模块树
     *
     * @param modulesList 模块信息列表
     * @return 模块树
     */
    public static List<Modules> buildModuleTree(List<Modules> modulesList) {
        // 新建模块树列表，用于存放创建好的模块树
        List<Modules> moduleTree = new ArrayList<>();
        for (Modules modules : modulesList) {
            // 判断当前模块是否为一级模块
            if (modules.getModuleParentId() == 0) {
                moduleTree.add(findChildren(modules, modulesList));
            }
        }
        return moduleTree;
    }

    /**
     * 递归查询子模块信息
     *
     * @param modules     当前模块
     * @param modulesList 模块列表
     * @return 模块信息
     */
    private static Modules findChildren(Modules modules, List<Modules> modulesList) {
        // 设置当前模块子模块列表为空
        modules.setChildren(new ArrayList<>());
        // 遍历所有模块列表
        for (Modules module : modulesList) {
            // 判断当前模块的id和模块列表中哪些模块的parentId相同
            if (modules.getId().equals(module.getModuleParentId())) {
                if (modules.getChildren() == null) {
                    modules.setChildren(new ArrayList<>());
                }
                // 设置单管菜单的子菜单列表，并进行递归查询
                modules.getChildren().add(findChildren(module, modulesList));
            }
        }
        return modules;
    }
}
