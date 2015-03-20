package com.softteco.toolset.sample.persons;

import com.google.inject.ImplementedBy;
import com.softteco.toolset.dto.PageInfoDto;
import java.util.List;

/**
 *
 * @author serge
 */
@ImplementedBy(PersonDaoBean.class)
public interface PersonDao {

    List<PersonEntity> findAll(PageInfoDto pageInfo);

    void persist(PersonEntity entity);
}
