package com.jt.sso.service;
import com.jt.sso.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

/**
 * 通过此对象处理登录请求
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        //1.基于用户名查询用户信息
        Map<String,Object> userMap=userMapper.selectUserByUsername(username);
        if(userMap==null)throw new UsernameNotFoundException("user not exists");
        //2.查询用户权限信息并封装查询结果
        List<String> userPermissions=
        userMapper.selectUserPermissions((Long)userMap.get("id"));
        //String password=passwordEncoder.encode("123456");
        //System.out.println("password="+password);
//        List<GrantedAuthority> grantedAuthorities =
//                AuthorityUtils.commaSeparatedStringToAuthorityList(
//                        "sys:res:retrieve,sys:res:create");
        //权限信息后续要从数据库去查
        return new User(username,
                (String)userMap.get("password"),//来自数据库
                AuthorityUtils.createAuthorityList(//来自数据库
                        userPermissions.toArray(new String[]{})));
        //这个值返回给谁?谁调用此方法这个就返回给谁.
    }
}
