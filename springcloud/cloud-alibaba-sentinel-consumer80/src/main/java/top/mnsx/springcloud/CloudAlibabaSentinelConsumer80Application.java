package top.mnsx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CloudAlibabaSentinelConsumer80Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaSentinelConsumer80Application.class, args);
    }

}
