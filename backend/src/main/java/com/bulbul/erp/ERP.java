package com.bulbul.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ERP {

    public static void main(String[] args) {
        SpringApplication.run(ERP.class, args);
    }




}
