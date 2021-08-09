package sso.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {
    /**服务端过滤器层面的跨域设计*/
    @Bean
    public FilterRegistrationBean<CorsFilter> filterFilterRegistrationBean(){
        //1.对此过滤器进行配置(跨域设置-url,method)
        UrlBasedCorsConfigurationSource configSource=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config=new CorsConfiguration();
        config.addAllowedHeader("*");//所有请求头信息
        config.addAllowedMethod("*");//所有请求方式,post,delete,get,put,....
        config.addAllowedOrigin("*");//所有请求参数
        config.setAllowCredentials(true);//所有认证信息,例如cookie
        //2.注册过滤器并设置其优先级
        configSource.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> fBean=
                new FilterRegistrationBean(
                        new CorsFilter(configSource));
        //设置此过滤器的优先级最高
        fBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return fBean;
    }

}