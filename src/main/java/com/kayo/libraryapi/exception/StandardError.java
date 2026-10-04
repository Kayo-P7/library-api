package com.kayo.libraryapi.exception;

import java.time.Instant;
import java.util.List;

public record StandardError(
        Instant timeStamp,
        Integer status,
        String error,
        String message,
        String path,
        List<ErrorCampo> errorCampo

) {
}
