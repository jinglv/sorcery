package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.sorcery.platform.constant.ApiConstants;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.ApiInfoDAO;
import com.sorcery.platform.domain.ApiInfo;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.ApiInfoService;
import com.sorcery.platform.utils.JsonUtil;
import com.sorcery.platform.vo.apis.ApiInfoVO;
import com.sorcery.platform.vo.apis.ApiRunVO;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

/**
 * @author jinglv
 * @date 2023/5/9 14:51
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiInfoServiceImpl implements ApiInfoService {

    private final ApiInfoDAO apiInfoDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addApiInfo(ApiInfoVO apiInfoVO, Long userId) {
        String apiInfoName = apiInfoVO.getApiName();
        if (StringUtils.isNullOrEmpty(apiInfoName)) {
            throw new ConditionException("接口名称不能为空！");
        }
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setModuleId(apiInfoVO.getModuleId());
        apiInfo.setApiName(apiInfoVO.getApiName());
        apiInfo.setApiPath(apiInfoVO.getApiPath());
        apiInfo.setMethod(apiInfoVO.getMethod());
        apiInfo.setHeader(apiInfoVO.getHeader());
        apiInfo.setParamType(apiInfoVO.getParamType());
        apiInfo.setParamsBody(apiInfoVO.getParamsBody());
        apiInfo.setResponse(apiInfoVO.getResponse());
        apiInfo.setAssertType(apiInfoVO.getAssertType());
        apiInfo.setAssertText(apiInfoVO.getAssertText());
        apiInfo.setUserId(userId);
        // 设置项目未删除逻辑
        apiInfo.setIsDelete(Constants.DEL_FLAG_ZERO);
        apiInfo.setCreateTime(LocalDateTime.now());
        apiInfo.setUpdateTime(LocalDateTime.now());
        Integer result = apiInfoDAO.addApiInfo(apiInfo);
        Assert.isFalse(result != 1, "新增接口信息失败!");
    }

    @Override
    public ApiInfo getApiInfoById(Long apiInfoId) {
        Optional<ApiInfo> apiInfo = Optional.ofNullable(apiInfoDAO.getApiInfoById(apiInfoId));
        return apiInfo.orElse(null);
    }

    @Override
    public ApiInfo getApiInfoByName(String apiInfoName) {
        Optional<ApiInfo> apiInfo = Optional.ofNullable(apiInfoDAO.getApiInfoByName(apiInfoName));
        return apiInfo.orElse(null);
    }

    @Override
    public PageResult<ApiInfo> pageApiInfoList(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no - 1) * size);
        params.put("limit", size);
        Integer total = apiInfoDAO.pageCountApiInfo(params);
        List<ApiInfo> apiInfoList = new ArrayList<>();
        if (total > 0) {
            apiInfoList = apiInfoDAO.pageApiInfoList(params);
        }
        return new PageResult<>(total, apiInfoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApiInfo(Long apiInfoId, ApiInfoVO apiInfoVO, Long userId) {
        // 根据接口信息id查询接口信息
        ApiInfo apiInfo = this.getApiInfoById(apiInfoId);
        if (apiInfo == null) {
            throw new ConditionException("根据接口 Id{" + apiInfoId + "}未查到接口信息！");
        }
        apiInfo.setModuleId(apiInfoVO.getModuleId());
        apiInfo.setApiName(apiInfoVO.getApiName());
        apiInfo.setApiPath(apiInfoVO.getApiPath());
        apiInfo.setMethod(apiInfoVO.getMethod());
        apiInfo.setHeader(apiInfoVO.getHeader());
        apiInfo.setParamType(apiInfoVO.getParamType());
        apiInfo.setParamsBody(apiInfoVO.getParamsBody());
        apiInfo.setResponse(apiInfoVO.getResponse());
        apiInfo.setAssertType(apiInfoVO.getAssertType());
        apiInfo.setAssertText(apiInfoVO.getAssertText());
        apiInfo.setUserId(userId);
        // 设置项目未删除逻辑
        apiInfo.setIsDelete(Constants.DEL_FLAG_ZERO);
        apiInfo.setUpdateTime(LocalDateTime.now());
        Integer result = apiInfoDAO.updateApiInfo(apiInfoId, apiInfo);
        Assert.isFalse(result != 1, "接口信息更新失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiInfo(Long apiInfoId) {
        // 根据Jenkins id查询项目信息
        ApiInfo apiInfo = this.getApiInfoById(apiInfoId);
        if (apiInfo == null) {
            throw new ConditionException("根据接口 Id{" + apiInfoId + "}未查到接口信息！");
        }
        apiInfo.setIsDelete(Constants.DEL_FLAG_ONE);
        apiInfo.setUpdateTime(LocalDateTime.now());
        Integer result = apiInfoDAO.updateApiInfo(apiInfoId, apiInfo);
        Assert.isFalse(result != 1, "删除接口信息失败!");
    }

    @Override
    public String run(ApiRunVO apiRunVO) {
        // 设置模板请求url
        RestAssured.baseURI = apiRunVO.getUrl();
        RequestSpecification requestSpecification = given();
        // 获取请求参数数据
        Map<String, Object> headers = JsonUtil.jsonToMap(apiRunVO.getHeader());
        Map<String, Object> params = null;
        String body = null;
        if (apiRunVO.getParamType().equals(ApiConstants.PARAM_KEY_VALUE)) {
            params = JsonUtil.jsonToMap(apiRunVO.getParamsBody());
        } else if (apiRunVO.getParamType().equals(ApiConstants.PARAM_JSON)) {
            body = apiRunVO.getParamsBody();
        }
        if (MapUtil.isNotEmpty(headers)) {
            requestSpecification.headers(headers);
        }
        if (MapUtil.isNotEmpty(params)) {
            requestSpecification.params(params);
        }
        if (CharSequenceUtil.isNotBlank(body)) {
            requestSpecification.body(body);
        }
        // 将拼装好的接口进行请求
        return requestSpecification.request(apiRunVO.getMethod(), apiRunVO.getApiPath()).then().extract().response().asString();
    }
}
