package com.softteco.toolset.restlet;

import java.io.IOException;
import java.util.Date;
import org.restlet.data.MediaType;
import org.restlet.data.Tag;
import org.restlet.representation.Representation;
import org.restlet.representation.RepresentationInfo;
import org.restlet.resource.ResourceException;

/**
 *
 * @author sergeizenevich
 */
public abstract class AbstractConditionalResource<S extends UserSession> extends AbstractResource<S> {

    private Tag tag;
    private Date modificationDate;

    protected abstract Object getData();

    @Override
    protected void doInit() {
        super.doInit();

        tag = null;
        modificationDate = null;
    }

    @Override
    protected Representation get() throws ResourceException {
        try {
            final RepresentationInfo info = getInfo();
            final Representation representation = toRepresentation(getData(), info);
            if (representation.getTag() == null) {
                representation.setTag(info.getTag());
            }
            System.out.println("" + representation.getModificationDate() + " vs " + info.getModificationDate());
            if (representation.getModificationDate() == null) {
                representation.setModificationDate(info.getModificationDate());
            }
            if (representation.getExpirationDate() == null) {
                representation.setExpirationDate(getExpirationDate());
            }
            return representation;
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    protected MediaType getMediaType() {
        return MediaType.APPLICATION_JSON;
    }

    protected Date getExpirationDate() {
        return null;
    }

    protected Tag buildTag() {
        return null;
    }

    private Tag getTag() {
        if (tag == null) {
            tag = buildTag();
        }
        return tag;
    }

    protected Date buildModificationDate() {
        return null;
    }

    private Date getModificationDate() {
        if (modificationDate == null) {
            modificationDate = buildModificationDate();
        }
        return modificationDate;
    }

    @Override
    protected RepresentationInfo getInfo() throws ResourceException {
        final RepresentationInfo representationInfo = new RepresentationInfo(getMediaType());
        if (getTag() != null) {
            representationInfo.setTag(getTag());
        }
        System.out.println("getModificationDate: " + getModificationDate());
        if (getModificationDate() != null) {
            representationInfo.setModificationDate(getModificationDate());
        }
        return representationInfo;
    }
}
