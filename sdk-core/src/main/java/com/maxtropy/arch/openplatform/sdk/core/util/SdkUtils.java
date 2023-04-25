package com.maxtropy.arch.openplatform.sdk.core.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author luwang
 * @description
 * @date 2023/04/07
 */
public class SdkUtils {



    public static URI toEndpointURI(String endpoint, String defaultProtocol) throws IllegalArgumentException {
        if (endpoint != null && !endpoint.contains("://")) {
            endpoint = defaultProtocol + "://" + endpoint;
        }
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getCookie(Cookie[] cookies, String name) {
        if(cookies == null || cookies.length < 1 || !StringUtils.hasText(name)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
