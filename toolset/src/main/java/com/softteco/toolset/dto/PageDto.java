package com.softteco.toolset.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author serge
 * @param <Dto>
 */
public class PageDto<Dto extends Serializable> extends PageInfoDto implements Serializable {

    public boolean hasNext;
    public List<Dto> lines;

    public PageDto() {
    }

    public PageDto(final PageInfoDto page, final List<Dto> newLines) {
        if (page != null) {
            this.pageSize = page.pageSize;
            this.pageNumber = page.pageNumber;
            this.sort = page.sort;
        }
        this.lines = newLines;
        hasNext = newLines.size() > pageSize;

        if (hasNext) {
            this.lines.remove(this.lines.size() - 1);
        }
    }

    public PageInfoDto nextPage() {
        if (!hasNext) {
            return null;
        }

        final PageInfoDto pageInfoDto = new PageInfoDto();
        pageInfoDto.pageSize = this.pageSize;
        pageInfoDto.pageNumber = this.pageNumber + 1;
        pageInfoDto.sort = this.sort;
        return pageInfoDto;
    }
}
