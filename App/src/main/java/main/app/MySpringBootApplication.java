package main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
@EnableCaching
public class MySpringBootApplication {


    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}
