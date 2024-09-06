package org.skwzz.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skwzz.global.exception.JwtAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e){
            log.info("expiredJwtException 터짐");
            Map<String, String> map = new HashMap<>();

            map.put("errortype", "Forbidden");
            map.put("code", "402");
            map.put("message", "만료된 토큰입니다. Refresh 토큰이 필요합니다.");

            log.error("만료된 토큰");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            log.info("생성된 response = {}", response);
        } catch (JwtException e){
            log.info("JwtException 터짐");
            Map<String, String> map = new HashMap<>();

            map.put("errortype", "Forbidden");
            map.put("code", "400");
            map.put("message", "변조된 토큰입니다. 로그인이 필요합니다.");

            log.error("변조된 토큰");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }
    }
}

