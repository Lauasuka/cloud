package io.amoe.cloud.account.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @date 2020/9/15 14:47
 */
@Slf4j
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class SessionConfig {
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new AuthHeaderHttpSessionIdResolver();
    }

    public static class AuthHeaderHttpSessionIdResolver implements HttpSessionIdResolver{
        private static final String HEADER_TOKEN = "Token";
        private final String headerName;

        public AuthHeaderHttpSessionIdResolver() {
            this(HEADER_TOKEN);
        }

        public AuthHeaderHttpSessionIdResolver(String headerName) {
            this.headerName = headerName;
        }

        @Override
        public List<String> resolveSessionIds(HttpServletRequest request) {
            String headerValue = request.getHeader(this.headerName);
            return StringUtils.isNotBlank(headerValue) ? Collections.singletonList(base64Decode(headerValue)) : Collections.emptyList();
        }

        @Override
        public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
            response.setHeader(this.headerName, base64Encode(sessionId));
        }

        @Override
        public void expireSession(HttpServletRequest request, HttpServletResponse response) {
            response.setHeader(this.headerName, "");
        }

        private String base64Decode(String base64Value) {
            try {
                byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
                return new String(decodedCookieBytes);
            }
            catch (Exception ex) {
                log.debug("Unable to Base64 decode value: " + base64Value);
                return null;
            }
        }

        private String base64Encode(String value) {
            byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
            return new String(encodedCookieBytes);
        }
    }
}
