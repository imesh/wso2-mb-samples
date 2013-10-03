package org.imesh.sample.wso2.mb.message;

import java.io.Serializable;

public class Member implements Serializable {
    private String protocol;
    private String ipAddress;
    private int httpPort;
    private int httpProxyPort;
    private int httpsPort;
    private int httpsProxyPort;
    private String iaasName;
    private String status;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public int getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public int getHttpsProxyPort() {
        return httpsProxyPort;
    }

    public void setHttpsProxyPort(int httpsProxyPort) {
        this.httpsProxyPort = httpsProxyPort;
    }

    public String getIaasName() {
        return iaasName;
    }

    public void setIaasName(String iaasName) {
        this.iaasName = iaasName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
