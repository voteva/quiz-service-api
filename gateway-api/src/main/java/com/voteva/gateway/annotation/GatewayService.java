package com.voteva.gateway.annotation;

import com.voteva.gateway.exception.model.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.stereotype.Service
public @interface GatewayService {

    String value() default "";

    Service serviceName() default Service.GATEWAY;
}
