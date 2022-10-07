package top.mnsx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudAlibabaConfigClient3377Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConfigClient3377Application.class, args);
    }

}
