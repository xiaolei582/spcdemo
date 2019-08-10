package com.example.helloapp.controller;

import com.example.helloapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author huangxiaolei
 * @description
 * @create 2019/08/09
 */
@RestController
public class HelloController {

    private Logger log = Logger.getLogger(String.valueOf(getClass()));

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/index")
    public String index(){
        List<ServiceInstance> list = client.getInstances("HELLO-SERVICE");

        for (ServiceInstance serviceInstance : list) {
            System.out.println("getHost: "+serviceInstance.getHost());
            System.out.println("getInstanceId: "+serviceInstance.getInstanceId());
            System.out.println("getScheme: "+serviceInstance.getScheme());
            System.out.println("getServiceId: "+serviceInstance.getServiceId());
            System.out.println("getMetadata: "+serviceInstance.getMetadata());
            System.out.println("getPort: "+serviceInstance.getPort());
            System.out.println("getUri: "+serviceInstance.getUri());
            System.out.println("---------------");
        }
        return list.get(0).getUri().toASCIIString();
    }

    @RequestMapping("/hello")
    public String hello(String name){
        System.out.println("被调用");
        return String.format("Hello %s !", name);
    }

    @RequestMapping("/user")
    public List<User> user(){
        List<User> list = new ArrayList<>();
        list.add(new User("template", "123456"));
        list.add(new User("adga", "123456"));
        list.add(new User("adggare", "123456"));

        return list;
    }

    @GetMapping("/test/{name}")
    public String test(@PathVariable String name){
        return "hello is my: "+name;
    }

}
