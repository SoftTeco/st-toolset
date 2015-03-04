package com.softteco.toolset.rs;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

/**
 *
 * @author serge
 */
public abstract class AbstractRestDao {
    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    private HttpClient httpClient;

    protected abstract String getHost();

    protected final int getPort() {
        if (getProtocol().equals("http")) {
            return HTTP_PORT;
        }
        if (getProtocol().equals("https")) {
            return HTTPS_PORT;
        }
        throw new RuntimeException("Please reload this method.");
    }

    protected String getProtocol() {
        return "http";
    }

    protected boolean allowToUseSelfSignedCertificates() {
        return false;
    }

    protected final HttpClient getClient() {
        if (httpClient == null) {
            if (allowToUseSelfSignedCertificates()) {
                Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), HTTPS_PORT);
                Protocol.registerProtocol("https", easyhttps);
            }

            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
            httpClient.getHostConfiguration().setHost(getHost(), getPort(), getProtocol());
            configureClient(httpClient);
        }

        return httpClient;
    }

    protected void configureClient(final HttpClient client) {
    }
}
