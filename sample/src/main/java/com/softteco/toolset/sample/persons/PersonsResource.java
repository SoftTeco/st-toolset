package com.softteco.toolset.sample.persons;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.softteco.toolset.dto.PageInfoDto;
import com.softteco.toolset.restlet.AbstractResource;
import com.softteco.toolset.sample.I18nServiceBean;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.HashMap;

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
    @Inject
    private I18nServiceBean i18nServiceBean;

    @Get("json")
    public PersonsListDto getAll() {
        PageInfoDto dto = getPageInfo();
        dto.pageSize = pageSize;
        return personService.getAll(dto);
    }

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
