package com.sorcery.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.sorcery.platform.domain.ApiInfo;
import com.sorcery.platform.domain.PageResult;
import com.sorcery.platform.vo.apis.ApiInfoVO;
import com.sorcery.platform.vo.apis.ApiRunVO;

/**
 * @author jinglv
 * @date 2023/5/9 13:36
 */
public interface ApiInfoService {
    /**
     * 新增接口信息
     *
     * @param apiInfoVO 接口信息入参
     * @param userId    用户id
     */
    void addApiInfo(ApiInfoVO apiInfoVO, Long userId);

    /**
     * 根据Id查询接口信息
     *
     * @param apiInfoId 接口信息主键id
     * @return 接口信息
     */
    ApiInfo getApiInfoById(Long apiInfoId);

    /**
     * 根据name查询接口信息
     *
     * @param apiInfoName 接口信息名称
     * @return 接口信息
     */
    ApiInfo getApiInfoByName(String apiInfoName);

    /**
     * 分页查询接口信息列表
     *
     * @param params 分页信息
     * @return 接口信息列表
     */
    PageResult<ApiInfo> pageApiInfoList(JSONObject params);

    /**
     * 更新接口信息
     *
     * @param apiInfoId 接口 id
     * @param apiInfoVO 接口信息
     * @param userId    创建人
     */
    void updateApiInfo(Long apiInfoId, ApiInfoVO apiInfoVO, Long userId);


    /**
     * 删除接口信息
     *
     * @param apiInfoId 接口 id
     */
    void deleteApiInfo(Long apiInfoId);

    /**
     * 接口执行
     *
     * @param apiRunVO 执行接口信息
     * @return response
     */
    String run(ApiRunVO apiRunVO);
}
