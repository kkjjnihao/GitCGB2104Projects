package com.jt.res.service;

import com.jt.res.service.factory.AuthFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "jt-sso-auth",
             contextId = "remoteAuthService",
             fallbackFactory = AuthFallbackFactory.class)
public interface RemoteAuthService {

    @GetMapping("/auth/info")
    Map<String,Object> getAuthentication(@RequestParam("token") String token);
}
