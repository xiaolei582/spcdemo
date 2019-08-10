package com.example.userapp.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangxiaolei
 * @description
 * @create 2019/08/10
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
        //配置负载均衡的规则，更改为随机
        return new RandomRule();
    }

}
