package com.voteva.gateway.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class HttpUtils {

    public static String getRemoteAddress(HttpServletRequest request) {
        String xff = getXForwardedFor(request);
        return xff.isEmpty() ? request.getRemoteAddr() : xff;
    }

    private static String getXForwardedFor(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null) {
            StringTokenizer tokenizer = new StringTokenizer(xff, ",");
            if (tokenizer.hasMoreTokens()) {
                return StringUtils.trim(tokenizer.nextToken());
            }
        }
        return StringUtils.EMPTY;
    }
}
