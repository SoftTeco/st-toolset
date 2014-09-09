package com.softteco.toolset.dto;

import java.io.Serializable;

/**
 *
 * @author serge
 */
public class PageInfoDto implements Serializable {

    public int pageNumber = 0;
    public int pageSize = 20;
    
    public SortInfoDto[] sort;

    public int getFirst() {
        return pageNumber * pageSize;
    }
}
