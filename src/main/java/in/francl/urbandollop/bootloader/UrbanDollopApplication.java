package in.francl.urbandollop.bootloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(
        scanBasePackages = { "in.francl.urbandollop" }
)
@EnableJpaRepositories(
        basePackages = { "in.francl.urbandollop" }
)
@EntityScan(
        basePackages = { "in.francl.urbandollop" }
)
@EnableTransactionManagement
public class UrbanDollopApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UrbanDollopApplication.class, args);
    }
    
}


