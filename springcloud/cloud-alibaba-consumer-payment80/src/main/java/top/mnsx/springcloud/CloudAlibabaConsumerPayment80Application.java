package top.mnsx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class CloudAlibabaConsumerPayment80Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConsumerPayment80Application.class, args);
    }

}
