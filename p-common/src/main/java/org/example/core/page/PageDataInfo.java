package org.example.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private int totalPages;

    // 总元素数
    private int totalElements;

    /** 列表数据 */
    private List<?> content;

    // 是否已经到达了末尾
    private Boolean last;

    // 是否是第一次查询
    private Boolean first;

    //本次查询到的元素数量
    private int numberOfElements;

    // 是否为空的
    private Boolean empty;


}
