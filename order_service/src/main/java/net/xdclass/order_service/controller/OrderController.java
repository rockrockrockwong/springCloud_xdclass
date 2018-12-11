package net.xdclass.order_service.controller;

import net.xdclass.order_service.service.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ProductOrderService productOrderService;


    @RequestMapping("save")
    public Object save(@RequestParam("user_id") int userId, @RequestParam("product_id") int product_id){
        logger.info("comming into order-service-->OrderController-->save");
        return productOrderService.save(userId,product_id);
    }



}
