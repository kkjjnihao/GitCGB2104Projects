package com.cy.service;

import com.cy.service.factory.RemoteProviderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 定义远程服务调用接口,这个接口不需要我们自己写实现类,
 *
 * */
@FeignClient(name = "sca-provider",
             contextId = "remoteProvideService",
             fallbackFactory = RemoteProviderFallbackFactory.class)//sca-provider为nacos中的服务名
public interface RemoteProviderService {
    @GetMapping("/provider/echo/{msg}")
    public String echoMsg(@PathVariable String msg);
}