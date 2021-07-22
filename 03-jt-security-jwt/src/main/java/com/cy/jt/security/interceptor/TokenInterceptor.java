package com.cy.jt.security.interceptor;

import com.cy.jt.security.util.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===ppppppp==");
        String token = request.getParameter("Authentication");
        if(!StringUtils.hasLength(token))
            throw new RuntimeException("please login");
        if(JwtUtils.isTokenExpired(token))
            throw new RuntimeException("login timeout,please login");

        return true;
    }
}
