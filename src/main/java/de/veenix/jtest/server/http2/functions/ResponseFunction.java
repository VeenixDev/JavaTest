package de.veenix.jtest.server.http2.functions;

import de.veenix.jtest.server.http2.*;
import lombok.extern.log4j.Log4j2;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class ResponseFunction implements Function<HTTPRequest, FunctionResponse<HTTPResponse>> {
    @Override
    public FunctionResponse<HTTPResponse> apply(HTTPRequest httpRequest) {
        log.info(httpRequest.toString());
        HashMap<String, String> headers = new HashMap<>();
        if(httpRequest.getHeader() != null) {
            copyKey("cookies", headers, httpRequest.getHeader());
            copyKey("session", headers, httpRequest.getHeader(), () -> UUID.randomUUID().toString());
        }
        String[] requestedFileNameSplit = httpRequest.getRequestPath().split("/", Integer.MAX_VALUE);
        FileInfo fileInfo = Utils.getFileInfo(requestedFileNameSplit[requestedFileNameSplit.length - 1]);
        headers.put("Content-Type" , fileInfo.getMimeType());

        if(httpRequest.getType() == HTTPType.GET && httpRequest.getRequestPath().equals("/stopServer")) {
            // Killswitch for the server
            Main.setRunning(false);
            log.info("Server stops after the last request is handled");
            return new FunctionResponse<>(false, new HTTPResponse(HTTPStatus.OK, "<html><head><title>Test</title></head><body>Server Stopped!</body></html>", headers));
        }

        byte[] content = Utils.readFile(httpRequest.getRequestPath(), fileInfo.isBinary());
        if(content != null) {
            if(httpRequest.getRequestPath().equals("/assets/logo.svg")) {
                StringBuilder sb = new StringBuilder();
                for(byte b : content) {
                    sb.append((char) b);
                }
                log.info(sb.toString());
            }

            return new FunctionResponse<>(false, new HTTPResponse(HTTPStatus.OK, Utils.readFile(httpRequest.getRequestPath(), fileInfo.isBinary()), headers));
        } else {
            log.warn("Couldn't find requested file");
            return new FunctionResponse<>(true, new HTTPResponse(HTTPStatus.NOT_FOUND, "<html><head><title>404 - Not found</title></head><body>404 - Not found</body></html>", headers));
        }
    }

    private <K, V> void copyKey(final K key, Map<K, V> copyTo, Map<K, V> copyFrom, Supplier<V> supplier) {
        if(copyFrom.containsKey(key)) {
            copyTo.put(key, copyFrom.get(key));
        } else if(supplier != null) {
            copyTo.put(key, supplier.get());
        }
    }

    private <K, V> void copyKey(final K key, Map<K, V> copyTo, Map<K, V> copyFrom) {
        copyKey(key, copyTo, copyFrom, null);
    }
}
