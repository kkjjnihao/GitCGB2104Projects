package com.cy.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**登录成功以后返回给客户端一个json数据*/
public class JsonAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        //获得用户信息
        User principal = (User)authentication.getPrincipal();
        System.out.println(principal);
        //获得用户凭证(默认为密码)
        Object credentials = authentication.getCredentials();
        System.out.println(credentials);
        //设置响应数据的编码
        httpServletResponse.setCharacterEncoding("utf-8");
        //告诉客户端响应数据的类型,以及客户端以怎样的编码进行显示
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //获取一个输出流对象
        PrintWriter out=httpServletResponse.getWriter();
        //
        //
        Map<String,Object> map = new HashMap<>();
        map.put("state","200");
        map.put("msg", "ok");
        //
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        //向客户端输出一个json格式字符串
        //out.println("{\"state\":200,\"msg\":\"ok\"}");
        out.println(jsonStr);
        out.flush();

    }
}
