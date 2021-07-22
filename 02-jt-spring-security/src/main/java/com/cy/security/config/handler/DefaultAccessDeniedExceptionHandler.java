package com.cy.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认的用于处理访问被拒绝的异常处理器对象
 * 1)Default 默认
 * 2)Access 访问
 * 3)Denied 拒绝
 * 4)Excepiton 异常
 * 5)Handler 处理器
 */
public class DefaultAccessDeniedExceptionHandler
                   implements AccessDeniedHandler {
    /**
     * 此方法用于处理AccessDeniedException对象
     * @param httpServletRequest 请求对象
     * @param httpServletResponse 响应对象
     * @param exception 访问被拒绝的的异常对象
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException exception)
            throws IOException, ServletException {
        //方案1:重定向
       //httpServletResponse.sendRedirect("http://www.tedu.cn");
      //方案2:假如访问被拒绝了向客户端响应一个json格式的字符串
        //2.1设置响应数据的编码
        httpServletResponse.setCharacterEncoding("utf-8");
        //2.2告诉浏览器响应数据的内容类型以及编码
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //2.3获取输出流对象
        PrintWriter out=httpServletResponse.getWriter();
        //2.4将数据输出到客户端
        //2.4.1封装数据
        Map<String,Object> map=new HashMap<>();
        map.put("state",HttpServletResponse.SC_FORBIDDEN);//SC_FORBIDDEN的值是403
        map.put("message","没有访问权限,请联系管理员");
        //2.4.2将数据转换为json格式字符串
        String jsonStr=
        new ObjectMapper().writeValueAsString(map);
        //2.4.3输出数据
        out.println(jsonStr);
        out.flush();
    }
}
