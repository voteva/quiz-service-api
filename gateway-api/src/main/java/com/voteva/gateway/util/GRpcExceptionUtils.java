package com.voteva.gateway.util;

import com.voteva.gateway.exception.GatewayApiException;
import io.grpc.StatusRuntimeException;

public class GRpcExceptionUtils {

    public static GatewayApiException convert(StatusRuntimeException exception) {
        return new GatewayApiException(exception.getStatus().toString() + "; " + exception.getMessage());
    }
}
