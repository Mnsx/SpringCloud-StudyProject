package top.mnsx.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 13:13
 * @Description:
 */
@Component
@FeignClient("nacos-payment-provider")
public interface PaymentService {
    @GetMapping("/payment/nacos/{id}")
    String get(@PathVariable("id") Integer id);
}
