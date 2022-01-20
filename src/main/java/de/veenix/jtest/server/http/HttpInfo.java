package de.veenix.jtest.server.http;

public class HttpInfo {

    private RequestMethod method;
    private String version;
    private String path;

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static RequestMethod getMethodFromString(String method) {
        return switch (method) {
            case "GET" -> RequestMethod.GET;
            case "POST" -> RequestMethod.POST;
            case "PUT" -> RequestMethod.PUT;
            case "DELETE" -> RequestMethod.DELETE;
            default -> null;
        };
    }

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

}
