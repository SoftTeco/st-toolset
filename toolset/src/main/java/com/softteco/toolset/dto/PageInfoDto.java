package com.softteco.toolset.dto;

import java.io.Serializable;

/**
 *
 * @author serge
 */
public class PageInfoDto implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGE = 0;

    public int pageNumber = 0;
    public int pageSize = DEFAULT_PAGE_SIZE;
    public SortInfoDto[] sort;

    public PageInfoDto() {
    }

    public PageInfoDto(final int size) {
        this.pageSize = size;
    }

    public final int getFirst() {
        return pageNumber * pageSize;
    }

    public final boolean isPaggable() {
        return pageSize > 0;
    }

    public final int getLast() {
        return (pageNumber + 1) * pageSize;
    }
}
