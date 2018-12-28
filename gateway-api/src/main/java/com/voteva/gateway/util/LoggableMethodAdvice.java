package com.voteva.gateway.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class LoggableMethodAdvice {

    private static final Logger defaultLogger = LoggerFactory.getLogger(LoggableMethodAdvice.class);

    private final LoadingCache<Class, Logger> loggerCache = CacheBuilder.newBuilder().build(
            new CacheLoader<Class, Logger>() {
                @Override
                public Logger load(Class clazz) {
                    return LoggerFactory.getLogger(clazz);
                }
            });

    @AfterThrowing(value = "@annotation(loggedAnnotation)", throwing = "ex")
    private void afterThrowing(JoinPoint jp, Exception ex, Logged loggedAnnotation) {
        Signature signature = jp.getSignature();
        Object[] args = jp.getArgs();

        StringBuilder sb = new StringBuilder();
        sb.append("Failed");
        if (signature != null) {
            sb.append(" ")
                    .append(signature.getDeclaringType().getSimpleName())
                    .append(".")
                    .append(signature.getName())
                    .append("(");

            if (args.length > 0) {
                sb.append(Stream.of(args)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")));
            }
            sb.append(")");
        }

        sb.append(" with reason: ");
        sb.append(ex.getMessage());

        Logger logger = Optional.ofNullable(signature)
                .map(Signature::getDeclaringType)
                .map(loggerCache::getUnchecked)
                .orElse(defaultLogger);

        log(logger, loggedAnnotation.value(), sb.toString());
    }

    private void log(Logger logger, Level level, String message) {
        switch (level) {
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(message);
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(message);
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(message);
                }
                break;
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(message);
                }
                break;
            case TRACE:
                if (logger.isTraceEnabled()) {
                    logger.trace(message);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported logging level: " + level);
        }
    }
}
