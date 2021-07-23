package sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sso.util.JwtUtils;
import sso.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean//对象名默认为方法名
    //@Bean("bcryptPasswordEncoder")//bean对象名字为bcryptPasswordEncoder
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.配置form认证
        http.formLogin()
                .successHandler(successHandler()) //登录成功
                .failureHandler(failureHandler());//登录失败
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint()) //提示要认证
                .accessDeniedHandler(null); //提示访问被拒绝
        //3.所有资源都要认证
        http.authorizeRequests().anyRequest().authenticated();
    }

    /**
     *
     * @return
     */
    private AuthenticationSuccessHandler successHandler(){
        return (req,res,auth) -> {
            Map<String,Object> map = new HashMap<>();
            map.put("state",HttpServletResponse.SC_OK);//200
            map.put("message","登录成功");
            Map<String,Object> userInfo = new HashMap<>();
            User user = (User) auth.getPrincipal();
            userInfo.put("username", user.getUsername());
            ArrayList<String> authorities = new ArrayList<>();
            user.getAuthorities().forEach(authority ->
                    authorities.add(authority.getAuthority()) );
            userInfo.put("authorities",authorities);
            String token = JwtUtils.generatorToken(userInfo);
            map.put("token", token);
            WebUtils.writeJsonToClient(res, map);
        };
    }

    /**
     *
     * @return
     */
    private AuthenticationFailureHandler failureHandler(){
        return (req,res,auth) -> {
            Map<String,Object> map = new HashMap<>();
            map.put("state",HttpServletResponse.SC_FORBIDDEN);//403
            map.put("message","没有访问权限,请联系管理员");
            WebUtils.writeJsonToClient(res, map);
        };
    }

    /**
     *
     * @return
     */
    private AuthenticationEntryPoint entryPoint(){
        return (req,res,auth) -> {
            Map<String,Object> map = new HashMap<>();
            map.put("state",HttpServletResponse.SC_UNAUTHORIZED);//401
            map.put("message","请先登录");
            WebUtils.writeJsonToClient(res, map);
        };
    }

}
