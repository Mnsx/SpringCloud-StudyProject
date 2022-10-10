package top.mnsx.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.mnsx.springcloud.entity.Payment;
import top.mnsx.springcloud.service.PaymentService;

import javax.annotation.Resource;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 8:32
 * @Description:
 */
@RestController
public class CircleBreakerController {
    @Value("${service-url.nacos-user-service}")
    public String SERVICE_URL;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource("fallback")
//    @SentinelResource(value = "fallback", fallback="handleFallback")
//    @SentinelResource(value = "fallback", blockHandler="blockHandler")
//    @SentinelResource(value="fallback", blockHandler = "blockHandler", fallback = "handleFallback")
    @SentinelResource(value = "fallback", blockHandler="blockHandler", exceptionsToIgnore = {RuntimeException.class})
    public String fallback(@PathVariable Long id) {
        String result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, String.class, id);

        if (id == 4) {
            throw new RuntimeException("非法参数异常");
        } else if (result == null) {
            throw new RuntimeException("没有对应数据");
        }

        return result;
    }

    public String handleFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return "handlerFallback，exception：" + e.getMessage() + "data: " + payment;
    }

    public String blockHandler(@PathVariable Long id, BlockException blockException) {
        Payment payment = new Payment(id, "null");
        return "blockHandler-sentinel限流，blockException：" + blockException.getMessage() + "data: " + payment;
    }

    // =========================

    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    @SentinelResource(value = "fallback", fallback="handleFallback")
    public String paymentSQL(@PathVariable("id") Long id) {
        if (id == 4) {
            throw new RuntimeException("hello");
        }
        return paymentService.paymentSQL(id);
    }

}
