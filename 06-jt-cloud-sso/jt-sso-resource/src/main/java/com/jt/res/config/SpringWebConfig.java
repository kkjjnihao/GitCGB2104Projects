package com.jt.res.config;

import com.jt.res.interceptor.TokenInterceptor;
import com.jt.res.service.RemoteAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RemoteAuthService remoteAuthService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(remoteAuthService))
                .addPathPatterns("/**");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new TokenInterceptor(restTemplate))
//                .addPathPatterns("/**");
//    }
}
