package com.softteco.toolset.sample.persons;

import com.google.inject.ImplementedBy;
import com.softteco.toolset.dto.PageInfoDto;

/**
 *
 * @author serge
 */
@ImplementedBy(PersonServiceBean.class)
public interface PersonService {

    PersonsListDto getAll(PageInfoDto pageInfo);

    PersonDto create(PersonDto dto);
}
