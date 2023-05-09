package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.service.ProjectService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.utils.FileUtils;
import com.sorcery.platform.vo.ProjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author jinglv
 * @date 2022/9/15 10:51
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "项目相关接口", value = "ProjectApi", protocols = "JSON")
public class ProjectApi {

    private final ProjectService projectService;

    private final FileUtils fileUtils;

    private final UserSupport userSupport;

    @ApiOperation(value = "新增项目信息")
    @PostMapping("/project")
    public JsonResponse<String> addProject(@RequestParam("image") MultipartFile image, @RequestBody ProjectVO projectVO) {
        log.info("新增项目,请求参数：{}", JSONUtil.parse(projectVO));
        if (Objects.isNull(projectVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        // 上传图片
        String url = fileUtils.uploads(image);
        projectService.addProject(projectVO, url, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据项目id查询项目信息")
    @GetMapping("/project/{projectId}")
    public JsonResponse<Project> getProjectInfoById(@PathVariable Long projectId) {
        log.info("根据项目id查询项目信息，项目id:{}", projectId);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        Project projectInfo = projectService.getProjectById(projectId);
        return JsonResponse.success(projectInfo);
    }

    @ApiOperation(value = "根据项目名称查询项目信息")
    @GetMapping("/project/name/{projectName}")
    public JsonResponse<Project> getProjectInfoByName(@PathVariable String projectName) {
        log.info("根据项目id查询项目信息，项目名称:{}", projectName);
        // 预留，当前登录用户信息，便于后期做权限管理
        Long userId = userSupport.getCurrentUserId();
        Project projectInfo = projectService.getProjectByName(projectName);
        return JsonResponse.success(projectInfo);
    }

    @ApiOperation(value = "分页查询项目信息列表")
    @GetMapping("/projects")
    public JsonResponse<PageResult<Project>> pageProjectList(@RequestParam Integer no, @RequestParam Integer size) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("userId", userId);
        PageResult<Project> projectPageResult = projectService.pageProjectList(params);
        return JsonResponse.success(projectPageResult);
    }

    @ApiOperation(value = "更新项目信息")
    @PutMapping("/project/{projectId}")
    public JsonResponse<String> updateProject(@RequestParam("image") MultipartFile image, @PathVariable Long projectId, @RequestBody ProjectVO projectVO) {
        log.info("更新项目Id{},更新项目请求参数：{}", projectId, JSONUtil.parse(projectVO));
        if (Objects.isNull(projectVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        // 上传图片
        String url = fileUtils.uploads(image);
        projectService.updateProject(projectId, projectVO, url, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除项目信息")
    @DeleteMapping("/project/{projectId}")
    public JsonResponse<String> deleteProject(@PathVariable Long projectId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除项目Id{}", projectId);
        projectService.deleteProject(projectId);
        return JsonResponse.success();
    }
}
