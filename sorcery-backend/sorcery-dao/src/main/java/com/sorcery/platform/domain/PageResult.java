package com.sorcery.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 查询列表分页
 *
 * @author jinglv
 * @date 2023/4/25 11:13
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 分页的总数
     */
    private Integer total;

    /**
     * 分页的列表
     */
    private List<T> list;

}
