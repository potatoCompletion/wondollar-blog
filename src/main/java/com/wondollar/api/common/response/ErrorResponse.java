package com.wondollar.api.common.response;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

public record ErrorResponse(String code, String message, List<String> errorList) {}