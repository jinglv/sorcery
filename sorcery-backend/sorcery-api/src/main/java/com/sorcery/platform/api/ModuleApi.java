package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.Modules;
import com.sorcery.platform.service.ModuleService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.vo.module.ModuleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/5/16 14:24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "模块相关接口", value = "ModuleApi", protocols = "JSON")
public class ModuleApi {

    private final ModuleService moduleService;
    private final UserSupport userSupport;

    @ApiOperation(value = "新增模块信息")
    @PostMapping("/module")
    public JsonResponse<String> addModule(@RequestBody ModuleVO moduleVO) {
        log.info("新增模块,请求参数：{}", JSONUtil.parse(moduleVO));
        if (Objects.isNull(moduleVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        moduleService.addModule(moduleVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据模块id查询模块信息")
    @GetMapping("/module/{moduleId}")
    public JsonResponse<Modules> getModuleInfoById(@PathVariable Long moduleId) {
        log.info("根据模块id查询项目信息，模块id:{}", moduleId);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        Modules modules = moduleService.getModuleById(moduleId);
        return JsonResponse.success(modules);
    }

    @ApiOperation(value = "根据项目名称查询项目信息")
    @GetMapping("/module/name/{moduleName}")
    public JsonResponse<Modules> getModuleInfoByName(@PathVariable String moduleName) {
        log.info("根据模块名称查询模块信息，模块名称:{}", moduleName);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        Modules modules = moduleService.getModuleByName(moduleName);
        return JsonResponse.success(modules);
    }

    @ApiOperation(value = "更新模块信息")
    @PutMapping("/module/{moduleId}")
    public JsonResponse<String> updateModule(@PathVariable Long moduleId, @RequestBody ModuleVO moduleVO) {
        log.info("更新模块Id{},更新模块请求参数：{}", moduleId, JSONUtil.parse(moduleVO));
        if (Objects.isNull(moduleVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        moduleService.updateModule(moduleId, moduleVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "获取项目下模块树")
    @GetMapping("/module/tree/{projectId}")
    public JsonResponse<List<Modules>> modulesTree(@PathVariable Long projectId) {
        log.info("获取项目Id：{},下的模块树", projectId);
        List<Modules> modulesTree = moduleService.getModulesTree(projectId);
        return JsonResponse.success(modulesTree);
    }

    @ApiOperation(value = "删除模块信息")
    @DeleteMapping("/module/{moduleId}")
    public JsonResponse<String> deleteProject(@PathVariable Long moduleId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除模块Id{}", moduleId);
        moduleService.deleteModule(moduleId);
        return JsonResponse.success();
    }

}
