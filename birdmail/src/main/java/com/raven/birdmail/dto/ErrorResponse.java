package com.raven.birdmail.dto;

public record ErrorResponse(
        int status,
        String message
) {}
