package com.softteco.toolset.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.math.IntMath;

import java.io.Serializable;
import java.math.RoundingMode;

/**
 *
 * @author serge
 */
public class PageInfoDto implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGE = 0;

    public int pageNumber = 0;
    public int pageSize = DEFAULT_PAGE_SIZE;
    public int totalLines = 0;
    public int totalPage = 0;
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

    @Override
    public String toString() {
        return "PageInfoDto{" + "pageNumber=" + pageNumber + ", pageSize=" + pageSize + '}';
    }

    public void setTotalLines(final Long totalRecords) {
        this.totalLines = Math.toIntExact(totalRecords);
    }

    public void setTotalPage() {
        this.totalPage = IntMath.divide(totalLines, pageSize, RoundingMode.CEILING);
    }
}
