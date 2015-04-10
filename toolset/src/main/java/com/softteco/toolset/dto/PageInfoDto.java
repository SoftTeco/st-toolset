package com.softteco.toolset.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public final int getFirst() {
        return pageNumber * pageSize;
    }

    @JsonIgnore
    public final boolean isPaggable() {
        return pageSize > 0;
    }

    @JsonIgnore
    public final int getLast() {
        return (pageNumber + 1) * pageSize;
    }
}
