package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.domain.Project;
import com.sorcery.platform.service.ProjectService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.vo.project.ProjectSearchVO;
import com.sorcery.platform.vo.project.ProjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    private final UserSupport userSupport;

    @ApiOperation(value = "新增项目信息")
    @PostMapping("/project")
    public JsonResponse<String> addProject(@RequestBody ProjectVO projectVO) {
        log.info("新增项目,请求参数：{}", JSONUtil.parse(projectVO));
        if (Objects.isNull(projectVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        projectService.addProject(projectVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据项目id查询项目信息")
    @GetMapping("/project/{projectId}")
    public JsonResponse<Project> getProjectInfoById(@PathVariable Long projectId) {
        log.info("根据项目id查询项目信息，项目id:{}", projectId);
        Project projectInfo = projectService.getProjectById(projectId);
        return JsonResponse.success(projectInfo);
    }

    @ApiOperation(value = "根据项目名称查询项目信息")
    @GetMapping("/project/name/{projectName}")
    public JsonResponse<Project> getProjectInfoByName(@PathVariable String projectName) {
        log.info("根据项目id查询项目信息，项目名称:{}", projectName);
        Project projectInfo = projectService.getProjectByName(projectName);
        return JsonResponse.success(projectInfo);
    }

    @ApiOperation(value = "分页查询项目信息列表")
    @PostMapping("/projects")
    public JsonResponse<PageResult<Project>> pageProjectList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody ProjectSearchVO projectSearchVO) {
        log.info("项目条件查询请求参数：{} ", JSONUtil.parse(projectSearchVO));
        if (Objects.isNull(projectSearchVO)) {
            return JsonResponse.fail();
        }
        PageResult<Project> projectPageResult = projectService.pageProjectList(pageNum, pageSize, projectSearchVO);
        return JsonResponse.success(projectPageResult);
    }

    @ApiOperation(value = "更新项目信息")
    @PutMapping("/project/{projectId}")
    public JsonResponse<String> updateProject(@PathVariable Long projectId, @RequestBody ProjectVO projectVO) {
        log.info("更新项目Id{},更新项目请求参数：{}", projectId, JSONUtil.parse(projectVO));
        if (Objects.isNull(projectVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        projectService.updateProject(projectId, projectVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除项目信息")
    @DeleteMapping("/project/{projectId}")
    public JsonResponse<String> deleteProject(@PathVariable Long projectId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除项目Id{}", projectId);
        projectService.deleteProject(projectId, currentUserId);
        return JsonResponse.success();
    }

    @ApiOperation(value = "查询所有项目信息")
    @GetMapping("/projects/all")
    public JsonResponse<List<Project>> selectAllProjectList() {
        Long userId = userSupport.getCurrentUserId();
        List<Project> allProject = projectService.selectAllProjectList();
        return JsonResponse.success(allProject);
    }
}
