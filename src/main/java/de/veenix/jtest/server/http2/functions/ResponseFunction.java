package de.veenix.jtest.server.http2.functions;

import de.veenix.jtest.server.http2.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Log4j2
public class ResponseFunction implements Function<HTTPRequest, FunctionResponse<HTTPResponse>> {
    @Override
    public FunctionResponse<HTTPResponse> apply(HTTPRequest httpRequest) {
        log.info(httpRequest.toString());
        HashMap<String, String> headers = new HashMap<>();
        if(httpRequest.getHeader() != null) {
            copyKey("cookies", headers, httpRequest.getHeader());
        }

        if(httpRequest.getType() == HTTPType.GET && httpRequest.getRequestPath().equals("/stopServer")) {
            // Killswitch for the server
            Main.setRunning(false);
            return new FunctionResponse<>(false, new HTTPResponse(HTTPStatus.OK, "<html><head><title>Test</title></head><body>Server Stopped!</body></html>", headers));
        }

        return new FunctionResponse<>(false, new HTTPResponse(HTTPStatus.OK, "<html><head><title>Test</title></head><body>FooBar</body></html>", headers));
    }

    private <K, V> void copyKey(final K key, Map<K, V> copyTo, Map<K, V> copyFrom) {
        if(copyFrom.containsKey(key)) {
            copyTo.put(key, copyFrom.get(key));
        }
    }
}
