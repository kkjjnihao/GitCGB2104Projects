package com.cy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 当我们执行登录操作的时,底层会通过过滤器等对象,调用这个方法
     * @param username 这个参数为页面输出的用户名
     * @return 一般从数据库基于用户名查询到的用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //基于用户名从数据库的信息
        //User user = userMapper.selectUserByName(username);
        if(!"jack".equals(username))
            throw new UsernameNotFoundException("user not exists");
        //2. 将用户信息封装到UserDetails对象中返回
        //假设这个密码也是从数据库查询到的
        String encodedPwd = passwordEncoder.encode("123456");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_jinpai,ROLE_tongpai,sys:res:retrieve,sys:res:create");
        User user = new User(username, encodedPwd, grantedAuthorities);
        return user;
    }
}
