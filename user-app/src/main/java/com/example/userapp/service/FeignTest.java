package com.example.userapp.service;

import com.example.userapp.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author huangxiaolei
 * @description Feign测试类
 * @create 2019/08/10
 */
@FeignClient("EUREKA-PROVIDE")
public interface FeignTest {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    List<User> user();

    @GetMapping("/test/{name}")
    String test(@PathVariable String name);

}
