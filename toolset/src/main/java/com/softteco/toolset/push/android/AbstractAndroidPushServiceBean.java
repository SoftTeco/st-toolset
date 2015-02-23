package com.softteco.toolset.push.android;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softteco.toolset.push.PushService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 *
 * @author sergeizenevich
 */
public abstract class AbstractAndroidPushServiceBean implements PushService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient httpClient;

    protected abstract String getKey();

    public HttpClient getClient() {
        if (httpClient == null) {
            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
            httpClient.getHostConfiguration().setHost("android.googleapis.com", 443, "https");
        }

        return httpClient;
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

            return objectMapper.readValue(response = postMethod.getResponseBodyAsString(), ResponseDto.class);
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
    public boolean sendMessage(final String to, final Object data) {
        final RequestDto requestDto = new RequestDto();
        requestDto.registration_ids = new ArrayList<>();
        requestDto.registration_ids.add(to);
        requestDto.data = data;

        return execute(requestDto).success > 0;
    }
}
