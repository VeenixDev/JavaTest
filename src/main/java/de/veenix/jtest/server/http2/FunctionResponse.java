package de.veenix.jtest.server.http2;

public class FunctionResponse<R> {

    private final boolean error;
    private final R response;

    public FunctionResponse(boolean error, R response) {
        this.error = error;
        this.response = response;
    }
    public boolean isError() {
        return error;
    }

    public R getResponse() {
        return response;
    }
}
