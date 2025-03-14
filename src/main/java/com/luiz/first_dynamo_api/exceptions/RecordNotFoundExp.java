package com.luiz.first_dynamo_api.exceptions;

public class RecordNotFoundExp extends RuntimeException {
    public RecordNotFoundExp(String entity) {
        super(String.format("{error: \"%s NOT FOUND\"}", entity));
    }
}