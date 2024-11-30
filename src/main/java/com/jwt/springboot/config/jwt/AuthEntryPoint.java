package com.jwt.springboot.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

        /*
          Unauthorized Access (401)
           - Full authentication is required to access this resource
           - JWT token is expired
          Access Denied (403)
           - Access is denied
          Malformed Token
           - JWT string argument cannot be null or empty
           - An error occurred while decoding JWT
          **/

        logger.error("Unauthorized error: {}", authException.getMessage());
        String errorMessage = "Unauthorized";
        String jsonResponse = "{"
                + "\"error\": \""+errorMessage+"\","
                + "\"message\": \"" + authException.getMessage() + "\""
                + "}";

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
