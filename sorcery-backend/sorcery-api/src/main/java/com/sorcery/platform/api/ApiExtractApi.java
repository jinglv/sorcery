package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.ApiExtract;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.service.ApiExtractService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.support.UserToken;
import com.sorcery.platform.vo.apis.ApiExtractExecuteVO;
import com.sorcery.platform.vo.apis.ApiExtractVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/5/10 11:23
 */
@Slf4j
@UserToken
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "接口断言信息相关接口", value = "ApiExtractApi", protocols = "JSON")
public class ApiExtractApi {

    private final ApiExtractService apiExtractService;
    private final UserSupport userSupport;

    @ApiOperation(value = "新增接口断言信息")
    @PostMapping("/extract")
    public JsonResponse<String> addApiExtract(@RequestBody ApiExtractVO apiExtractVO) {
        log.info("新增接口断言信息,请求参数：{}", JSONUtil.parse(apiExtractVO));
        if (Objects.isNull(apiExtractVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        apiExtractService.addApiExtract(apiExtractVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "根据接口断言id查询接口信息")
    @GetMapping("/extract/{apiExtractId}")
    public JsonResponse<ApiExtract> getApiExtractInfoById(@PathVariable Long apiExtractId) {
        log.info("根据接口断言Id查询接口信息，ApiExtract Id:{}", apiExtractId);
        ApiExtract apiExtract = apiExtractService.getApiExtractById(apiExtractId);
        return JsonResponse.success(apiExtract);
    }

    @ApiOperation(value = "根据接口断言名称查询接口信息")
    @GetMapping("/extract/name/{apiExtractName}")
    public JsonResponse<ApiExtract> getApiExtractByName(@PathVariable String apiExtractName) {
        log.info("根据ApiExtract名称查询Jenkins信息，ApiExtract Name:{}", apiExtractName);
        ApiExtract apiExtract = apiExtractService.getApiExtractByName(apiExtractName);
        return JsonResponse.success(apiExtract);
    }

    @ApiOperation(value = "分页查询接口断言信息列表")
    @GetMapping("/extracts")
    public JsonResponse<PageResult<ApiExtract>> pageApiExtractList(@RequestParam Integer no, @RequestParam Integer size) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("userId", userId);
        PageResult<ApiExtract> projectPageResult = apiExtractService.pageApiExtractList(params);
        return JsonResponse.success(projectPageResult);
    }

    @ApiOperation(value = "更新接口断言信息")
    @PutMapping("/extracts/{apiExtractId}")
    public JsonResponse<String> updateJenkins(@PathVariable Long apiExtractId, @RequestBody ApiExtractVO apiExtractVO) {
        log.info("更新ApiExtract Id{},更新接口信息请求参数：{}", apiExtractId, JSONUtil.parse(apiExtractVO));
        if (Objects.isNull(apiExtractVO)) {
            return JsonResponse.fail("请求参数不能为空");
        }
        apiExtractService.updateApiExtract(apiExtractId, apiExtractVO, userSupport.getCurrentUserId());
        return JsonResponse.success();
    }

    @ApiOperation(value = "删除接口断言信息")
    @DeleteMapping("/extracts/{apiExtractId}")
    public JsonResponse<String> deleteJenkins(@PathVariable Long apiExtractId) {
        // 预留
        Long currentUserId = userSupport.getCurrentUserId();
        log.info("删除接口断言信息：{}", apiExtractId);
        apiExtractService.deleteApiExtract(apiExtractId, currentUserId);
        return JsonResponse.success();
    }

    @ApiOperation(value = "断言表达式提取结果")
    @PostMapping("/extract/execute")
    public JsonResponse<Map<String, Object>> execute(@RequestBody ApiExtractExecuteVO apiExtractExecuteVO) {
        log.info("断言表达式提取结果,请求参数：{}", JSONUtil.parse(apiExtractExecuteVO));
        if (Objects.isNull(apiExtractExecuteVO)) {
            return JsonResponse.fail();
        }
        userSupport.getCurrentUserId();
        Map<String, Object> result = apiExtractService.execute(apiExtractExecuteVO);
        return JsonResponse.success(result);
    }
}
