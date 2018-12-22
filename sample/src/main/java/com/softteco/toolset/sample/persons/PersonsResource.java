package com.softteco.toolset.sample.persons;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.softteco.toolset.dto.PageInfoDto;
import com.softteco.toolset.restlet.AbstractResource;
import com.softteco.toolset.sample.I18nServiceBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.HashMap;

/**
 *
 * @author serge
 */
@Api(value = "Persons", description = "Person list resource")
public class PersonsResource extends AbstractResource {

    @Inject
    private PersonService personService;
    @Inject
    @Named("page.size")
    private int pageSize;
    @Inject
    private I18nServiceBean i18nServiceBean;

    @ApiOperation(value = "list the persons", tags = "person", response = PersonsListDto.class)
    @ApiResponses({ @ApiResponse(code = 200, message = "the list of persons"), })
    @Get("json")
    public PersonsListDto getAll() {
        PageInfoDto dto = getPageInfo();
        dto.pageSize = pageSize;
        return personService.getAll(dto);
    }

    @ApiOperation(value = "add a person", tags = "person")
    @Post("json")
    public PersonDto create(final PersonDto dto) {
        // "send email"
        System.out.println(i18nServiceBean.getMessage(I18nServiceBean.BUNDLE_MAIL, "mail.subject"));
        System.out.println(i18nServiceBean.getMessage(I18nServiceBean.BUNDLE_MAIL, "mail.body", new HashMap<String, String>() {{
            put("name", "Customer");
        }}));
        // create
        return personService.create(dto);
    }
}
