package io.amoe.cloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Amoe
 * @date 2020/6/1 13:46
 */
@Data
public class PageData<T> implements Serializable {
    private List<T> records;
    private Long total;
    private Long pageSize;
    private Long currentPage;
    private Long pages;
}
