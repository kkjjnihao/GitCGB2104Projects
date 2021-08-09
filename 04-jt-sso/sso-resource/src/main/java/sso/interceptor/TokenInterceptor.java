package sso.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import sso.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.从请求中获取token对象(如何获取取决于你传递token的方式:header,params)
        String token=request.getHeader("token");
        //2.验证token是否存在
        if(token==null||"".equals(token))
            throw new RuntimeException("请先登录");//WebUtils.writeJsonToClient
        //3.验证token是否过期
        if(JwtUtils.isTokenExpired(token))
            throw new RuntimeException("登录超时,请重新登录");
        //4.解析token中的认证和权限信息(一般存储在jwt格式中的负载部分)
        Claims claims=JwtUtils.getClaimsFromToken(token);
        List<String> list=(List<String>)
                claims.get("authorities");//这个名字应该与创建token时,指定的权限名相同
        //5.封装和存储认证和权限信息
        //5.1构建UserDetail对象(用户身份的象征-类似于一张名片,微信的二维码)
        UserDetails userDetails= User.builder()
                .username((String)claims.get("username"))
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
