package com.softteco.toolset.sample.persons;

import com.softteco.toolset.bl.AbstractAssembler;

/**
 *
 * @author serge
 */
public class PersonDtoAssembler extends AbstractAssembler<PersonEntity, PersonDto> {

    @Override
    protected Class<PersonDto> getDtoClass() {
        return PersonDto.class;
    }

    @Override
    public void assemble(final PersonDto dto, final PersonEntity entity) {
        dto.account = entity.getAccount();
        dto.name = entity.getName();
    }

    PersonEntity disassemble(PersonDto dto) {
        final PersonEntity entity = new PersonEntity();
        entity.setAccount(dto.account);
        entity.setName(dto.name);
        return entity;
    }
}
