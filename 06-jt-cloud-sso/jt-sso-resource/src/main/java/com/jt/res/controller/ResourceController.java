package com.jt.res.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequestMapping("/doCreate")
    public String doCreate(){
        return "create resource (insert) ok";
    }
    @PreAuthorize("hasAuthority('sys:res:retrieve')")
    @RequestMapping("/doRetrieve")
    public String doRetrieve(){
        return "retrieve resource (retrieve) ok";
    }
    @PreAuthorize("hasAuthority('sys:res:delete')")
    @RequestMapping("/doDelete")
    public String doDelete(){
        return "delete resource (delete) ok";
    }
    @PreAuthorize("hasAuthority('sys:res:update')")
    @RequestMapping("/doUpdate")
    public String doUpdate(){
        return "update resource (update) ok";
    }
}
