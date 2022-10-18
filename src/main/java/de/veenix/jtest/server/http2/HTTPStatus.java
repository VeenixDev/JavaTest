package de.veenix.jtest.server.http2;

public enum HTTPStatus {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    int code;

    HTTPStatus(int code) {
        this.code = code;
    }
}
