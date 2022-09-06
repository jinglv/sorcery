package com.sorcery.platform.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author jinglv
 * @date 2022/8/31 10:31
 */
@Mapper
public interface DemoDAO {

    /**
     * 查询示例
     *
     * @param id
     * @return
     */
    Long query(Long id);
}
