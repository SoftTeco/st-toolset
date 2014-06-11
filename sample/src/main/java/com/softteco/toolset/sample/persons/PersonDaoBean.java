package com.softteco.toolset.sample.persons;

import com.softteco.toolset.jpa.AbstractJpaDao;

/**
 *
 * @author serge
 */
public class PersonDaoBean extends AbstractJpaDao<PersonEntity, Long> implements PersonDao {

    @Override
    protected Class<PersonEntity> getEntityClass() {
        return PersonEntity.class;
    }
}
