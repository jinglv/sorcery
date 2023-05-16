package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.service.JenkinsService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.vo.jenkins.JenkinsInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/4/27 15:17
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Jenkins相关接口", value = "JenkinsApi", protocols = "JSON")
public class JenkinsApi {

    private final JenkinsService jenkinsService;
    private final UserSupport userSupport;

    @ApiOperation(value = "新增Jenkins信息")
    @PostMapping("/jenkins")
    public JsonResponse<String> addJenkins(@RequestBody JenkinsInfoVO jenkinsInfoVO) {
        log.info("新增Jenkins信息,请求参数：{}", JSONUtil.parse(jenkinsInfoVO));
        if (Objects.isNull(jenkinsInfoVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        jenkinsService.addJenkinsInfo(jenkinsInfoVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据jenkins id查询jenkins信息")
    @GetMapping("/jenkins/{jenkinsId}")
    public JsonResponse<JenkinsInfo> getJenkinsInfoById(@PathVariable Long jenkinsId) {
        log.info("根据Jenkins Id查询Jenkins信息，Jenkins Id:{}", jenkinsId);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        JenkinsInfo jenkinsInfo = jenkinsService.getJenkinsInfoById(jenkinsId);
        return JsonResponse.success(jenkinsInfo);
    }

    @ApiOperation(value = "根据Jenkins名称查询Jenkins信息")
    @GetMapping("/jenkins/name/{jenkinsName}")
    public JsonResponse<JenkinsInfo> getProjectInfoByName(@PathVariable String jenkinsName) {
        log.info("根据Jenkins名称查询Jenkins信息，Jenkins Name:{}", jenkinsName);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        JenkinsInfo jenkinsInfo = jenkinsService.getJenkinsInfoByName(jenkinsName);
        return JsonResponse.success(jenkinsInfo);
    }

    @ApiOperation(value = "分页查询Jenkins信息列表")
    @GetMapping("/jenkins")
    public JsonResponse<PageResult<JenkinsInfo>> pageJenkinsInfoList(@RequestParam Integer no, @RequestParam Integer size) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("userId", userId);
        PageResult<JenkinsInfo> projectPageResult = jenkinsService.pageJenkinsInfoList(params);
        return JsonResponse.success(projectPageResult);
    }

    @ApiOperation(value = "更新Jenkins信息")
    @PutMapping("/jenkins/{jenkinsId}")
    public JsonResponse<String> updateJenkins(@PathVariable Long jenkinsId, @RequestBody JenkinsInfoVO jenkinsInfoVO) {
        log.info("更新Jenkins Id{},更新Jenkins请求参数：{}", jenkinsId, JSONUtil.parse(jenkinsInfoVO));
        if (Objects.isNull(jenkinsInfoVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        jenkinsService.updateJenkins(jenkinsId, jenkinsInfoVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除Jenkins信息")
    @DeleteMapping("/jenkins/{jenkinsId}")
    public JsonResponse<String> deleteJenkins(@PathVariable Long jenkinsId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除JenkinsId{}", jenkinsId);
        jenkinsService.deleteJenkins(jenkinsId);
        return JsonResponse.success();
    }
}
