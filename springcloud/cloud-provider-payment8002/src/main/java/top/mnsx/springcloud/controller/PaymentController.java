package top.mnsx.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.mnsx.springcloud.entity.CommonResult;
import top.mnsx.springcloud.entity.Payment;
import top.mnsx.springcloud.service.PaymentService;

import javax.annotation.Resource;

/**
 * @BelongsProject: spring-cloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 10:48
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果：" + result);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据库成功" + serverPort, result);
        } else {
            return new CommonResult<>(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("获得结果：" + payment);

        if (payment != null) {
            return new CommonResult<>(200, "查询数据库成功" + serverPort, payment);
        } else {
            return new CommonResult<>(444, "查询数据库失败, 查询ID" + id + "失败");
        }
    }

    @GetMapping
    public String getServerPort() {
        return serverPort;
    }
}
