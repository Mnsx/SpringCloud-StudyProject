package top.mnsx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudAlibabaSentinelProvider9003Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaSentinelProvider9003Application.class, args);
    }

}
