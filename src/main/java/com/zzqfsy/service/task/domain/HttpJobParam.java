package com.zzqfsy.service.task.domain;

/**
 * Created by john on 16-12-23.
 */
public class HttpJobParam {
    private String ipAddress;
    private String method;
    private String param;

    public HttpJobParam() {
    }

    public HttpJobParam(String ipAddress, String param) {
        this.ipAddress = ipAddress;
        this.param = param;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
