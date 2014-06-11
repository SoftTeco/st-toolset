package com.softteco.toolset.sample.persons;

import com.softteco.toolset.dto.PageDto;
import java.io.Serializable;

/**
 *
 * @author serge
 */
public class PersonsListDto extends PageDto<PersonDto> implements Serializable {

    public int count;
}
