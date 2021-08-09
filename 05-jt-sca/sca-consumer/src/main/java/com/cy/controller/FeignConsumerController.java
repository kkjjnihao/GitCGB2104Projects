package com.cy.controller;

import com.cy.service.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignConsumerController {

    @Autowired
    private RemoteProviderService remoteProvideService;

    @GetMapping("/feign/echo/{msg}")
    public String doFeignEcho(@PathVariable String msg){
        return remoteProvideService.echoMsg(msg);
    }
}
