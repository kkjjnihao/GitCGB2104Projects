package com.cy.service.factory;

import com.cy.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteProviderFallbackFactory implements FallbackFactory<RemoteProviderService> {

    @Override
    public RemoteProviderService create(Throwable throwable) {
        System.out.println("--------------------");
        System.out.println(throwable.getMessage());
        System.out.println("--------------------");
        return msg -> "服务维护中,稍等片刻再访问";
    }
}
