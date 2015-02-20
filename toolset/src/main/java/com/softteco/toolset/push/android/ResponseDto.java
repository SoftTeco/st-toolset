package com.softteco.toolset.push.android;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sergeizenevich
 */
public class ResponseDto implements Serializable {

    public Long multicast_id;
    public Long success;
    public Long failure;
    public Long canonical_ids;
    public List<ResultEntry> results;
}
