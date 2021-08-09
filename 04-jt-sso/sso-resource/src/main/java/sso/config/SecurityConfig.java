package sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import sso.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //
        http.csrf().disable();
        //
        http.exceptionHandling().accessDeniedHandler(null);
        //
        http.authorizeRequests().anyRequest().permitAll();
    }

    public AccessDeniedHandler accessDeniedHandler(){
        return (request,response,e) -> {
            //
            HashMap<String, Object> map = new HashMap<>();
            map.put("state",HttpServletResponse.SC_FORBIDDEN);//403
            map.put("message", "权限不足");
            //2.将响应信息写到客户端
            WebUtils.writeJsonToClient(response, map);
        };
    }
}
