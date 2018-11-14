package com.voteva.gateway.exception.util;

import com.voteva.gateway.exception.GatewayApiException;
import com.voteva.gateway.exception.model.Service;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;

public class GRpcExceptionUtil {

    public static GatewayApiException convertByUsersAuth(StatusRuntimeException exception) {
        return convert(Service.USERS_AUTH, exception);
    }

    public static GatewayApiException convertByTests(StatusRuntimeException exception) {
        return convert(Service.TESTS, exception);
    }

    public static GatewayApiException convertByInterview(StatusRuntimeException exception) {
        return convert(Service.INTERVIEW, exception);
    }

    public static GatewayApiException convertByQuiz(StatusRuntimeException exception) {
        return convert(Service.QUIZ, exception);
    }

    private static GatewayApiException convert(Service service, StatusRuntimeException exception) {
        return new GatewayApiException(service, convertToHttpStatus(exception.getStatus()), exception.getMessage());
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
