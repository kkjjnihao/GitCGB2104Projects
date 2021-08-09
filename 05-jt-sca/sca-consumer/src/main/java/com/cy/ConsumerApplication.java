package com.cy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 启动feign方式的服务启动时会扫描@FeignClient注解描述的接口
 */
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Value("${server.port}")
    private String server;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadBalanceRestTemplate(){
        return new RestTemplate();
    }

    @RestController
    public class ConsumerController{
        @Autowired
        private RestTemplate restTemplate;
        /**负载均衡客户端对象:
         * 基于此对象可以从注册中心获取服务实例*/
        @Autowired
        private LoadBalancerClient loadBalancerClient;

        /**
         * 应用整合了负载均衡功能的RestTemplate对象
         */
        @Autowired
        private RestTemplate loadBalanceRestTemplate;

        /**基于RestTemplate进行远程服务调用,但是不具备负载均衡特性
         * 这种方式不需要nacos,是直接通过固定的ip和端口进行服务调用.
         * 此方式不适合多服务实例调用.*/
        private AtomicLong atomicLong=new AtomicLong(1);
        @GetMapping("/consumer/doRestEcho1")
        public  String doRestEcho01() throws InterruptedException {
            //consumerService.doGetResource();
            //获取自增对象的值,然后再加1
//            long num=atomicLong.getAndIncrement();
//            if(num%2==0){//模拟50%的慢调用比例
//                Thread.sleep(200);
//                //throw new RuntimeException(" Runtime Exception ");
//            }
            String url="http://localhost:8081/provider/echo/"+server;
            //远程过程调用-RPC
            return restTemplate.getForObject(url,String.class);//String.class调用服务响应数据类型
        }
        /**基于RestTemplate进行远程服务调用,但是在调用之前基于
         * loadBalancerClient对象去从nacos注册中心基于服务名
         * 查找服务实例(可能有多个),此时会在本地按照一定算法去选择
         * 服务实例,然后进行服务调用*/
        @GetMapping("/consumer/doRestEcho2")
        public String doRestEcho02(){
            //基于服务名(nacos中服务列表中的名字)查找服务实例
            ServiceInstance choose =
                    loadBalancerClient.choose("sca-provider");
            String ip=choose.getHost();
            int port=choose.getPort();//8080,8081
            //构建远程调用的url
            //String url="http://"+ip+":"+port+"/provider/echo/"+server;
            String url=String.format("http://%s:%s/provider/echo/%s",
                    ip,port,server);
            //远程过程调用-RPC
            return restTemplate.getForObject(url,String.class);//String.class调用服务响应数据类型
        }
        /**基于具备了负载均衡特性的RestTemplate进行远程服务调用,
         * 实现的功能与方式2相同,只是代码编写上相对于第二种方式做了
         * 简化,从效率上会稍微逊色于方式2,因为底层会对请求进行拦截,
         * 拦截到以后再基于loadBalancerClient获取服务实例进行调用.*/
        @GetMapping("/consumer/doRestEcho3")
        public String doRestEcho03(){
            String url=
                    String.format("http://%s/provider/echo/%s",
                            "sca-provider",server);
            return loadBalanceRestTemplate.getForObject(url,String.class);
        }
    }
}
