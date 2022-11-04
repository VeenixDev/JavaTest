package de.veenix.jtest.server.http2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The class represents a response from the web server
 */
public class HTTPResponse {

    //TODO: Change header management to support "Set-Cookie" in response
    private final HTTPStatus status;
    private final String body;
    private final byte[] binData;
    private final HashMap<String, String> header;

    public HTTPResponse(HTTPStatus status, String body, HashMap<String, String> header) {
        this.status = status;
        this.body = body;
        this.header = header;
        binData = null;
    }

    public HTTPResponse(HTTPStatus status, byte[] binData, HashMap<String, String> header) {
        this.status = status;
        body = null;
        this.header = header;
        this.binData = binData;
    }

    public void write(OutputStream stream) throws IOException {
        stream.write("HTTP/1.1\n".getBytes());

        if(header != null) {
            for(Map.Entry<String, String> headerValue : header.entrySet()) {
                stream.write((headerValue.getKey() + ": " + headerValue.getValue() + "\n").getBytes());
            }
        }

        stream.write("Server: VHTTP/0.1\n".getBytes());

        if(body != null) {
            stream.write(("Content-Length: " + (body.length() - 1) + "\n").getBytes());
            stream.write("\n".getBytes());
            stream.write(body.getBytes());
        } else if(binData != null) {
            stream.write(("Content-Length: " + (binData.length - 1) + "\n").getBytes());
            stream.write("\n".getBytes());
            stream.write(binData);
        } else {
            stream.write("\n".getBytes());
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

    public byte[] getBinData() {
        return binData;
    }
}
