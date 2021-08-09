package sso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ResourceController {

    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequestMapping("/doCreate")
    public String doCreate(HttpServletResponse response){

        return "create resource (insert data) ok";
    }
    /**查询操作*/
    @PreAuthorize("hasAuthority('sys:res:retrieve')")
    @RequestMapping("/doRetrieve")
    public String doRetrieve(){//Retrieve 表示查询
        return "query resource (select data) ok";
    }
    /**修改操作*/
    @PreAuthorize("hasAuthority('sys:res:update')")
    @RequestMapping("/doUpdate")
    public String doUpdate(){
        return "update resource (update data) ok";
    }
    /**删除操作*/
    @PreAuthorize("hasAuthority('sys:res:delete')")
    @RequestMapping("/doDelete")
    public String doDelete(){
        return "delete resource (dalete data) ok";
    }

}
