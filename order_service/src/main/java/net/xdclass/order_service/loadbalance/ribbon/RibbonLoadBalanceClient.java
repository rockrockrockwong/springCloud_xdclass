package net.xdclass.order_service.loadbalance.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestTemplate;

public class RibbonLoadBalanceClient {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public Object getForObject(String serviceID,String interfacePath,Class returnClass){
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceID);
        String realUrl = String.format("http://%s:%s/"+interfacePath,serviceInstance.getHost(),serviceInstance.getPort());
        return restTemplate.getForObject(realUrl,returnClass);
    }

}
