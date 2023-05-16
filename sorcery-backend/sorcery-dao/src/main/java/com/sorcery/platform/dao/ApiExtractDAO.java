package com.sorcery.platform.dao;

import com.sorcery.platform.domain.ApiExtract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jinglv
 * @date 2023/5/9 11:32
 */
@Mapper
public interface ApiExtractDAO {
    /**
     * 新增接口断言信息
     *
     * @param apiExtract 接口断言信息
     * @return int
     */
    Integer addApiExtract(ApiExtract apiExtract);

    /**
     * 根据apiExtractId，查询接口断言信息
     *
     * @param apiExtractId 接口断言信息 id
     * @return 接口信息
     */
    ApiExtract getApiExtractById(Long apiExtractId);

    /**
     * 根据接口断言名称，查询接口断言信息
     *
     * @param apiInfoExtractName 接口断言名称
     * @return 接口信息
     */
    ApiExtract getApiExtractByName(String apiInfoExtractName);

    /**
     * 查询接口断言信息的总数
     *
     * @param params 参数
     * @return 接口信息
     */
    Integer pageCountApiExtract(Map<String, Object> params);

    /**
     * 查询所有接口断言信息列表
     *
     * @param params 参数
     * @return 接口信息
     */
    List<ApiExtract> pageApiExtractList(Map<String, Object> params);

    /**
     * 更新接口断言信息
     *
     * @param apiInfoExtractId 接口断言信息 id
     * @param apiExtract       接口断言更新信息
     * @return int
     */
    Integer updateApiExtract(@Param("apiInfoExtractId") Long apiInfoExtractId, @Param("apiInfoExtract") ApiExtract apiExtract);
}
