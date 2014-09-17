package com.softteco.toolset.dto;

import java.io.Serializable;

/**
 *
 * @author serge
 */
public class PageInfoDto implements Serializable {

    public int pageNumber = 0;
    public int pageSize = 20;

    public int getFirst() {
        return pageNumber * pageSize;
    }

    public boolean isPaggable() {
        return pageSize > 0;
    }

    public int getLast() {
        return (pageNumber + 1) * pageSize;
    }
}
