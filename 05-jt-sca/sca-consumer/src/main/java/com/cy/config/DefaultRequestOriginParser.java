package com.cy.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义请求解析器,用于对请求进行解析,并返回解析结果,sentinel底层
 * 在拦截到用户请求以后,会对请求数据基于此对象进行解析,判定是否
 * 符合黑白名单规则,例如,客户端请求一个资源,方式如下:
 * http://ip:port/consumer/doRestEcho1?origin=app1
 * 假如参数origin的值在黑名单规则中,则限制对资源的访问.
 */
@Component
public class DefaultRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //获取请求参数origin的值
        return request.getParameter("origin");
    }
}
