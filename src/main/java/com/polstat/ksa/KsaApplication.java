package com.polstat.ksa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KsaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KsaApplication.class, args);
    }
}

