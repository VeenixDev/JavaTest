package de.veenix.jtest.server.http2;

import java.util.HashMap;

public class HTTPResponse {

    private final HTTPStatus status;
    private final HashMap<String, Object> body;
    private final HashMap<String, Object> header;

    public HTTPResponse(HTTPStatus status, HashMap<String, Object> body, HashMap<String, Object> header) {
        this.status = status;
        this.body = body;
        this.header = header;
    }

    public HTTPStatus getStatus() {
        return status;
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public HashMap<String, Object> getHeader() {
        return header;
    }
}
