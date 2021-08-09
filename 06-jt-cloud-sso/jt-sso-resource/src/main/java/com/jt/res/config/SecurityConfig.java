package com.jt.res.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import sso.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.设置拒绝处理器(不允许访问资源时,应该给出什么反馈)
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        //3.资源访问(所有资源在本项目中的访问不进行认证)
        http.authorizeRequests().anyRequest().permitAll();
    }
    /**资源访问被拒绝的处理器*/
    public AccessDeniedHandler accessDeniedHandler(){
        return (request,response,e)->{
            //1.构建响应信息
            Map<String,Object> map=new HashMap<>();
            map.put("state",403);
            map.put("message","权限不足");
            //2.将响应信息写到客户端
            WebUtils.writeJsonToClient(response,map);
        };
    }
}
