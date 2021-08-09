package com.jt.res.service.factory;

import com.jt.res.service.RemoteAuthService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthFallbackFactory implements FallbackFactory<RemoteAuthService> {
    @Override
    public RemoteAuthService create(Throwable throwable) {
        return token -> {
            Map<String,Object> map = new HashMap<>();
            map.put("msg", "服务维护中,稍等片刻再访问");
            return map;
        };
    }
}
