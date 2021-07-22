package com.cy.jt.security.config;

import com.cy.jt.security.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    /**
     * 将拦截器添加到spring mvc的执行链中
     * @param registry 此对象提供了一个list集合,可以将拦截器添加到集合中
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                //配置要拦截的url
                .addPathPatterns("/retrieve","/update");
    }
}
