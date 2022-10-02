package top.mnsx.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.mnsx.springcloud.entity.CommonResult;
import top.mnsx.springcloud.entity.Payment;

import javax.annotation.Resource;

/**
 * @BelongsProject: springcloudstudy
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 12:16
 * @Description:
 */
@RestController
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    @GetMapping("/consumer/payment/create")
    public CommonResult<Integer> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Integer> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
}
