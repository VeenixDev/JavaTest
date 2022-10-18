package de.veenix.jtest.server.http2;

import java.util.HashMap;

public class HTTPRequest {

    private final HTTPType type;
    private final String version;
    private final String requestPath;
    private final HashMap<String, String> header;

    public HTTPRequest(HTTPType type, String version, String requestPath, HashMap<String, String> header) {
        this.type = type;
        this.version = version;
        this.requestPath = requestPath;
        this.header = header;
    }

    public HTTPType getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "HTTPRequest{" +
                "type=" + type +
                ", version='" + version + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", header=" + header +
                '}';
    }
}
