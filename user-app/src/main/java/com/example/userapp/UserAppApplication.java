package com.example.userapp;

import com.example.userapp.config.RibbonConfiguration;
import com.example.userapp.domain.User;
import com.example.userapp.service.FeignTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RibbonClient(name = "EUREKA-PROVIDE",configuration = RibbonConfiguration.class)
public class UserAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAppApplication.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/test")
    public Object test(){

        //获取eureka-provide的ip和端口
        ServiceInstance si = this.loadBalancerClient.choose("eureka-provide");

        System.out.println("host:"+si.getHost());
        System.out.println("port:"+si.getPort());

        ParameterizedTypeReference<List<User>> ptr = new ParameterizedTypeReference<List<User>>(){};

        //返回值二次封装
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange("http://EUREKA-PROVIDE:9000/user", HttpMethod.GET,null,ptr);

        List<User> list = responseEntity.getBody();

        System.out.println(restTemplate.getForObject("http://EUREKA-PROVIDE:9000/hello", String.class));

        return list;
    }

    @RequestMapping("/test2")
    public String test2(){
        return restTemplate.getForObject("http://EUREKA-PROVIDE:9000/hello", String.class);
    }

    @RequestMapping("test3")
    public String test3(){
        restTemplate.getForObject("http://EUREKA-PROVIDE:9000/hello", String.class,"timeplet");
        return restTemplate.getForObject("http://EUREKA-PROVIDE:9000/hello", String.class);
    }



    //ribbon测试
    @GetMapping("/user")
    public List findById(){
        return restTemplate.getForObject("http://EUREKA-PROVIDE/user", ArrayList.class);
    }

    private Logger log = LoggerFactory.getLogger(UserAppApplication.class);


    @Autowired
    private FeignTest feignTest;


    @GetMapping("/test4/{name}")
    public String test4(@PathVariable String name){
       return feignTest.test(name);
    }

    @GetMapping("/test5")
    public List<User> test5(){
        return feignTest.user();
    }
}
