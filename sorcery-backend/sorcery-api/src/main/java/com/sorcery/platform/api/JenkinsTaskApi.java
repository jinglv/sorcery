package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.JenkinsTask;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.service.JenkinsTaskService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.utils.TokenUtil;
import com.sorcery.platform.vo.JenkinsTaskStatusVO;
import com.sorcery.platform.vo.JenkinsTaskVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/5/5 15:02
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jenkins")
@Api(tags = "Jenkins任务相关接口", value = "JenkinsTaskApi", protocols = "JSON")
public class JenkinsTaskApi {

    private final JenkinsTaskService jenkinsTaskService;
    private final UserSupport userSupport;

    @ApiOperation(value = "新增Jenkins任务信息")
    @PostMapping("/task")
    public JsonResponse<String> addJenkinsTask(@RequestBody JenkinsTaskVO jenkinsTaskVO) {
        log.info("新增Jenkins任务信息,请求参数：{}", JSONUtil.parse(jenkinsTaskVO));
        if (Objects.isNull(jenkinsTaskVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        jenkinsTaskService.addJenkinsTask(jenkinsTaskVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据jenkins任务id查询jenkins任务信息")
    @GetMapping("/task/{jenkinsTaskId}")
    public JsonResponse<JenkinsTask> getJenkinsInfoById(@PathVariable Long jenkinsTaskId) {
        log.info("根据Jenkins任务Id查询Jenkins任务信息，Jenkins任务Id:{}", jenkinsTaskId);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        JenkinsTask jenkinsTask = jenkinsTaskService.getJenkinsTaskById(jenkinsTaskId);
        return JsonResponse.success(jenkinsTask);
    }

    @ApiOperation(value = "根据Jenkins任务名称查询Jenkins任务信息")
    @GetMapping("/task/name/{jenkinsTaskName}")
    public JsonResponse<JenkinsTask> getProjectInfoByName(@PathVariable String jenkinsTaskName) {
        log.info("根据Jenkins任务名称查询Jenkins任务信息，Jenkins任务名称:{}", jenkinsTaskName);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        JenkinsTask jenkinsTask = jenkinsTaskService.getJenkinsTaskByName(jenkinsTaskName);
        return JsonResponse.success(jenkinsTask);
    }

    @ApiOperation(value = "分页查询Jenkins任务信息列表")
    @GetMapping("/tasks")
    public JsonResponse<PageResult<JenkinsTask>> pageJenkinsTaskInfoList(@RequestParam Integer no, @RequestParam Integer size) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("userId", userId);
        PageResult<JenkinsTask> projectPageResult = jenkinsTaskService.pageJenkinsTaskList(params);
        return JsonResponse.success(projectPageResult);
    }

    @ApiOperation(value = "更新Jenkins任务信息")
    @PutMapping("/task/{jenkinsTaskId}")
    public JsonResponse<String> updateJenkinsTask(@PathVariable Long jenkinsTaskId, @RequestBody JenkinsTaskVO jenkinsTaskVO) {
        log.info("更新Jenkins任务Id{},更新Jenkins任务请求参数：{}", jenkinsTaskId, JSONUtil.parse(jenkinsTaskVO));
        if (Objects.isNull(jenkinsTaskVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        jenkinsTaskService.updateJenkinsTask(jenkinsTaskId, jenkinsTaskVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除Jenkins任务信息")
    @DeleteMapping("/task/{jenkinsTaskId}")
    public JsonResponse<String> deleteProject(@PathVariable Long jenkinsTaskId) {
        log.info("删除Jenkins任务Id{}", jenkinsTaskId);
        jenkinsTaskService.deleteJenkinsTask(jenkinsTaskId);
        return JsonResponse.success();
    }

    @ApiOperation(value = "执行Jenkins Job")
    @PostMapping("/task/{jenkinsTaskId}/run")
    public JsonResponse<String> runJenkinsTask(@PathVariable Long jenkinsTaskId, @RequestBody JenkinsTaskVO jenkinsTaskVO) {
        log.info("执行Jenkins任务信息,Jenkins任务Id:{}, 请求参数：{}", jenkinsTaskId, JSONUtil.parse(jenkinsTaskVO));
        Long userId = userSupport.getCurrentUserId();
        String token;
        try {
            token = TokenUtil.generateToken(userId);
            log.info("token信息：{}", token);
        } catch (Exception e) {
            log.error("获取token信息失败：{}", e.getMessage());
            return JsonResponse.fail("获取当前登录token失败！");
        }
        jenkinsTaskService.runJenkinsTask(jenkinsTaskVO, jenkinsTaskId, userId, token);
        return JsonResponse.success();
    }

    @ApiOperation(value = "修改测试任务状态")
    @PutMapping("/task/status")
    public JsonResponse<String> updateTaskStatus(@RequestBody JenkinsTaskStatusVO jenkinsTaskStatusVO) {
        log.info("修改测试任务状态,请求参数：{} ", JSONUtil.parse(jenkinsTaskStatusVO));
        Long jenkinsTaskId = jenkinsTaskStatusVO.getJenkinsTaskId();
        String buildUrl = jenkinsTaskStatusVO.getBuildUrl();
        Integer status = jenkinsTaskStatusVO.getStatus();
        jenkinsTaskService.updateTaskStatus(jenkinsTaskId, buildUrl, status);
        return JsonResponse.success();
    }

    @ApiOperation(value = "获取Allure报告")
    @GetMapping("/allure/report/{jenkinsTaskId}")
    public JsonResponse<Map<String, String>> getAllureReport(@PathVariable Long jenkinsTaskId) {
        log.info("获取Jenkins Job执行完成Allure报告，Jenkins任务id为：{}", jenkinsTaskId);
        Map<String, String> allureReport = jenkinsTaskService.getAllureReport(jenkinsTaskId);
        return JsonResponse.success(allureReport);
    }
}
