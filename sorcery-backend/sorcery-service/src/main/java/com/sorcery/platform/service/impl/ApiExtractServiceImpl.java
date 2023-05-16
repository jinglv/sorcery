package com.sorcery.platform.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.constant.Constants;
import com.sorcery.platform.dao.ApiExtractDAO;
import com.sorcery.platform.domain.ApiExtract;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.ApiExtractService;
import com.sorcery.platform.utils.JsonPathUtil;
import com.sorcery.platform.vo.apis.ApiExtractExecuteVO;
import com.sorcery.platform.vo.apis.ApiExtractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author jinglv
 * @date 2023/5/10 10:13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiExtractServiceImpl implements ApiExtractService {

    private final ApiExtractDAO apiExtractDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addApiExtract(ApiExtractVO apiExtractVO, Long userId) {
        String apiExtractName = apiExtractVO.getApiExtractName();
        if (StringUtils.isBlank(apiExtractName)) {
            throw new ConditionException("接口断言名称不能为空！");
        }
        ApiExtract apiExtract = new ApiExtract();
        apiExtract.setApiId(apiExtractVO.getApiId());
        apiExtract.setApiExtractName(apiExtractVO.getApiExtractName());
        apiExtract.setExtract(apiExtractVO.getExtract());
        apiExtract.setValue(apiExtractVO.getValue());
        apiExtract.setUserId(userId);
        // 设置项目未删除逻辑
        apiExtract.setIsDelete(Constants.DEL_FLAG_ZERO);
        apiExtract.setCreateTime(LocalDateTime.now());
        apiExtract.setUpdateTime(LocalDateTime.now());
        Integer result = apiExtractDAO.addApiExtract(apiExtract);
        Assert.isFalse(result != 1, "新增接口断言信息失败!");
    }

    @Override
    public ApiExtract getApiExtractById(Long apiExtractId) {
        Optional<ApiExtract> apiExtractInfo = Optional.ofNullable(apiExtractDAO.getApiExtractById(apiExtractId));
        return apiExtractInfo.orElse(null);
    }

    @Override
    public ApiExtract getApiExtractByName(String apiExtractName) {
        Optional<ApiExtract> apiExtractInfo = Optional.ofNullable(apiExtractDAO.getApiExtractByName(apiExtractName));
        return apiExtractInfo.orElse(null);
    }

    @Override
    public PageResult<ApiExtract> pageApiExtractList(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no - 1) * size);
        params.put("limit", size);
        Integer total = apiExtractDAO.pageCountApiExtract(params);
        List<ApiExtract> apiExtractsList = new ArrayList<>();
        if (total > 0) {
            apiExtractsList = apiExtractDAO.pageApiExtractList(params);
        }
        return new PageResult<>(total, apiExtractsList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApiExtract(Long apiExtractId, ApiExtractVO apiExtractVO, Long userId) {
        // 根据接口信息id查询接口信息
        ApiExtract apiInfoExtract = this.getApiExtractById(apiExtractId);
        if (apiInfoExtract == null) {
            throw new ConditionException("根据接口断言信息Id{" + apiExtractId + "}未查到接口断言信息！");
        }
        apiInfoExtract.setApiId(apiExtractVO.getApiId());
        apiInfoExtract.setApiExtractName(apiExtractVO.getApiExtractName());
        apiInfoExtract.setExtract(apiExtractVO.getExtract());
        apiInfoExtract.setValue(apiExtractVO.getValue());
        apiInfoExtract.setUserId(userId);
        // 设置项目未删除逻辑
        apiInfoExtract.setIsDelete(Constants.DEL_FLAG_ZERO);
        apiInfoExtract.setUpdateTime(LocalDateTime.now());
        Integer result = apiExtractDAO.updateApiExtract(apiExtractId, apiInfoExtract);
        Assert.isFalse(result != 1, "接口断言信息更新失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiExtract(Long apiExtractId) {
        // 根据Jenkins id查询项目信息
        ApiExtract apiExtract = this.getApiExtractById(apiExtractId);
        if (apiExtract == null) {
            throw new ConditionException("根据接口断言信息Id{" + apiExtractId + "}未查到接口断言信息！");
        }
        apiExtract.setIsDelete(Constants.DEL_FLAG_ONE);
        apiExtract.setUpdateTime(LocalDateTime.now());
        Integer result = apiExtractDAO.updateApiExtract(apiExtractId, apiExtract);
        Assert.isFalse(result != 1, "删除接口断言信息失败!");
    }


    @Override
    public Map<String, Object> execute(ApiExtractExecuteVO apiExtractExecuteVO) {
        List<String> extracts = apiExtractExecuteVO.getExtract();
        Map<String, Object> results = new HashMap<>();
        for (String extract : extracts) {
            Object result = JsonPathUtil.extract(apiExtractExecuteVO.getResponseBody(), extract);
            log.info("JsonPath表达式匹配结果：{}", result);
            if (result == null) {
                throw new ConditionException("JsonPath表达式：{ " + extract + " }匹配错误！");
            }
            results.put(extract, result);
        }
        return results;
    }
}
