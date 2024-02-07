package org.zerobase.reservestoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class ReserveStoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReserveStoreApiApplication.class, args);
    }
}
