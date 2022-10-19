package de.veenix.jtest.server.http2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {

    private final HTTPStatus status;
    private final String body;
    private final HashMap<String, String> header;

    public HTTPResponse(HTTPStatus status, String body, HashMap<String, String> header) {
        this.status = status;
        this.body = body;
        this.header = header;
    }

    public void write(OutputStream stream, HTTPResponse response) throws IOException {
        stream.write("HTTP/1.1\r\n".getBytes());

        if(response.header != null) {
            for(Map.Entry<String, String> headerValue : header.entrySet()) {
                stream.write((headerValue.getKey() + ": " + headerValue.getValue() + "\r\n").getBytes());
            }
        }

        stream.write("Server: VHTTP/0.1\r\n".getBytes());

        if(response.body != null) {
            stream.write(("Content-Length: " + (response.getBody().length() - 1) + "\r\n").getBytes());
            stream.write("\r\n".getBytes());
            stream.write(response.getBody().getBytes());
        } else {
            stream.write("\r\n".getBytes());
        }

        stream.flush();
        stream.close();
    }

    public HTTPStatus getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }
}
