package sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sso.dao.UserMapper;

import java.util.List;
import java.util.Map;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper userMapper;

    /**
     * 当我们执行登录操作的时,底层会通过过滤器等对象,调用这个方法
     * @param username 这个参数为页面输出的用户名
     * @return 一般从数据库基于用户名查询到的用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.基于用户名查询用户信息
        Map<String, Object> userMap = userMapper.selectUserByUsername(username);
        if(userMap == null) throw new UsernameNotFoundException("user not exists");
        //2.查询用户权限信息并封装查询结果
        String password=passwordEncoder.encode("123456");
        System.out.println("password="+password);
        List<String> userPermissions = userMapper.selectUserPermissions((Long) userMap.get("id"));
        //这个user是SpringSecurity提供的UserDetails接口的实现,用于封装用户信息
        //后续我们也可以基于需要自己构建UserDetails接口的实现
        return new User(username,(String) userMap.get("password"), AuthorityUtils.createAuthorityList(userPermissions.toArray(new String[]{})));//这里的返回值会交给springsecurity去校验
    }
}
