package com.rock.eurakasrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakasrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakasrvApplication.class, args);
    }
}
