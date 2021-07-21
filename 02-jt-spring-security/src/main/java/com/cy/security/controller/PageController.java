package com.cy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request){
        String username=request.getParameter("username");
        System.out.println("toIndex()=>"+username);
        //spring mvc 中的重定向
        return "redirect:/index.html";
    }
}
