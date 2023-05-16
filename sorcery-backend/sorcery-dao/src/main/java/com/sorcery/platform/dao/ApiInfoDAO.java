package com.sorcery.platform.dao;

import com.sorcery.platform.domain.ApiInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jinglv
 * @date 2023/5/9 11:22
 */
@Mapper
public interface ApiInfoDAO {

    /**
     * 新增接口信息
     *
     * @param apiInfo 接口信息
     * @return int
     */
    Integer addApiInfo(ApiInfo apiInfo);

    /**
     * 根据apiInfoId，查询接口信息
     *
     * @param apiInfoId 接口信息 id
     * @return 接口信息
     */
    ApiInfo getApiInfoById(Long apiInfoId);

    /**
     * 根据Api名称，查询接口信息
     *
     * @param apiInfoName Jenkins名称
     * @return 接口信息
     */
    ApiInfo getApiInfoByName(String apiInfoName);

    /**
     * 查询接口信息的总数
     *
     * @param params 参数
     * @return 接口信息
     */
    Integer pageCountApiInfo(Map<String, Object> params);

    /**
     * 查询所有接口信息列表
     *
     * @param params 参数
     * @return 接口信息
     */
    List<ApiInfo> pageApiInfoList(Map<String, Object> params);

    /**
     * 更新接口信息
     *
     * @param apiInfoId 接口信息 id
     * @param apiInfo   更新Jenkins信息参数
     * @return int
     */
    Integer updateApiInfo(@Param("apiInfoId") Long apiInfoId, @Param("apiInfo") ApiInfo apiInfo);
}
