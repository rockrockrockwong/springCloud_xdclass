package net.xdclass.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.xdclass.order_service.domain.ProductOrder;
import net.xdclass.order_service.loadbalance.ribbon.RibbonLoadBalanceClient;
import net.xdclass.order_service.service.ProductClient;
import net.xdclass.order_service.service.ProductOrderService;
import net.xdclass.order_service.service.productServices.ProductService_UseHystrixCommand;
import net.xdclass.order_service.utils.JasonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    @Autowired
    @Qualifier("ribbonRestTemplate")
    private RestTemplate ribbonRestTemplate;

    @Autowired
    @Qualifier("RibbonLoadBalanceClient")
    private RibbonLoadBalanceClient ribbonLoadBalanceClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    @Qualifier("ProductService_UseHystrixCommand")
    ProductService_UseHystrixCommand productService_useHystrixCommand;

    @Override
    public ProductOrder save(int userId, int productId) {

        logger.info("coming into order-service-->ProductOrderServiceImpl-->save");

        //create a new product order
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateDate(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());

        //call remote product service to get product info, here we
        //use stand alone service with "HystrixCommand".
        Map<String,Object> prod = productService_useHystrixCommand.getProductInfo(productId);
        productOrder.setProductName(prod.get("name").toString());
        productOrder.setPrice(Integer.parseInt(prod.get("price").toString()));

        /*Map<String,Object> prod = getProductInfo_by_ribbonRestTemplate(productId);
        productOrder.setProductName(prod.get("name").toString());
        productOrder.setPrice(Integer.parseInt(prod.get("price").toString()));*/

        /*
        //set prodcut properties get from product-service
        JsonNode jsonNode = getProductInfo_byJson(productId);
        productOrder.setProductName(jsonNode.get("name").asText());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").asText()));
        */

        return productOrder;
    }

    /**
     * HystrixCommand will NOT work here, as this method use "productClient"
     * which decorate by "@FeignClient", the circuit breaker will take effect following
     *
     * */
    @HystrixCommand(fallbackMethod = "invokeProductId_fallbak")
    public JsonNode getProductInfo_byJson(int productId){
        //call by feign client
        String response = productClient.findById(productId);
        JsonNode jsonNode = JasonUtils.str2JsonNode(response);

        //output product in console
        System.out.println(response);

        return jsonNode;
    }
    private JsonNode invokeProductId_fallbak(int productId){
        System.out.println("invoked invokeProductId_fallbak");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree("{\"id\":999,\"name\":\"Unknow\",\"price\":0,\"store\":0}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  jsonNode;
    }

    private Map<String,Object> getProductInfo_byRibbonLoadBalanceClient(int productId){
        //call by choosed ServiceInstance by LoadBalancerClient
        /*
        Object product_by_ribbonLoadBalanceClient = ribbonLoadBalanceClient.
                getForObject("product-service",
                             "api/v1/product/find?id="+productId,
                              Object.class);
        */
        Map<String,Object> productMap_by_ribbonLoadBalanceClient = (Map<String, Object>) ribbonLoadBalanceClient.
                getForObject("product-service",
                        "api/v1/product/find?id="+productId,
                        Map.class);
        return productMap_by_ribbonLoadBalanceClient;
    }
    private Map<String,Object> getProductInfo_by_ribbonRestTemplate(int productId){
        //call by RestTemplate embellished by @Loadbalance
        //Object product_by_ribbonRT = ribbonRestTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId,Object.class);
        Map<String,Object> productMap_by_ribbonRT = ribbonRestTemplate.
                getForObject("http://product-service/api/v1/product/find?id="+productId,
                              Map.class);

        return productMap_by_ribbonRT;
    }


}
