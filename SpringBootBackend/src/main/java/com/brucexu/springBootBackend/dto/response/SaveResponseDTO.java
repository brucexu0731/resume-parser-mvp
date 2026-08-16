package com.brucexu.springBootBackend.dto.response;

public record SaveResponseDTO(
        Long personalId,
        String responseMessage //"DUPLICATE" or "SUCCESS" -> an enum probably better
) {}
