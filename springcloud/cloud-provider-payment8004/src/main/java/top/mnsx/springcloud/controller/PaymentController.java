package top.mnsx.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/2 10:06
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/zk")
    public String paymentZK() {
        return "SpringCloud with Zookeeper：" + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
