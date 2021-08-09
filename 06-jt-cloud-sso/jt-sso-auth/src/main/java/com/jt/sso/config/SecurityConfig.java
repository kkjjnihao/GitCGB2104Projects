package com.jt.sso.config;

import com.jt.sso.util.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sso.util.WebUtils;

import java.util.*;

/**构建配置安全对象
 * 1)认证规则(哪些资源必须认证才可访问)
 * 2)加密规则(添加用户时密码写到了数据库,登录时要将输入的密码与数据查询出的密码进行比对)
 * 3)认证成功怎么处理?(跳转页面,返回json)
 * 4)认证失败怎么处理?(跳转页面,返回json)
 * 5)没有登录就去访问资源系统怎么处理?(返回登录页面,返回json)
 * */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**密码加密*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    /**
     * 定义认证规则
     * @param http 安全对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //1.关闭跨域攻击
        http.csrf().disable();
       //2.配置form认证
        http.formLogin()
                //登录成功怎么处理?(向客户端返回json)
                .successHandler(successHandler()) //登录成功
                //登录失败怎么处理?(向客户端返回json)
                .failureHandler(failureHandler());//登录失败
        //假如某个资源必须认证才可访问,那没有认证怎么办?(返回json)
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint()); //提示要认证
       //3.所有资源都要认证
        http.authorizeRequests()
                .antMatchers("/auth/info")
                .permitAll()
                .anyRequest()//所有请求->对应任意资源
                .authenticated();//必须认证
    }
    /**登录成功以后的处理器*/
    private AuthenticationSuccessHandler successHandler(){
        //当接口中只有一个抽象方法时,构建接口的实现类对象,可以参考如下写法:
        return (request,response,authentication) -> {
            Map<String,Object> map=new HashMap<>();
            map.put("state",200);
            map.put("message","login ok");
            //创建一个令牌对象(应该包含认证和权限信息),JWT格式的令牌可以满足这种需求
            Map<String,Object> jwtMap=new HashMap<>();
            //获取用户身份(包含了认证和授权信息)
            User principal = (User)authentication.getPrincipal();
            //获取用户名
            String username=principal.getUsername();
            //获取用户权限
            List<String> authoritiesList = new ArrayList<>();
            Collection<GrantedAuthority> authorities =
                    principal.getAuthorities();
            for(GrantedAuthority a:authorities){
                authoritiesList.add(a.getAuthority());//sys:res:create
            }
            jwtMap.put("username",username);
            jwtMap.put("authorities",authoritiesList);
            String token= JwtUtils.generatorToken(jwtMap);
            map.put("token",token);
            WebUtils.writeJsonToClient(response,map);
        };
    }
    /**登录失败的处理器*/
    private AuthenticationFailureHandler failureHandler(){
        return (request,response,exception)->{
            Map<String,Object> map=new HashMap<>();
            map.put("state",500);
            map.put("message","username or password error");
            //思考除了返信息,还要返回什么?(JWT令牌)
            WebUtils.writeJsonToClient(response,map);

        };
    }
    /**假如没有登录访问资源时给出提示*/
    private AuthenticationEntryPoint entryPoint(){
        return (request,response,exception)->{
            Map<String,Object> map=new HashMap<>();
            map.put("state",500);
            map.put("message","please login");
            //思考除了返回这些信息,还要返回什么?(JWT令牌)
           WebUtils.writeJsonToClient(response,map);

        };
    }
}
