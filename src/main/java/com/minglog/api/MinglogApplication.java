package com.minglog.api;

import com.minglog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class MinglogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinglogApplication.class, args);
    }

}
