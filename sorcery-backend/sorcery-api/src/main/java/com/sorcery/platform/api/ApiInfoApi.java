package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.sorcery.platform.domain.ApiInfo;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.domain.ResponseInfo;
import com.sorcery.platform.service.ApiInfoService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.vo.apis.ApiInfoSearchVO;
import com.sorcery.platform.vo.apis.ApiInfoVO;
import com.sorcery.platform.vo.apis.ApiRunVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/5/9 16:08
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "接口信息相关接口", value = "ApisApi", protocols = "JSON")
public class ApiInfoApi {

    private final ApiInfoService apiInfoService;
    private final UserSupport userSupport;

    @ApiOperation(value = "新增接口信息")
    @PostMapping("/apis")
    public JsonResponse<String> addApiInfo(@RequestBody ApiInfoVO apiInfoVO) {
        log.info("新增接口信息,请求参数：{}", JSONUtil.parse(apiInfoVO));
        if (Objects.isNull(apiInfoVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        apiInfoService.addApiInfo(apiInfoVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据apiInfo id查询接口信息")
    @GetMapping("/apis/{apiInfoId}")
    public JsonResponse<ApiInfo> getApiInfoInfoById(@PathVariable Long apiInfoId) {
        log.info("根据ApiInfo Id查询接口信息，ApiInfo Id:{}", apiInfoId);
        ApiInfo apiInfo = apiInfoService.getApiInfoById(apiInfoId);
        return JsonResponse.success(apiInfo);
    }

    @ApiOperation(value = "根据接口名称查询接口信息")
    @GetMapping("/apis/name/{apiInfoName}")
    public JsonResponse<ApiInfo> getApiInfoByName(@PathVariable String apiInfoName) {
        log.info("根据ApiInfo名称查询Jenkins信息，ApiInfo Name:{}", apiInfoName);
        ApiInfo apiInfo = apiInfoService.getApiInfoByName(apiInfoName);
        return JsonResponse.success(apiInfo);
    }

    @ApiOperation(value = "分页查询模块下接口信息列表")
    @PostMapping("/apis/{moduleId}")
    public JsonResponse<PageResult<ApiInfo>> pageJenkinsInfoList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @PathVariable Long moduleId, @RequestBody ApiInfoSearchVO apiInfoSearchVO) {
        log.info("模块id：{}, 接口条件查询请求参数：{} ", moduleId, JSONUtil.parse(apiInfoSearchVO));
        if (Objects.isNull(apiInfoSearchVO)) {
            return JsonResponse.fail();
        }
        PageResult<ApiInfo> apiInfoPageResult = apiInfoService.pageApiInfoList(pageNum, pageSize, moduleId, apiInfoSearchVO);
        return JsonResponse.success(apiInfoPageResult);
    }

    @ApiOperation(value = "更新接口信息")
    @PutMapping("/apis/{apiInfoId}")
    public JsonResponse<String> updateJenkins(@PathVariable Long apiInfoId, @RequestBody ApiInfoVO apiInfoVO) {
        log.info("更新ApiInfo Id{},更新接口信息请求参数：{}", apiInfoId, JSONUtil.parse(apiInfoVO));
        if (Objects.isNull(apiInfoVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        apiInfoService.updateApiInfo(apiInfoId, apiInfoVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除接口信息")
    @DeleteMapping("/apis/{apiInfoId}")
    public JsonResponse<String> deleteJenkins(@PathVariable Long apiInfoId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除接口信息：{}", apiInfoId);
        apiInfoService.deleteApiInfo(apiInfoId, currentUserId);
        return JsonResponse.success();
    }

    @ApiOperation(value = "执行接口")
    @PostMapping("/apis/run")
    public JsonResponse<ResponseInfo> run(@RequestBody ApiRunVO apiRunVO) {
        log.info("接口请求参数：{}", JSONUtil.parse(apiRunVO));
        ResponseInfo result = apiInfoService.run(apiRunVO);
        return JsonResponse.success(result);
    }
}
