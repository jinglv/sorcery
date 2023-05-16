package com.sorcery.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.ApiExtract;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.vo.apis.ApiExtractExecuteVO;
import com.sorcery.platform.vo.apis.ApiExtractVO;

import java.util.Map;

/**
 * @author jinglv
 * @date 2023/5/9 13:44
 */
public interface ApiExtractService {
    /**
     * 新增接口断言信息
     *
     * @param apiExtractVO 接口断言信息入参
     * @param userId       用户id
     */
    void addApiExtract(ApiExtractVO apiExtractVO, Long userId);

    /**
     * 根据Id查询接口断言信息
     *
     * @param apiExtractId 接口断言信息主键id
     * @return 接口信息
     */
    ApiExtract getApiExtractById(Long apiExtractId);

    /**
     * 根据name查询接口信息
     *
     * @param apiExtractName 接口断言信息名称
     * @return 接口信息
     */
    ApiExtract getApiExtractByName(String apiExtractName);

    /**
     * 分页查询接口信息列表
     *
     * @param params 分页信息
     * @return 接口信息列表
     */
    PageResult<ApiExtract> pageApiExtractList(JSONObject params);

    /**
     * 更新接口断言信息
     *
     * @param apiExtractId 接口断言信息主键id
     * @param apiExtractVO 接口信息
     * @param userId       创建人
     */
    void updateApiExtract(Long apiExtractId, ApiExtractVO apiExtractVO, Long userId);

    /**
     * 删除接口信息
     *
     * @param apiExtractId 接口断言信息主键id
     */
    void deleteApiExtract(Long apiExtractId);

    /**
     * 断言表达式提取结果
     *
     * @param apiExtractExecuteVO 断言提取对象
     * @return 提取结果
     */
    Map<String, Object> execute(ApiExtractExecuteVO apiExtractExecuteVO);

}
