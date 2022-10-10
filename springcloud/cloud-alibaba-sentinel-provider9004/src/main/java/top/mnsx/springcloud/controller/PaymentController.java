package top.mnsx.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.springcloud.entity.Payment;

import java.util.HashMap;
import java.util.UUID;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 8:16
 * @Description:
 */
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> map = new HashMap<>();

    static {
        map.put(1L, new Payment(1L, UUID.randomUUID().toString()));
        map.put(2L, new Payment(2L, UUID.randomUUID().toString()));
        map.put(3L, new Payment(3L, UUID.randomUUID().toString()));
    }

    @GetMapping("/paymentSQL/{id}")
    public String paymentSQL(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        return "from mysql, serverPort: " + serverPort + "data: " + payment;
    }
}
