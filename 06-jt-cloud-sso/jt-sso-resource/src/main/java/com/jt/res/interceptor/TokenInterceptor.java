package com.jt.res.interceptor;

import com.jt.res.service.RemoteAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {

    private RestTemplate restTemplate;
    public TokenInterceptor(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    private RemoteAuthService remoteAuthService;
    public TokenInterceptor(RemoteAuthService remoteAuthService){
        this.remoteAuthService=remoteAuthService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.从请求中获取token对象(如何获取取决于你传递token的方式:header,params)
        String token=request.getHeader("token");
        //2.验证token是否存在
        if(token==null||"".equals(token))
            throw new RuntimeException("请先登录");//WebUtils.writeJsonToClient
        //3.验证token是否过期(将来可以直接去访问认证服务器,在那去校验,谁发的令牌,谁校验令牌)
        Map<String,Object> map = remoteAuthService.getAuthentication(token);
//        String url = "http://jt-sso-auth/auth/info?token="+token;
//        Map<String,Object> map = restTemplate.getForObject(url, Map.class);
        if((boolean) map.get("expired"))
            throw new RuntimeException("登录超时,请重新登录");
        //4.解析token中的认证和权限信息(一般存储在jwt格式中的负载部分)
        List<String> list =(List<String>) map.get("authorities");
        //5.封装和存储认证和权限信息
        //5.1构建UserDetail对象(用户身份的象征-类似于一张名片,微信的二维码)
        UserDetails userDetails= User.builder()
                .username((String)map.get("username"))
                .password("")
                .authorities(list.toArray(new String[]{}))
                .build();
        //5.2构建Security权限交互对象(记住,固定写法)
        PreAuthenticatedAuthenticationToken authToken=
                new PreAuthenticatedAuthenticationToken(
                        userDetails,//用户身份
                        userDetails.getPassword(),
                        userDetails.getAuthorities());
        //5.3将权限交互对象与当前请求进行绑定
        authToken.setDetails(new WebAuthenticationDetails(request));
        //5.4.将认证后的token存储到Security上下文(会话对象)
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return true;
    }
}
