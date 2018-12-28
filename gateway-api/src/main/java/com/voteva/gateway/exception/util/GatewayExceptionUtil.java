package com.voteva.gateway.exception.util;

import com.voteva.gateway.exception.GatewayApiException;
import com.voteva.gateway.exception.model.Service;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;

class GatewayExceptionUtil {

    static GatewayApiException convert(Service service, Exception ex) {
        if (ex instanceof StatusRuntimeException) {
            return new GatewayApiException(
                    service,
                    convertToHttpStatus(((StatusRuntimeException) ex).getStatus()),
                    ex.getMessage());
        } else if (ex instanceof GatewayApiException) {
            return (GatewayApiException) ex;
        }
        return new GatewayApiException(service, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private static HttpStatus convertToHttpStatus(Status status) {
        switch (status.getCode()) {
            case OK:
                return HttpStatus.OK;
            case UNAUTHENTICATED:
                return HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case UNAVAILABLE:
                return HttpStatus.SERVICE_UNAVAILABLE;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
