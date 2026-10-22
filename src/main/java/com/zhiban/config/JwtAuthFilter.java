package com.zhiban.config;

import com.zhiban.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uri = request.getRequestURI();

        // 静态资源直接放行
        if (uri.contains("/swagger") || uri.contains("/api-docs") || uri.contains("/uploads")) {
            chain.doFilter(request, response);
            return;
        }

        // 尝试解析 token（如果有的话）
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                if (jwtUtil.isTokenExpired(token)) {
                    throw new RuntimeException("token已过期");
                }
                Long userId = jwtUtil.getUserId(token);
                String username = jwtUtil.getUsername(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userId, null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER")));
                auth.setDetails(username);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // token 无效，继续处理请求
            }
        }

        // 检查是否需要登录
        boolean isPublic = uri.contains("/login") || uri.contains("/register")
                || uri.contains("/api/util")
                || uri.equals("/api/post/list") || uri.contains("/api/comment/post/");

        if (!isPublic && SecurityContextHolder.getContext().getAuthentication() == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
