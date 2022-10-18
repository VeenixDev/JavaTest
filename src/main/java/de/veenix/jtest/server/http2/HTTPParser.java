package de.veenix.jtest.server.http2;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

@Log4j2
public class HTTPParser {

    private final Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpResponseFunction;
    private final Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpErrorResponseFunction;
    private BufferedReader reader;

    public HTTPParser(Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpResponseFunction,
                      Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpErrorResponseFunction) {
        this.httpResponseFunction = httpResponseFunction;
        this.httpErrorResponseFunction = httpErrorResponseFunction;
    }

    public HTTPResponse parseFromSocket(Socket socket) {
        HTTPRequest request = parseRequest(socket);

        FunctionResponse<HTTPResponse> response = httpResponseFunction.apply(request);

        if (response.isError()) {
            FunctionResponse<HTTPResponse> errorResponse = httpErrorResponseFunction.apply(request);
            if (errorResponse.isError()) {
                log.error("Ran into an error while generating the error response");
                return new HTTPResponse(HTTPStatus.INTERNAL_SERVER_ERROR, null, null);
            }
            log.warn("Generated error response without further errors");
            return errorResponse.getResponse();
        }

        log.info("Generated response without errors");
        return response.getResponse();
    }

    public HTTPRequest parseRequest(Socket socket) {
        String firstLine;
        HashMap<String, String> headers = new HashMap<>();
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            firstLine = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    break;
                }
                String[] lineSplit = line.split(":");
                headers.put(lineSplit[0], lineSplit[1]);

            }
        } catch (IOException e) {
            log.error("Failed at parsing request: " + e.getMessage());
            throw new RuntimeException(e);
        }
        String[] args = firstLine.split(" ", Integer.MAX_VALUE);
        return new HTTPRequest(HTTPType.valueOf(args[0]), args[2], args[1], headers);
    }

    public Function<HTTPRequest, FunctionResponse<HTTPResponse>> getHttpErrorResponseFunction() {
        return httpErrorResponseFunction;
    }

    public Function<HTTPRequest, FunctionResponse<HTTPResponse>> getHttpResponseFunction() {
        return httpResponseFunction;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
