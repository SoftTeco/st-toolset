package com.softteco.toolset.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author serge
 */
public class PageDto<Dto extends Serializable> extends PageInfoDto implements Serializable {

    public boolean hasNext;
    public List<Dto> lines;

    public PageDto() {
    }

    public PageDto(final PageInfoDto page, final List<Dto> lines) {
        this.pageSize = page.pageSize;
        this.pageNumber = page.pageNumber;
        this.lines = lines;
        hasNext = lines.size() > pageSize;

        if (hasNext) {
            this.lines.remove(this.lines.size() - 1);
        }
    }
}
