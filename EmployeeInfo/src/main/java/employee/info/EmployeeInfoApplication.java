package employee.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class EmployeeInfoApplication {


    public static void main(String[] args) {
        SpringApplication.run(EmployeeInfoApplication.class, args);
    }

}
