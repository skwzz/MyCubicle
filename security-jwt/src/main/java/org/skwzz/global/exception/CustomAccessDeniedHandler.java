package org.skwzz.global.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // HTTP 403 Unauthorized 상태 코드 설정
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=UTF-8");

        // 에러 메시지 작성
        String errorMessage = "Unauthorized access: " + accessDeniedException.getMessage();

        // JWT 관련 예외 메시지를 사용자에게 전달
        PrintWriter writer = response.getWriter();
        writer.write("{ \"error\": \"" + errorMessage + "\" }");
        writer.flush();
        writer.close();
    }

}
