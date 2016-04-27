package com.softteco.toolset.push.android;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softteco.toolset.push.Payload;
import com.softteco.toolset.push.PushService;
import com.softteco.toolset.rs.AbstractRestDao;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 *
 * @author sergeizenevich
 */
public abstract class AbstractAndroidPushServiceBean extends AbstractRestDao implements PushService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected abstract String getKey();

    @Override
    protected final String getHost() {
        return "android.googleapis.com";
    }

    @Override
    protected final String getProtocol() {
        return "https";
    }

    private ResponseDto execute(final RequestDto requestDto) {
        PostMethod postMethod = null;
        String response = null;
        try {
            postMethod = new PostMethod("/gcm/send");
            postMethod.addRequestHeader("Content-Type", "application/json");
            postMethod.addRequestHeader("Accept", "application/json");
            postMethod.addRequestHeader("Authorization", "key=" + getKey());

            postMethod.setRequestEntity(new StringRequestEntity(objectMapper.writeValueAsString(requestDto), "application/json", "utf-8"));
            getClient().executeMethod(postMethod);

            response = postMethod.getResponseBodyAsString();
            return objectMapper.readValue(response, ResponseDto.class);
        } catch (UnsupportedEncodingException e) {
            System.out.println("RESPONSE: " + response);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("RESPONSE: " + response);
            throw new RuntimeException(e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
    }

    @Override
    public final boolean sendMessage(final String to, final Payload data) {
        final RequestDto requestDto = new RequestDto();
        requestDto.registrationIds = new ArrayList<>();
        requestDto.registrationIds.add(to);
        requestDto.data = data.buildAndroidObject();

        return execute(requestDto).success > 0;
    }
}
