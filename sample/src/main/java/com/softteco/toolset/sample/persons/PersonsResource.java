package com.softteco.toolset.sample.persons;

import com.google.inject.Inject;
import com.softteco.toolset.restlet.AbstractResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 *
 * @author serge
 */
public class PersonsResource extends AbstractResource {

    @Inject
    private PersonService personService;

    @Get("json")
    public PersonsListDto getAll() {
        return personService.getAll(getPageInfo());
    }

    @Post("json")
    public PersonDto create(final PersonDto dto) {
        return personService.create(dto);
    }
}
