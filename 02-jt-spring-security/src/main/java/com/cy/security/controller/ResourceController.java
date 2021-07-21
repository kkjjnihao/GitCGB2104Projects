package com.cy.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @PreAuthorize("hasAnyAuthority('sys:res:create')")
    @RequestMapping("doCreate")
    public String doCreate(){
        return "add resource";
    }

}
