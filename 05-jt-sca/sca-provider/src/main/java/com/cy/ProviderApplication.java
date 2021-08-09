package com.cy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
    @Value("${server.port}")
    private String server;

    @Value("${logging.level.com.cy:error}")
    private String logLevel;

    private static final Logger log = LoggerFactory.getLogger(ProviderApplication.class);



    @RefreshScope
    @RestController
    public class ProviderController{
        @Value("${server.tomcat.threads.max:50}")
        private int maxThread;

        @Value("${page.pageSize:10}")
        private int page;

        public ProviderController(){
            System.out.println("ProviderController()");
        }

        @GetMapping("/provider/pageSize")
        public String doPageSize(){
            return "page size is "+page;
        }

        @GetMapping("/provider/maxThread")
        public String doGetMaxThread(){
            return "max thread is "+maxThread;
        }

        @GetMapping("/provider/logLevel")
        public String doGetLogLevel(){
            //System.out.println("log.level="+logLevel);
            //日志优先级 trace<debug<info<warn<<error
            log.trace("==log.trace==");//跟踪
            log.debug("==log.debug==");//调试
            log.info("==log.info==");//常规信息
            log.warn("==log.warn==");//警告
            log.error("==log.error==");//错误信息
            return "log level is "+logLevel;
        }
        @GetMapping("/provider/echo/{msg}")
        public String doEcho(@PathVariable String msg) throws InterruptedException {//Echo为回显的含义
//            Thread.sleep(500000);
            if (msg ==null || msg.length() < 3)
                throw new IllegalArgumentException("参数非法");
            return server+" say: hello "+msg;
        }
    }
}
