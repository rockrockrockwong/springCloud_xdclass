package net.xdclass.order_service.loadbalance.ribbon;

import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {



    @Bean("restTemplate")
    RestTemplate restTemplate(){return new RestTemplate();}

    @Bean("ribbonRestTemplate")
    @LoadBalanced
    RestTemplate ribbonRestTemplate(){
        return new RestTemplate();
    }

    @Bean("RibbonLoadBalanceClient")
    RibbonLoadBalanceClient ribbonLoadBalanceClient(){
        return new RibbonLoadBalanceClient();
    }


}
