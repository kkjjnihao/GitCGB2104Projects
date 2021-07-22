package com.cy.security.config;

import com.cy.security.config.handler.DefaultAccessDeniedExceptionHandler;
import com.cy.security.config.handler.DefaultAuthenticationEntryPoint;
import com.cy.security.config.handler.DefaultAuthenticationFailureHandler;
import com.cy.security.config.handler.JsonAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.DelegatingAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

/**
 * 由@Configuration注解描述的类为spring中的配置类,配置类会在spring
 * 工程启动时优先加载,在配置类中通常会对第三方资源进行初始配置.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 对http请求的安全控制进行配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.配置登录url(登录表单使用那个页面)
        http.formLogin().loginPage("/login.html")
            .loginProcessingUrl("/login")
            //.usernameParameter("username")//表单提交属性为username可以省略
            //.passwordParameter("password")//表单提交属性为password可以省略
            //.successForwardUrl()//请求转发
            .defaultSuccessUrl("/index.html")//
            //.successHandler(new JsonAuthenticationSuccessHandler())
            //.successHandler(new RedirectAuthenticationSuccessHandler("http://www.tedu.cn"))
            //.failureHandler(new DefaultAuthenticationFailureHandler())
            //.failureUrl()
            .and()//完成上部分配置,开始另一配置
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/logout.html?logout")
            ;
        //设置需要认证与拒绝访问的异常处理器
        http.exceptionHandling()
                .accessDeniedHandler(
                        new DefaultAccessDeniedExceptionHandler());
        //设置需要登录后访问的异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(
                        new DefaultAuthenticationEntryPoint());
        //3.放行登录url(不需要认证就可以访问)
        http.authorizeRequests()
            .antMatchers(//配置下列路径的授权
                    "/login.html",
                    "/js/*",
                    "/css/*",
                    "/images/**",
                    "/bower_components/**")//这里写你要放行的资源
            .permitAll()//允许直接访问
            .anyRequest().authenticated()//除了以上资源必须认证才能访问
            ;
    }


    /**
     * 定义SpringSecurity密码加密对象
     * @Bean 注解通常会在@Configuration注解描述的类中描述方法,
     * 用于告诉spring框架这个方法的返回值会交给spring管理,并spring
     * 管理的这个对象起个默认的名字,这个名字与方法名相同,当然也可以通过
     * @Bean注解起名字
     */
    @Bean//对象名默认为方法名
    //@Bean("bcryptPasswordEncoder")//bean对象名字为bcryptPasswordEncoder
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
