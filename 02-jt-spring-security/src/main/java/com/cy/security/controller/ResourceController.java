package com.cy.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceController {
    /**添加操作
     * @PreAuthorize 注解由SpringSecurity框架提供,用于描述方法,此注解描述
     * 方法以后,再访问方法首先要进行权限检测*/
    //@PreAuthorize("hasRole('admin')")//假如登录用户具备admin这个角色可以访问
    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequestMapping("/doCreate")
    public String doCreate(){
        //......
        return "create resource (insert data) ok";
    }
    /**查询操作*/
    /**修改操作*/
    /**删除操作*/
    @PreAuthorize("hasAuthority('sys:res:delete')")
    @RequestMapping("/doDelete")
    public String doDelete(){
        //.....
        return "delete resource (dalete data) ok";
    }

    @GetMapping("doGetUser")
    public String doGetUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();

        return principal.getUsername() + principal.getAuthorities() + principal.getPassword();
    }

}
