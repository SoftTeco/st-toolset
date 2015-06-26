package com.softteco.toolset.sample.persons;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.softteco.toolset.dto.PageInfoDto;
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
    @Inject
    @Named("page.size")
    private int pageSize;

    @Get("json")
    public PersonsListDto getAll() {
        PageInfoDto dto = getPageInfo();
        dto.pageSize = pageSize;
        return personService.getAll(dto);
    }

    @Post("json")
    public PersonDto create(final PersonDto dto) {
        return personService.create(dto);
    }
}
