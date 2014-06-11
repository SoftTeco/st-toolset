package com.softteco.toolset.sample.persons;

import com.google.inject.Inject;
import com.softteco.toolset.dto.PageInfoDto;

/**
 *
 * @author serge
 */
public class PersonServiceBean implements PersonService {

    @Inject
    private PersonDao personDao;
    @Inject
    private PersonDtoAssembler personDtoAssembler;

    @Override
    public PersonsListDto getAll(final PageInfoDto pageInfo) {
        final PersonsListDto dto = new PersonsListDto();
        dto.lines = personDtoAssembler.assemble(personDao.findAll(pageInfo));
        return dto;
    }

    @Override
    public PersonDto create(final PersonDto dto) {
        final PersonEntity entity = personDtoAssembler.disassemble(dto);
        personDao.persist(entity);
        return personDtoAssembler.assemble(entity);
    }
}
