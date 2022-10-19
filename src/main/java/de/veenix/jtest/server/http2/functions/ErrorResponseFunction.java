package de.veenix.jtest.server.http2.functions;

import de.veenix.jtest.server.http2.FunctionResponse;
import de.veenix.jtest.server.http2.HTTPRequest;
import de.veenix.jtest.server.http2.HTTPResponse;
import de.veenix.jtest.server.http2.HTTPStatus;

import java.util.function.Function;

public class ErrorResponseFunction implements Function<HTTPRequest, FunctionResponse<HTTPResponse>> {
    @Override
    public FunctionResponse<HTTPResponse> apply(HTTPRequest httpRequest) {
        return new FunctionResponse<>(false, new HTTPResponse(HTTPStatus.INTERNAL_SERVER_ERROR, "<html><head><title>Ups...</title></head><body>Something unexpected happened we are sorry for the inconveniences</body></html>", null));
    }
}
