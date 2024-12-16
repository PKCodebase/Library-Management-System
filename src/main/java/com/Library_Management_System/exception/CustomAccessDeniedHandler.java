package com.Library_Management_System.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        // Setting the HTTP status to FORBIDDEN
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Sending a custom error message in the response body
        response.getWriter().write("You don't have access to this resource. Please check your permissions.");

        // Optionally, you can set the content type to application/json
        response.setContentType("application/json");
    }
}
