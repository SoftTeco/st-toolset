package com.softteco.toolset.sample.persons;

import com.softteco.toolset.bl.AutofillAbstractAssembler;

/**
 *
 * @author serge
 */
public class PersonDtoAssembler extends AutofillAbstractAssembler<PersonEntity, PersonDto> {

    PersonEntity disassemble(final PersonDto dto) {
        final PersonEntity entity = new PersonEntity();
        disassemble(entity, dto);
        return entity;
    }
}
