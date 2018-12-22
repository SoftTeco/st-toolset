package com.softteco.toolset.sample.persons;

import com.softteco.toolset.dto.PageDto;
import com.softteco.toolset.dto.PageInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author serge
 */
public class PersonsListDto extends PageDto<PersonDto> implements Serializable {

    public int count;

    public PersonsListDto() {
    }

    public PersonsListDto(PageInfoDto page, List<PersonDto> newLines) {
        super(page, newLines);
        count = newLines.size();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
