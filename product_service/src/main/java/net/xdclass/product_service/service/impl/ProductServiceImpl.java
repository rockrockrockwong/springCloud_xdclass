package net.xdclass.product_service.service.impl;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final Map<Integer,Product> daoMap = new HashMap<Integer,Product>();

    static {
        Product p1 = new Product(1,"iphoneX",9999,10);
        Product p2 = new Product(2,"冰箱",2341,102);
        Product p3 = new Product(3,"洗衣机",2341,110);
        Product p4 = new Product(4,"电话",6758,210);
        Product p5= new Product(5,"汽车",9999,110);
        Product p6 = new Product(6,"椅子",120,510);
        Product p7 = new Product(7,"java编程思想",140,170);

        daoMap.put(1,p1);
        daoMap.put(2,p2);
        daoMap.put(3,p3);
        daoMap.put(4,p4);
        daoMap.put(5,p5);
        daoMap.put(6,p6);
        daoMap.put(7,p7);

    }

    @Override
    public List<Product> listProduct() {
        logger.info("coming into product-service-->ProductServiceImpl-->listProduct");
        Collection<Product> collection = daoMap.values();
        ArrayList<Product> list = new ArrayList<Product>(collection);
        return list;
    }

    @Override
    public Product findById(int id) {
        logger.info("coming into product-service-->ProductServiceImpl-->findById");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return daoMap.get(id);
    }
}
