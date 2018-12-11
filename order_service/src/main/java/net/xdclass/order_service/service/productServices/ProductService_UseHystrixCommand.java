package net.xdclass.order_service.service.productServices;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("ProductService_UseHystrixCommand")
public class ProductService_UseHystrixCommand {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("ribbonRestTemplate")
    private RestTemplate ribbonRestTemplate;

    @HystrixCommand(fallbackMethod = "invokeProductId_fallbak")
    public Map<String,Object> getProductInfo(int productId){

        Map<String,Object> mapper = ribbonRestTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId,Map.class);

        //output product in console
        System.out.print("ProductService_UseHystrixCommand::");
        System.out.println(mapper);

        return mapper;
    }
    private Map<String, Object> invokeProductId_fallbak(int productId){
        System.out.println("ProductService_UseHystrixCommand::invoked invokeProductId_fallbak");
        Map<String,Object> rtnMapper = new HashMap<String,Object>();
        rtnMapper.put("name","fakename");
        rtnMapper.put("price","0");
        return  rtnMapper;
    }

}
