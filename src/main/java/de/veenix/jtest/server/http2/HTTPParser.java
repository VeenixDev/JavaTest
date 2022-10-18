package de.veenix.jtest.server.http2;

import jdk.jshell.spi.ExecutionControl;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HTTPParser {

    private Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpResponseFunction;
    private Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpErrorResponseFunction;

    public HTTPParser(Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpResponseFunction,
                      Function<HTTPRequest, FunctionResponse<HTTPResponse>> httpErrorResponseFunction) {
        this.httpResponseFunction = httpResponseFunction;
        this.httpErrorResponseFunction = httpErrorResponseFunction;
    }

    public HTTPResponse parseFromSocket(Socket socket) {
        HTTPRequest request = parseRequest(socket);

        HashMap<String, String> headers = new HashMap<>();

        HTTPRequest request = new HTTPRequest(
                HTTPType.GET,
                "HTTP/1.1",
                "/favicon.ico",
                headers
        );

        FunctionResponse<HTTPResponse> response = httpResponseFunction.apply(request);

        if(response.isError()) {
            FunctionResponse<HTTPResponse> errorResponse = httpErrorResponseFunction.apply(request);
            if(errorResponse.isError()) {
                return new HTTPResponse(HTTPStatus.INTERNAL_SERVER_ERROR, null, null);
            }
            return errorResponse.getResponse();
        }

        return response.getResponse();
    }

    public HTTPRequest parseRequest(Socket socket) throws Exception {
        throw new Exception("test");
    }

    public Function<HTTPRequest, FunctionResponse<HTTPResponse>> getHttpErrorResponseFunction() {
        return httpErrorResponseFunction;
    }

    public Function<HTTPRequest, FunctionResponse<HTTPResponse>> getHttpResponseFunction() {
        return httpResponseFunction;
    }
}
