package com.softteco.toolset.rs;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

/**
 *
 * @author serge
 */
public abstract class AbstractRestDao {

    private HttpClient httpClient;

    protected abstract String getHost();

    protected int getPort() {
        if (getProtocol().equals("http")) {
            return 80;
        }
        if (getProtocol().equals("https")) {
            return 443;
        }
        throw new RuntimeException("Please reload this method.");
    }

    protected String getProtocol() {
        return "http";
    }

    public HttpClient getClient() {
        if (httpClient == null) {
            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
            httpClient.getHostConfiguration().setHost(getHost(), getPort(), getProtocol());
            configureClient(httpClient);
        }

        return httpClient;
    }
    
    protected void configureClient(final HttpClient client) {
        
    }
}
